package com.study.onlinestore.dao.jdbc;

import com.study.onlinestore.dao.ProductDao;
import com.study.onlinestore.dao.jdbc.mapper.ProductRowMapper;
import com.study.onlinestore.entity.Product;

import javax.smartcardio.CommandAPDU;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private final static String GET_ALL_SQL = "SELECT id, name, description, price, picturePath FROM product;";
    private final static String ADD_PRODUCT_SQL = "INSERT INTO product (name, description, price, picturePath) VALUES (?, ?, ?, ?);";
    private final static ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    public List<Product> getAll() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_SQL);) {
            List<Product> products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addProduct(Product product) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT_SQL);) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getPicturePath());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection getConnection() {
        String dbUrl = "jdbc:postgresql://localhost:5432/storedb";
        try {
            return DriverManager.getConnection(dbUrl, "storedb_user", "123456");
        } catch (SQLException e) {
            throw new RuntimeException("Cannot create connection to database", e);
        }
    }
}
