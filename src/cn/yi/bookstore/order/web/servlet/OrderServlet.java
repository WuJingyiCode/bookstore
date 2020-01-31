package cn.yi.bookstore.order.web.servlet;

import cn.itcast.commons.CommonUtils;
import cn.yi.bookstore.cart.domain.Cart;
import cn.yi.bookstore.cart.domain.CartItem;
import cn.yi.bookstore.util.servlet.BaseServlet;
import cn.yi.bookstore.order.domain.Order;
import cn.yi.bookstore.order.domain.OrderItem;
import cn.yi.bookstore.order.service.OrderException;
import cn.yi.bookstore.order.service.OrderService;
import cn.yi.bookstore.user.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class OrderServlet extends BaseServlet {
    private OrderService orderService = new OrderService();

    public String addOrder(HttpServletRequest request, HttpServletResponse response) {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        Order order = new Order();
        order.setOid(CommonUtils.uuid());
        order.setOrderTime(new Date());
        User owner = (User) request.getSession().getAttribute("user");
        order.setOwner(owner);
        //1表示未付款
        order.setState(1);
        order.setTotal(cart.getTotalPrice());
        Collection<CartItem> cartItemList = cart.getCartItems();
        List<OrderItem> orderItemList = new ArrayList<>(cartItemList.size());
        for (CartItem cartItem: cartItemList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setIid(CommonUtils.uuid());
            orderItem.setBook(cartItem.getBook());
            orderItem.setOrder(order);
            orderItem.setCount(cartItem.getCount());
            orderItem.setSubtotalPrice(cartItem.getSubtotalPrice());
            orderItemList.add(orderItem);
        }
        order.setOrderItemList(orderItemList);
        cart.clear();
        orderService.addOrder(order);
        request.setAttribute("order", order);
        return "/jsps/order/orderdesc.jsp";
    }

    public String myOrders(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("orderList", orderService.myOrders(user));
        return "f:/jsps/order/orderlist.jsp";
    }

    public String orderDesc(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        request.setAttribute("order", orderService.loadOrder(oid));
        return "f:/jsps/order/orderdesc.jsp";
    }

    public String confirm(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        try {
            orderService.confirm(oid);
            request.setAttribute("msg", "确认收货，交易成功！");
        } catch (OrderException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }

    public String pay(HttpServletRequest request, HttpServletResponse response) {
        String oid = request.getParameter("oid");
        try {
            orderService.pay(oid);
            request.setAttribute("msg", "付款成功，请等待店家发货！");
        } catch (OrderException e) {
            request.setAttribute("msg", e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }
}
