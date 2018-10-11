package com.study.onlinestore.dao.jdbc.mapper;

import com.study.onlinestore.entity.Product;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        //prepare
        ProductRowMapper productRowMapper = new ProductRowMapper();
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getInt("id")).thenReturn(456);
        when(mockResultSet.getString("name")).thenReturn("test_name");
        when(mockResultSet.getString("description")).thenReturn("test_description");
        when(mockResultSet.getDouble("price")).thenReturn(1234.56);
        when(mockResultSet.getString("picturePath")).thenReturn("test_path");

        //when
        Product actualProduct = productRowMapper.mapRow(mockResultSet);

        //then
        assertNotNull(actualProduct);
        assertEquals(456, actualProduct.getId());
        assertEquals("test_name", actualProduct.getName());
        assertEquals("test_description", actualProduct.getDescription());
        assertEquals(1234.56, actualProduct.getPrice(),0.001);
        assertEquals("test_path", actualProduct.getPicturePath());
    }
}