package com.study.onlinestore.web.filter;

import com.study.onlinestore.entity.User;
import com.study.onlinestore.entity.UserType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";

        User user = (User) session.getAttribute("user");

        boolean isAdmin = (user != null) && (UserType.ADMIN.equals(user.getUserType()));
        boolean isUser = (user != null) && (UserType.USER.equals(user.getUserType()));

        if (isUser || isAdmin) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendRedirect(loginURI);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
