package com.study.onlinestore.dao.jdbc;

import com.study.onlinestore.entity.Product;
import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class JdbcProductDaoITest {
    private static PGSimpleDataSource dataSource;

    @BeforeClass
    public static void oneTimeSetUp() {
        // one-time initialization code
        dataSource = new PGSimpleDataSource();

        Properties properties = getAppProperties();

        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));
    }

    @Test
    public void testGetAll() {
        //prepare
        JdbcProductDao jdbcProductDao = new JdbcProductDao(dataSource);

        //when
        List<Product> actualProducts = jdbcProductDao.getAll();

        //then
        assertNotNull(actualProducts);
        assertFalse(actualProducts.isEmpty());

        for (Product product : actualProducts) {
            assertNotNull(product);
            assertNotNull(product.getId());
            assertNotNull(product.getName());
            System.out.println(product);
        }
    }

    private static Properties getAppProperties() {
        String propertiesLocation = System.getProperty("properties.location");
        if (propertiesLocation != null) {
            File file = new File(propertiesLocation, "application.properties");
            Properties properties = new Properties();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                properties.load(reader);
            } catch (Exception e) {
                throw new RuntimeException("File with properties for application couldn't be read at: " + file);
            }
            return properties;
        } else {
            throw new RuntimeException("Properties for application was not specified.");
        }
    }
}