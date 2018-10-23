package com.study.onlinestore.dao.jdbc;

import com.study.onlinestore.dao.UserDao;
import com.study.onlinestore.dao.jdbc.mapper.UserRowMapper;
import com.study.onlinestore.entity.User;

import java.sql.*;

public class JdbcUserDao implements UserDao {
    private final static String GET_USER_SQL = "SELECT name, password, usertype FROM store_user WHERE name = ? and password = ?;";
    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        try (Connection connection = JdbcConnector.instance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_SQL);) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            User user = new User();

            while (resultSet.next()) {
                user = USER_ROW_MAPPER.mapRow(resultSet);
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
