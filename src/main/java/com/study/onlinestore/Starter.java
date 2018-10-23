package com.study.onlinestore;

import com.study.onlinestore.dao.ProductDao;
import com.study.onlinestore.dao.UserDao;
import com.study.onlinestore.dao.jdbc.JdbcProductDao;
import com.study.onlinestore.dao.jdbc.JdbcUserDao;
import com.study.onlinestore.service.impl.DefaultProductService;
import com.study.onlinestore.service.impl.DefaultUserService;
import com.study.onlinestore.web.filter.AdminFilter;
import com.study.onlinestore.web.filter.LoginFilter;
import com.study.onlinestore.web.filter.UserFilter;
import com.study.onlinestore.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Starter {
    public static void main(String[] args) throws Exception {
        //config dao
        ProductDao productDao = new JdbcProductDao();
        UserDao userDao = new JdbcUserDao();

        //config service
        DefaultProductService productService = new DefaultProductService();
        productService.setProductDao(productDao);

        DefaultUserService userService = new DefaultUserService();
        userService.setUserDao(userDao);

        //config servlet
        ProductsServlet productsServlet = new ProductsServlet();
        productsServlet.setProductService(productService);

        ProductAddServlet productAddServlet = new ProductAddServlet();
        productAddServlet.setProductService(productService);

        ProductDeleteServlet productDeleteServlet = new ProductDeleteServlet();
        productDeleteServlet.setProductService(productService);

        ProductEditServlet productEditServlet = new ProductEditServlet();
        productEditServlet.setProductService(productService);

        LoginServlet loginServlet = new LoginServlet();
        loginServlet.setUserService(userService);

        //config filter
        AdminFilter adminFilter = new AdminFilter();
        UserFilter userFilter = new UserFilter();

        //config ServletContextHandler
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        //add servlet
        contextHandler.addServlet(new ServletHolder(productsServlet), "/");
        contextHandler.addServlet(new ServletHolder(productsServlet), "/products");
        contextHandler.addServlet(new ServletHolder(productAddServlet), "/product/add");
        contextHandler.addServlet(new ServletHolder(productDeleteServlet), "/product/delete/*");
        contextHandler.addServlet(new ServletHolder(productEditServlet), "/product/edit/*");
        contextHandler.addServlet(new ServletHolder(new AssetsServlet()), "/assets/*");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");
        contextHandler.addServlet(new ServletHolder(new LogoutServlet()), "/logout");
        //add filter
        contextHandler.addFilter(new FilterHolder(new LoginFilter()), "/*", EnumSet.allOf(DispatcherType.class));
        contextHandler.addFilter(new FilterHolder(adminFilter), "/product/add", EnumSet.allOf(DispatcherType.class));
        contextHandler.addFilter(new FilterHolder(adminFilter), "/product/delete/*", EnumSet.allOf(DispatcherType.class));
        contextHandler.addFilter(new FilterHolder(adminFilter), "/product/edit/*", EnumSet.allOf(DispatcherType.class));
        contextHandler.addFilter(new FilterHolder(userFilter), "/", EnumSet.allOf(DispatcherType.class));
        contextHandler.addFilter(new FilterHolder(userFilter), "/products", EnumSet.allOf(DispatcherType.class));

        //config server
        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
    }
}
