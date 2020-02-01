package cn.yi.bookstore.book.bean;

import cn.yi.bookstore.category.bean.Category;
import cn.yi.bookstore.util.db.ResultSetConvertor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Book {
    private String bid;
    private String bname;
    private double price;
    private String author;
    private String image;
    private Category category;

    public Book() {

    }

    public Book(String bid, String bname, double price, String author, String image, Category category) {
        this.bid = bid;
        this.bname = bname;
        this.price = price;
        this.author = author;
        this.image = image;
        this.category = category;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static ResultSetConvertor<Book> bookConvertor  = new ResultSetConvertor<Book>() {
        @Override
        public Book convert(ResultSet rs) {
            try {
                String bid = rs.getString("bid");
                String bname = rs.getString("bname");
                double price = rs.getDouble("price");
                String author = rs.getString("author");
                String image = rs.getString("image");
                String cid = rs.getString("cid");
                Category category = new Category();
                category.setCid(cid);
                return new Book(bid, bname, price, author, image, category);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    };
}
