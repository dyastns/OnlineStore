package com.study.onlinestore.dao.jdbc.mapper;

import com.study.onlinestore.entity.User;
import com.study.onlinestore.entity.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        String strUserType = resultSet.getString("userType");
        user.setUserType(UserType.getByName(strUserType));

        return user;
    }
}
