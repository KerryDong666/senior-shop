package com.kerry.senior.service;

import com.kerry.senior.domain.User;

import java.util.List;

/**
 * @author Kerry Dong
 */
public interface UserService {

    List<User> list();

    int insert(User user);

    User get(Long id);
}
