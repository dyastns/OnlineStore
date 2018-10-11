package com.study.onlinestore.service.impl;

import com.study.onlinestore.dao.ProductDao;
import com.study.onlinestore.entity.Product;
import com.study.onlinestore.service.ProductService;

import java.util.List;

public class DefaultProductService implements ProductService {
    private ProductDao productDao;

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public int addProduct(Product product) {
        return productDao.addProduct(product);
    }

    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
