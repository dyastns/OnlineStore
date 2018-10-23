package com.study.onlinestore.web.servlet;

import com.study.onlinestore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDeleteServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] args = pathInfo.split("/");
        int productId = Integer.parseInt(args[1]);
        productService.deleteProductById(productId);
        response.sendRedirect("/products");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
