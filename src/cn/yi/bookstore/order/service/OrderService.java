package cn.yi.bookstore.order.service;

import cn.yi.bookstore.order.bean.Order;
import cn.yi.bookstore.order.dao.OrderDao;
import cn.yi.bookstore.user.bean.User;
import cn.yi.bookstore.util.db.DbConnectionManager;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private OrderDao orderDao = new OrderDao();

    public void addOrder(Order order) {
        try {
            //开启事务
            DbConnectionManager.beginTransaction();
            orderDao.addOrder(order);
            orderDao.addOrderItemList(order.getOrderItemList());
            //提交事务
            DbConnectionManager.commitTransaction();

        } catch (Exception e) {
            //回滚事务
            try {
                DbConnectionManager.rollbackTransaction();
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
            throw new RuntimeException(e);
        }
    }

    public List<Order> myOrders(User user) {
        return orderDao.findOrderByUser(user);
    }

    public Order loadOrder(String oid) {
        return orderDao.findOrderByOId(oid);
    }

    public void confirm(String oid) throws OrderException {
        int state = orderDao.getOrderState(oid);
        if (state != 3) {
            throw new OrderException("订单状态异常！");
        }
        orderDao.updateOrderState(oid, 4);
    }

    public void pay(String oid) throws OrderException {
        int state = orderDao.getOrderState(oid);
        if (state != 1) {
            throw new OrderException("订单状态异常");
        }
        orderDao.updateOrderState(oid, 2);
    }
}
