package com.study.onlinestore;

import com.study.onlinestore.dao.ProductDao;
import com.study.onlinestore.dao.jdbc.JdbcProductDao;
import com.study.onlinestore.service.impl.DefaultProductService;
import com.study.onlinestore.web.servlet.AssetsServlet;
import com.study.onlinestore.web.servlet.ProductsAddServlet;
import com.study.onlinestore.web.servlet.ProductsServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {
        //config dao
        ProductDao productDao = new JdbcProductDao();

        //config service
        DefaultProductService productService = new DefaultProductService();
        productService.setProductDao(productDao);

        //config servlet
        ProductsServlet productsServlet = new ProductsServlet();
        productsServlet.setProductService(productService);

        ProductsAddServlet productsAddServlet = new ProductsAddServlet();
        productsAddServlet.setProductService(productService);


        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(productsServlet), "/");
        contextHandler.addServlet(new ServletHolder(productsServlet), "/products");
        contextHandler.addServlet(new ServletHolder(productsAddServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
    }
}
