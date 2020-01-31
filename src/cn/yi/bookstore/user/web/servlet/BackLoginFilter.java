package cn.yi.bookstore.user.web.servlet;

import cn.yi.bookstore.user.domain.AdminUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class BackLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        AdminUser admin = (AdminUser) servletRequest.getSession().getAttribute("admin");
        if  (admin == null) {
            request.setAttribute("msg", "请先登录管理员账号！");
            request.getRequestDispatcher("/adminjsps/login.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) {

    }

}
