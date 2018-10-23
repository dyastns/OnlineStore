package com.study.onlinestore.web.servlet;

import com.study.onlinestore.entity.Product;
import com.study.onlinestore.entity.User;
import com.study.onlinestore.service.ProductService;
import com.study.onlinestore.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProductEditServlet extends HttpServlet {
    private ProductService productService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        String pathInfo = request.getPathInfo();
        String[] args = pathInfo.split("/");
        int productId = Integer.parseInt(args[1]);

        Product product = productService.getProductById(productId);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("product", product);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        parameters.put("user", user);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("editproduct.html", parameters);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> requestParameters = request.getParameterMap();

        Product product = new Product();
        product.setId(Integer.parseInt(requestParameters.get("id")[0]));
        product.setName(requestParameters.get("name")[0]);
        product.setDescription(requestParameters.get("description")[0]);
        String strPrice = requestParameters.get("price")[0].replaceAll("[^0-9,.]","").replace(',', '.');
        product.setPrice(Double.parseDouble(strPrice));
        product.setPicturePath(requestParameters.get("picturePath")[0]);

        productService.editProduct(product);

        response.sendRedirect("/products");
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
