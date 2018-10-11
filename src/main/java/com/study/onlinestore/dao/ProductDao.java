package com.study.onlinestore.dao;

import com.study.onlinestore.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product> getAll();

    int addProduct(Product product);
}
