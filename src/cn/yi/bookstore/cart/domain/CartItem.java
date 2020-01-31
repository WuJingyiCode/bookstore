package cn.yi.bookstore.cart.domain;

import cn.yi.bookstore.book.domain.Book;

public class CartItem {
    private Book book;
    private int count;

    public CartItem(Book book, int count) {
        this.book = book;
        this.count = count;
    }

    /**
     * 小计
     * @return
     */
    public double getSubtotalPrice() {
        return book.getPrice() * count;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrCount(int count) {
        this.count += count;
    }
}
