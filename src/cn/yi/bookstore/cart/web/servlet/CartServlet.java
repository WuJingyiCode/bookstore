package cn.yi.bookstore.cart.web.servlet;

import cn.yi.bookstore.book.domain.Book;
import cn.yi.bookstore.book.service.BookService;
import cn.yi.bookstore.cart.domain.Cart;
import cn.yi.bookstore.cart.domain.CartItem;
import cn.yi.bookstore.util.servlet.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CartServlet extends BaseServlet {

    /**
     *
     * @param request
     * @param response
     */
    public String add(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //登录才能添加购物车
        if (cart != null) {
            String bid = request.getParameter("bid");
            Book book = new BookService().loadBookInfo(bid);
            int count = Integer.valueOf(request.getParameter("count"));
            cart.add(new CartItem(book, count));
        }
        return "f:/jsps/cart/cartlist.jsp";
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        String bid = request.getParameter("bid");
//        Book book = new BookService().loadBookInfo(bid);
        cart.delete(bid);
        return "f:/jsps/cart/cartlist.jsp";
    }

    /**
     * 清空购物车
     * @param request
     * @param response
     * @return
     */
    public String clear(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        cart.clear();
        return "f:/jsps/cart/cartlist.jsp";
    }
}
