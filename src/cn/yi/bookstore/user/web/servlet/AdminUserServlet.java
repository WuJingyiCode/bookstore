package cn.yi.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.yi.bookstore.util.servlet.BaseServlet;
import cn.yi.bookstore.user.domain.AdminUser;
import cn.yi.bookstore.user.service.AdminUserException;
import cn.yi.bookstore.user.service.AdminUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminUserServlet extends BaseServlet {
    private AdminUserService userService = new AdminUserService();

    public String login(HttpServletRequest request, HttpServletResponse response) {
        AdminUser form = CommonUtils.toBean(request.getParameterMap(), AdminUser.class);
        try {
            AdminUser admin = userService.login(form);
            request.getSession().setAttribute("admin", admin);
            return "r:/bookstore_war_exploded/adminjsps/index.jsp";
        } catch (AdminUserException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/login.jsp";
        }
    }
}
