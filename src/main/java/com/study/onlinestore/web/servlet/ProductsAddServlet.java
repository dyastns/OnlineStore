package com.study.onlinestore.web.servlet;

import com.study.onlinestore.entity.Product;
import com.study.onlinestore.service.ProductService;
import com.study.onlinestore.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductsAddServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        Map<String, Object> parameters = new HashMap<>();

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addproduct.html", parameters);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> requestParameters = request.getParameterMap();
        Product product = new Product();
        product.setName(requestParameters.get("name")[0]);
        product.setDescription(requestParameters.get("description")[0]);
        product.setPrice(Double.parseDouble(requestParameters.get("price")[0]));
        product.setPicturePath(requestParameters.get("picturePath")[0]);
        int result = productService.addProduct(product);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("product", product);
        parameters.put("result", result);

        response.setContentType("text/html;charset=utf-8");

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("addproduct.html", parameters);
        response.getWriter().write(page);
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
