package com.kerry.senior.service;

import com.kerry.senior.domain.Customer;
import com.kerry.senior.vo.CustomerRegisterVo;
import com.kerry.senior.vo.LoginVo;

/**
 * @author CP_dongchuan
 * @date 2018/3/29
 */
public interface CustomerService {

    int register(CustomerRegisterVo vo);

    Customer login(LoginVo vo);

}