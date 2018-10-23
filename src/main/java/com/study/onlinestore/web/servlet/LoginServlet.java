package com.study.onlinestore.web.servlet;

import com.study.onlinestore.entity.User;
import com.study.onlinestore.service.UserService;
import com.study.onlinestore.web.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");

        Map<String, Object> parameters = new HashMap<>();

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html", parameters);
        response.getWriter().write(page);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        User user = userService.getUserByNameAndPassword(name, password);

        if (user.getName() != null) {
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // !!! НЕ ВЛИЯЕТ !
            response.sendRedirect("/login");
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
