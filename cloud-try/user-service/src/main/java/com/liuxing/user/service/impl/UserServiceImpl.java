package com.liuxing.user.service.impl;

import com.liuxing.user.mapper.UserMapper;
import com.liuxing.user.pojo.User;
import com.liuxing.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public User getById(long id) {

        return userMapper.getById(id);
    }
}
