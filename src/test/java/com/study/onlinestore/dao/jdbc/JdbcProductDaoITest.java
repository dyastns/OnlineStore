package com.study.onlinestore.dao.jdbc;

import com.study.onlinestore.entity.Product;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class JdbcProductDaoITest {
    @Test
    public void testGetAll() {
        //prepare
        JdbcProductDao jdbcProductDao = new JdbcProductDao();

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