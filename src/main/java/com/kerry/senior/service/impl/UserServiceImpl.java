package com.kerry.senior.service.impl;

import com.kerry.senior.domain.User;
import com.kerry.senior.mapper.UserMapper;
import com.kerry.senior.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author CP_dongchuan
 * @date 2018/3/28
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int insert(User user) {
        int i = userMapper.inset(user);
        return i;
    }

    @Override
    public User get(Long id) {
        return userMapper.get(id);
    }

    @Override
    public List<User> list() {
        return userMapper.list();
    }
}
