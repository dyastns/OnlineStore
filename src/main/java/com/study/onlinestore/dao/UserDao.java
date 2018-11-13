package com.study.onlinestore.dao;

import com.study.onlinestore.entity.User;

public interface UserDao {
    User getUserByName(String name);
}
