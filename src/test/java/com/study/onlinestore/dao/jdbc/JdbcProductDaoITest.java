package com.study.onlinestore.dao.jdbc;

import com.study.applicationcontext.ApplicationContext;
import com.study.applicationcontext.impl.ClassPathApplicationContext;
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
        ApplicationContext context = new ClassPathApplicationContext("context.xml");
        dataSource = context.getBean(PGSimpleDataSource.class);
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
}