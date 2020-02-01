package cn.yi.bookstore.book.service;

import cn.itcast.commons.CommonUtils;
import cn.yi.bookstore.book.dao.BookDao;
import cn.yi.bookstore.book.bean.Book;

import java.util.List;

public class BookService {
    private BookDao bookDao = new BookDao();

    public List<Book> findAllBook() {
        return bookDao.findAllBook();
    }

    public List<Book> findBookByCategory(String cid) {
        return bookDao.findByCategory(cid);
    }

    public Book loadBookInfo(String bid) {
        return bookDao.findBookByBid(bid);
    }

    public void addBook(Book book) {
        book.setBid(CommonUtils.uuid());
        bookDao.addBook(book);
    }

    public void deleteBook(String bid) {
        bookDao.deleteBook(bid);
    }

    public void editBook(Book book) {
        bookDao.editBook(book);
    }
}
