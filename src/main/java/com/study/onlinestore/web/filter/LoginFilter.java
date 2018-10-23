package com.study.onlinestore.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);
        String loginURI = request.getContextPath() + "/login";
        String assetsURI = request.getContextPath() + "/assets/";

        boolean isAuth = (session != null) && (session.getAttribute("user") != null);
        boolean isLoginRequest = request.getRequestURI().equals(loginURI);
        boolean isAssetsRequest = request.getRequestURI().startsWith(assetsURI);

        if (isAuth || isLoginRequest || isAssetsRequest) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // !!! НЕ ВЛИЯЕТ !
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
