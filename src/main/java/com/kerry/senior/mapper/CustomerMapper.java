package com.kerry.senior.mapper;

import com.kerry.senior.domain.Customer;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Kerry Dong
 */
@Mapper
public interface CustomerMapper {

    Customer getById(Long id);

    int insert(Customer customer);

    Customer queryByMobile(String mobilePhone);

}
