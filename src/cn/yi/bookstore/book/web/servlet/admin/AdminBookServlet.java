package cn.yi.bookstore.book.web.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.yi.bookstore.book.domain.Book;
import cn.yi.bookstore.book.service.BookService;
import cn.yi.bookstore.category.domain.Category;
import cn.yi.bookstore.category.service.CategoryService;
import cn.yi.bookstore.util.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    public String findAllBook(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("bookList", bookService.findAllBook());
        return "f:/adminjsps/book/list.jsp";
    }

    public String load(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        request.setAttribute("categoryList", categoryService.findAllCategory());
        request.setAttribute("book", bookService.loadBookInfo(bid));
        return "f:/adminjsps/book/desc.jsp";
    }

    public String addPre(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("categoryList", categoryService.findAllCategory());
        return "f:/adminjsps/book/add.jsp";
    }

    public String addBook(HttpServletRequest request, HttpServletResponse response) {
        Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        book.setCategory(category);
        bookService.addBook(book);
        return findAllBook(request, response);
    }

    public String deleteBook(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        bookService.deleteBook(bid);
        return findAllBook(request, response);
    }

    public String editBook(HttpServletRequest request, HttpServletResponse response) {
        Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
        Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
        book.setCategory(category);
        bookService.editBook(book);
        return findAllBook(request, response);
    }
}
