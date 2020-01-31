package cn.yi.bookstore.order.dao;

import cn.itcast.jdbc.TxQueryRunner;
import cn.yi.bookstore.book.domain.Book;
import cn.yi.bookstore.order.domain.Order;
import cn.yi.bookstore.order.domain.OrderItem;
import cn.yi.bookstore.user.domain.User;
import cn.yi.bookstore.util.db.DbRunner;
import cn.yi.bookstore.util.db.ResultSetConvertor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDao {
    private DbRunner dbRunner = new DbRunner();

    public void addOrder(Order order) {
       try {
           String sql = "INSERT INTO orders VALUES(?, ?, ?, ?, ?, ?)";
           //处理参数
           User owner = order.getOwner();
           String uid = owner == null ? "0" : owner.getUid();
           Timestamp timestamp = new Timestamp(order.getOrderTime().getTime());
           Object[] params = new Object[] {order.getOid(), order.getTotal(), timestamp, order.getState(), order.getAddress(), uid};
           dbRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOrderItemList(List<OrderItem> orderItemList) {
        String sql = "INSERT INTO orderitem VALUES(?, ?, ?, ?, ?)";
        try  {
            int size = orderItemList.size();
            Object[][] params = new Object[size][];
            for (int i = 0; i < size; i++) {
                OrderItem orderItem = orderItemList.get(i);
                params[i] = new Object[] {orderItem.getIid(), orderItem.getCount(), orderItem.getSubtotalPrice(), orderItem.getBook().getBid(), orderItem.getOrder().getOid()};
            }
            dbRunner.insertBatch(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> findOrderByUser(User user) {
        try {
            String sql = "SELECT * FROM orders WHERE uid = ?";
            Object[] params = new Object[]{user.getUid()};
            List<Order> orderList = dbRunner.queryObjectList(sql, Order.orderConvertor, params);
            //todo 批量操作
            for (Order order: orderList) {
                loadOrderItemList(order);
            }
            return orderList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Order findOrderByOId(String oid) {
        try {
            String sql = "SELECT * FROM orders WHERE oid = ?";
            Order order = dbRunner.queryObject(sql, Order.orderConvertor, oid);
            loadOrderItemList(order);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadOrderItemList(Order order) throws SQLException {
        String sql = "SELECT * FROM orderitem oi INNER JOIN book b WHERE oi.bid = b.bid AND oid = ?";
        String oid = order.getOid();
        List<OrderItem> orderItemList = dbRunner.queryObjectList(sql, OrderItem.orderItemConvertor, oid);
        /*List<Map<String, Object>> listMap = qr.query(sql, new MapListHandler(), oid);
        List<OrderItem> orderItemList = new ArrayList<>(listMap.size());
        for (Map<String, Object> map: listMap) {
            OrderItem orderItem = new OrderItem();
            orderItem.setIid((String) map.get("itemid"));
            orderItem.setCount((int) map.get("count"));
            orderItem.setOrder(order);
            orderItem.setSubtotalPrice(((BigDecimal) map.get("subtotal")).doubleValue());
            Book book = new Book();
            book.setBid((String) map.get("bid"));
            book.setAuthor((String) map.get("author"));
            book.setBname((String) map.get("bname"));
            book.setImage((String) map.get("image"));
            book.setPrice(((BigDecimal) map.get("price")).doubleValue());
            orderItem.setBook(book);
            orderItemList.add(orderItem);
        }*/
        order.setOrderItemList(orderItemList);
    }

    public int getOrderState(String oid) {
        try {
            String sql = "SELECT state FROM orders WHERE oid = ?";
            return (Integer) dbRunner.queryObject(sql, ResultSetConvertor.INTEGER_CONVERTOR, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderState(String oid, int state) {
        try {
            String sql = "UPDATE orders SET state = ? WHERE oid = ?";
            dbRunner.update(sql, state, oid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
