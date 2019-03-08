package com.study.onlinestore;

import com.study.applicationcontext.ApplicationContext;
import com.study.applicationcontext.impl.ClassPathApplicationContext;
import com.study.onlinestore.service.ProductService;
import com.study.onlinestore.service.UserService;
import com.study.onlinestore.web.filter.AdminFilter;
import com.study.onlinestore.web.filter.LoginFilter;
import com.study.onlinestore.web.filter.UserFilter;
import com.study.onlinestore.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.EnumSet;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathApplicationContext("context.xml");
        ProductService productService = context.getBean(ProductService.class);
        UserService userService = context.getBean(UserService.class);

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

    private static Properties getAppProperties() {
        String propertiesLocation = System.getProperty("properties.location");
        if (propertiesLocation != null) {
            File file = new File(propertiesLocation, "application.properties");
            Properties properties = new Properties();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                properties.load(reader);
            } catch (Exception e) {
                throw new RuntimeException("File with properties for application couldn't be read at: " + file);
            }
            return properties;
        } else {
            throw new RuntimeException("Properties for application was not specified.");
        }
    }
}
