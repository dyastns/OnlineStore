package com.study.onlinestore.service;

import com.study.onlinestore.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    int addProduct(Product product);
}
