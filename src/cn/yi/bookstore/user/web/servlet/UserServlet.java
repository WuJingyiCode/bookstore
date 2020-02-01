package cn.yi.bookstore.user.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.yi.bookstore.cart.bean.Cart;
import cn.yi.bookstore.util.servlet.BaseServlet;
import cn.yi.bookstore.user.bean.User;
import cn.yi.bookstore.user.service.UserException;
import cn.yi.bookstore.user.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class UserServlet extends BaseServlet {
    private UserService userService = new UserService();

    public String register(HttpServletRequest request, HttpServletResponse response) {
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid() + CommonUtils.uuid());

        Map<String, String> errorMap = new HashMap<>();
        String username = form.getUsername();
        if (username == null || username.isEmpty()) {
            errorMap.put("username", "用户名不能为空！");
        } else if (username.length() < 3 || username.length() > 10) {
            errorMap.put("username", "用户名长度为3-10个字符");
        }
        String password = form.getPassword();
        if (password == null || password.isEmpty()) {
            errorMap.put("password", "密码不能为空！");
        } else if (password.length() < 3 || password.length() > 10) {
            errorMap.put("password", "密码长度为3-10个字符");
        }
        String email = form.getEmail();
        if (email == null || email.isEmpty()) {
            errorMap.put("email", "邮箱不能为空！");
        }
        //todo email
        /*else if (email.matches("\\w+@\\w+\\.\\w+")) {
            errorMap.put("email", "邮箱格式错误！");
        }*/

        if (errorMap.size() > 0) {
            request.setAttribute("errors", errorMap);
            request.setAttribute("form", form);
            return "f:/jsps/user/register.jsp";
        }

        try {
            userService.register(form);
        } catch (UserException e) {
            request.setAttribute("error", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/register.jsp";
        }

        //todo send email
        request.setAttribute("msg", "注册成功！请到尽快到邮箱激活！");
        return "f:/jsps/msg.jsp";
    }

    public String login(HttpServletRequest request, HttpServletResponse response) {
        User form = CommonUtils.toBean(request.getParameterMap(), User.class);
        try  {
            User user = userService.login(form);
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("cart", new Cart());
            return "r:/bookstore_war_exploded/index.jsp";
        } catch (UserException e) {
            request.setAttribute("msg", e.getMessage());
            request.setAttribute("form", form);
            return "f:/jsps/user/login.jsp";
        }
    }

    public String quit(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return "r:/bookstore_war_exploded/jsps/user/login.jsp";
    }
}
