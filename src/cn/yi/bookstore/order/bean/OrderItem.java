package cn.yi.bookstore.order.bean;

import cn.yi.bookstore.book.bean.Book;
import cn.yi.bookstore.util.db.ResultSetConvertor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItem {
    private String iid;
    private int count;
    private double subtotalPrice;
    /** 所属订单 */
    private Order order;
    private Book book;

    public OrderItem() {

    }

    public OrderItem(String iid, int count, double subtotalPrice, Order order, Book book) {
        this.iid = iid;
        this.count = count;
        this.subtotalPrice = subtotalPrice;
        this.order = order;
        this.book = book;
    }

    public String getIid() {
        return iid;
    }

    public void setIid(String iid) {
        this.iid = iid;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(double subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public static final ResultSetConvertor<OrderItem> orderItemConvertor = new ResultSetConvertor<OrderItem>() {
        @Override
        public OrderItem convert(ResultSet rs) {
            try {
                String itemId = rs.getString("itemid");
                int count = rs.getInt("count");
                double subtotal = rs.getDouble("subtotal");
                String bid = rs.getString("bid");
                String bname = rs.getString("bname");
                String author = rs.getString("author");
                double price = rs.getDouble("price");
                String image = rs.getString("image");
                Book book = new Book();
                book.setBid(bid);
                book.setBname(bname);
                book.setAuthor(author);
                book.setPrice(price);
                book.setImage(image);

                String oid = rs.getString("oid");
                Order order = new Order();
                order.setOid(oid);
                return new OrderItem(itemId, count, subtotal, order, book);
            } catch (SQLException e) {
                throw new RuntimeException("Fail to convert OrderItem");
            }
        }
    };
}
