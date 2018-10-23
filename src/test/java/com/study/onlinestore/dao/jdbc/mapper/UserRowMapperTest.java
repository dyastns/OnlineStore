package com.study.onlinestore.dao.jdbc.mapper;

import com.study.onlinestore.entity.User;
import com.study.onlinestore.entity.UserType;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {
    @Test
    public void testMapRow() throws SQLException {
        //prepare
        UserRowMapper userRowMapper = new UserRowMapper();
        ResultSet mockResultSet = mock(ResultSet.class);
        when(mockResultSet.getString("name")).thenReturn("test_name");
        when(mockResultSet.getString("password")).thenReturn("test_pass");
        when(mockResultSet.getString("userType")).thenReturn("GUEST");

        //when
        User actualUser = userRowMapper.mapRow(mockResultSet);

        //then
        assertNotNull(actualUser);
        assertEquals("test_name", actualUser.getName());
        assertEquals("test_pass", actualUser.getPassword());
        assertEquals(UserType.GUEST, actualUser.getUserType());
    }
}