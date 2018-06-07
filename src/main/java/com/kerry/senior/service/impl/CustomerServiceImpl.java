package com.kerry.senior.service.impl;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.exception.GlobalException;
import com.kerry.senior.exception.LoginException;
import com.kerry.senior.mapper.CustomerMapper;
import com.kerry.senior.redis.RedisConstant;
import com.kerry.senior.redis.RedisUtil;
import com.kerry.senior.result.CodeMsg;
import com.kerry.senior.service.CustomerService;
import com.kerry.senior.util.MD5;
import com.kerry.senior.util.UUIDUtil;
import com.kerry.senior.vo.CustomerRegisterVo;
import com.kerry.senior.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Kerry Dong
 * @date 2018/3/29
 */
@Service
@CacheConfig(cacheNames = "customer")
public class CustomerServiceImpl implements CustomerService {

    private final CustomerMapper customerMapper;

    private final RedisUtil redisUtil;

    private static final int COOKIE_EXPIRE_SECONDS = 3600 * 24 * 2;

    @Autowired
    public CustomerServiceImpl(CustomerMapper customerMapper, RedisUtil redisUtil) {
        this.customerMapper = customerMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int register(CustomerRegisterVo vo) {
        int row = 0;
        if (vo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobilePhone = vo.getMobilePhone();
        Customer customer = customerMapper.queryByMobile(mobilePhone);
        if (customer != null) {
            throw new GlobalException(CodeMsg.MOBILE_HAS_REGISTER);
        }
        customer = new Customer();
        BeanUtils.copyProperties(vo, customer);
        String dbPassword = MD5.md5(vo.getPassword(), vo.getSalt());
        customer.setPassword(dbPassword);
        customer.setRegisterDate(new Date());
        row = customerMapper.insert(customer);
        //异常测试
        int a = 1 / 0;
        if (row <= 0) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return row;
    }

    /**
     * 优化三:对象缓存
     *
     * @param vo
     * @param response
     * @return
     */
    @Override
    @Cacheable(key = "#vo.mobilePhone")
    public Customer login(LoginVo vo, HttpServletResponse response) {
        if (vo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        Customer customer = customerMapper.queryByMobile(vo.getMobilePhone());
        //验证手机号是否已经注册
        if (customer == null) {
            throw new LoginException(CodeMsg.MOBILE_NON_REGISTER);
        }
        //验证密码
        if (!MD5.md5(vo.getPassword(), customer.getSalt()).equals(customer.getPassword())) {
            throw new LoginException(CodeMsg.PWD_ERROR);
        }
        //生成token,添加cookie
        String token = UUIDUtil.getUuid();
        addCookie(response, customer, token);
        return customer;
    }

    @Override
    public Customer getCustomerByToken(HttpServletResponse response, String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Customer customer = redisUtil.get(token, Customer.class);
        //延长有效期
        if (customer != null) {
            addCookie(response, customer, token);
        }
        return customer;
    }

    private void addCookie(HttpServletResponse response, Customer customer, String token) {
        redisUtil.set(token, customer, COOKIE_EXPIRE_SECONDS);
        //设置cookie
        Cookie cookie = new Cookie(RedisConstant.USER_COOKIE_NAME, token);
        cookie.setMaxAge(COOKIE_EXPIRE_SECONDS);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
