package com.kerry.senior.mapper;

import com.kerry.senior.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author CP_dongchuan
 * @date 2018/3/29
 */
@Mapper
public interface CustomerMapper {

    Customer getById(Long id);

    int insert(Customer customer);

    Customer queryByMobile(String mobilePhone);

}
