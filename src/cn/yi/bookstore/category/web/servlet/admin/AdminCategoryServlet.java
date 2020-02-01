package cn.yi.bookstore.category.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.yi.bookstore.category.bean.Category;
import cn.yi.bookstore.category.service.CategoryException;
import cn.yi.bookstore.category.service.CategoryService;
import cn.yi.bookstore.util.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAllCategory(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("categoryList", categoryService.findAllCategory());
        return "f:/adminjsps/category/list.jsp";
    }

    public String add(HttpServletRequest request, HttpServletResponse response) {
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        category.setCid(CommonUtils.uuid());
        categoryService.addCategory(category);
        return findAllCategory(request, response);
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        try {
            categoryService.deleteCategory(cid);
        } catch (CategoryException e) {
            request.setAttribute("msg", e.getMessage());
            return "f:/adminjsps/msg.jsp";
        }
        return findAllCategory(request, response);
    }

    public String editPre(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        request.setAttribute("category", categoryService.loadCategory(cid));
        return "f:/adminjsps/category/mod.jsp";
    }

    public String edit(HttpServletRequest request, HttpServletResponse response) {
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        categoryService.editCategory(category);
        return findAllCategory(request, response);
    }
}
