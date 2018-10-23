package com.study.onlinestore.service;

import com.study.onlinestore.entity.User;

public interface UserService {
    User getUserByNameAndPassword(String name, String password);
}
