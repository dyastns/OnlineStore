package com.study.onlinestore.dao.jdbc;

import com.study.onlinestore.dao.UserDao;
import com.study.onlinestore.dao.jdbc.mapper.UserRowMapper;
import com.study.onlinestore.entity.User;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcUserDao implements UserDao {
    private final static String GET_USER_SQL = "SELECT name, password, usertype FROM store_user WHERE name = ?;";
    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private DataSource dataSource;

    public JdbcUserDao() {
    }

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User getUserByName(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_SQL);) {
            preparedStatement.setString(1, name);
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

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
