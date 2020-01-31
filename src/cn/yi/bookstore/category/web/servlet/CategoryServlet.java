package cn.yi.bookstore.category.web.servlet;

import cn.yi.bookstore.category.service.CategoryService;
import cn.yi.bookstore.util.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAllCategory(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("categoryList", categoryService.findAllCategory());
        return "f:/jsps/left.jsp";
    }
}
