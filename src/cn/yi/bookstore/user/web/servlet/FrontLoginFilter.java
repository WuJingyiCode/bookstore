package cn.yi.bookstore.user.web.servlet;

import cn.yi.bookstore.user.bean.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class FrontLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("msg", "请先登录！");
            request.getRequestDispatcher("/jsps/user/login.jsp").forward(request, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) {

    }

}
