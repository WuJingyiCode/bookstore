package cn.yi.bookstore.order.domain;

import cn.yi.bookstore.user.domain.User;
import cn.yi.bookstore.util.db.ResultSetConvertor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Order {
    private String oid;
    private Date orderTime;
    private double total;
    /** 1.未付款  2.已付款，未发货
     * 3.已发货，未确认  4.交易成功  */
    private int state;
    /** 订单所有者 */
    private User owner;
    /** 收货地址 */
    private String address;
    private List<OrderItem> orderItemList;

    public Order() {

    }

    public Order(String oid, Date orderTime, double total, int state, User owner, String address) {
        this.oid = oid;
        this.orderTime = orderTime;
        this.total = total;
        this.state = state;
        this.owner = owner;
        this.address = address;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static final ResultSetConvertor<Order> orderConvertor = new ResultSetConvertor<Order>() {
        @Override
        public Order convert(ResultSet rs) {
            try {
                String oid = rs.getString("oid");
                double total = rs.getDouble("total");
                Date orderTime = rs.getDate("ordertime");
                int state = rs.getInt("state");
                String address = rs.getString("address");
                String uid = rs.getString("uid");
                User owner = new User();
                owner.setUid(uid);
                return new Order(oid, orderTime, total, state, owner, address);
            } catch (SQLException e) {
                throw new RuntimeException("Fail to convert Order!");
            }
        }
    };
}
