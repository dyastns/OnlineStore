package com.study.onlinestore.dao.jdbc;

import com.study.onlinestore.dao.ProductDao;
import com.study.onlinestore.dao.jdbc.mapper.ProductRowMapper;
import com.study.onlinestore.entity.Product;

import javax.smartcardio.CommandAPDU;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {
    private final static String GET_ALL_SQL = "SELECT id, name, description, price, picturePath FROM product;";
    private final static String GET_PRODUCT_SQL = "SELECT id, name, description, price, picturePath FROM product WHERE id = ?;";
    private final static String ADD_PRODUCT_SQL = "INSERT INTO product (name, description, price, picturePath) VALUES (?, ?, ?, ?);";
    private final static String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE id = ?;";
    private final static String EDIT_PRODUCT_SQL = "UPDATE product SET name = ?, description = ?, price = ?, picturepath = ? WHERE id = ?;";
    private final static ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    private DataSource dataSource;

    public JdbcProductDao() {
    }

    public JdbcProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Product> getAll() {
        try (Connection connection = dataSource.getConnection();
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
    public Product getProductById(int productId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCT_SQL);) {
            preparedStatement.setInt(1, productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Product product = new Product();

            while (resultSet.next()) {
                product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
            }

            return product;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int addProduct(Product product) {
        try (Connection connection = dataSource.getConnection();
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

    @Override
    public int deleteProductById(int productId) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL);) {
            preparedStatement.setInt(1, productId);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int editProduct(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(EDIT_PRODUCT_SQL);) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setString(4, product.getPicturePath());
            preparedStatement.setInt(5, product.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
