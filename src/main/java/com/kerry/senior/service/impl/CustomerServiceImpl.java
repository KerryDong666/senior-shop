package com.kerry.senior.service.impl;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.exception.GlobalException;
import com.kerry.senior.exception.LoginException;
import com.kerry.senior.mapper.CustomerMapper;
import com.kerry.senior.result.CodeMsg;
import com.kerry.senior.service.CustomerService;
import com.kerry.senior.util.MD5;
import com.kerry.senior.vo.CustomerRegisterVo;
import com.kerry.senior.vo.LoginVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author CP_dongchuan
 * @date 2018/3/29
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    @Transactional
    public int register(CustomerRegisterVo vo) {
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
        int row = customerMapper.insert(customer);
        if (row <= 0) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        return row;
    }

    @Override
    public Customer login(LoginVo vo) {
        if (vo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        Customer customer = customerMapper.queryByMobile(vo.getMobilePhone());
        if (customer == null) {
            throw new LoginException(CodeMsg.MOBILE_NON_REGISTER);
        }
        if (!MD5.md5(vo.getPassword(), customer.getSalt()).equals(customer.getPassword())) {
            throw new LoginException(CodeMsg.PWD_ERROR);
        }
        return customer;
    }
}
