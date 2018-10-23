package com.study.onlinestore.service.impl;

import com.study.onlinestore.dao.UserDao;
import com.study.onlinestore.entity.User;
import com.study.onlinestore.service.UserService;

public class DefaultUserService implements UserService {
    private UserDao userDao;

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        return userDao.getUserByNameAndPassword(name, password);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}

