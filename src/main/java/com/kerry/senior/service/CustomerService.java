package com.kerry.senior.service;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.vo.CustomerRegisterVo;
import com.kerry.senior.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Kerry Dong
 */
public interface CustomerService {

    int register(CustomerRegisterVo vo);

    Customer login(LoginVo vo, HttpServletResponse response);

    Customer getCustomerByToken(HttpServletResponse response, String token);
}
