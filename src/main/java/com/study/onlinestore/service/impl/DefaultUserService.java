package com.study.onlinestore.service.impl;

import com.study.onlinestore.dao.UserDao;
import com.study.onlinestore.entity.User;
import com.study.onlinestore.service.UserService;

public class DefaultUserService implements UserService {
    private UserDao userDao;

    @Override
    public User getUserByName(String name) {
        return userDao.getUserByName(name);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

