package com.study.onlinestore.service;

import com.study.onlinestore.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAll();

    Product getProductById(int productId);

    int addProduct(Product product);

    int deleteProductById(int productId);

    int editProduct(Product product);
}
