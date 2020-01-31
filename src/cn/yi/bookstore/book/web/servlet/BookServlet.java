package cn.yi.bookstore.book.web.servlet;

import cn.yi.bookstore.book.service.BookService;
import cn.yi.bookstore.util.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    public String findAllBook(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("bookList", bookService.findAllBook());
        return "f:/jsps/book/booklist.jsp";
    }

    public String findBookByCategory(HttpServletRequest request, HttpServletResponse response) {
        String cid = request.getParameter("cid");
        request.setAttribute("bookList", bookService.findBookByCategory(cid));
        return "f:/jsps/book/booklist.jsp";
    }

    public String loadBookInfo(HttpServletRequest request, HttpServletResponse response) {
        String bid = request.getParameter("bid");
        request.setAttribute("book", bookService.loadBookInfo(bid));
        return "f:/jsps/book/bookdesc.jsp";
    }
}
