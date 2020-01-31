package cn.yi.bookstore.order.service;

public class OrderException extends Exception {
    public OrderException() {
        super();
    }

    public OrderException(String message) {
        super(message);
    }
}
