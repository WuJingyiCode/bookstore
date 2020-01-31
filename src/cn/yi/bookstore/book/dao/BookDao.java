package cn.yi.bookstore.book.dao;

import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import cn.yi.bookstore.book.domain.Book;
import cn.yi.bookstore.category.domain.Category;
import cn.yi.bookstore.util.db.DbRunner;
import cn.yi.bookstore.util.db.ResultSetConvertor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class BookDao {

    private DbRunner dbRunner = new DbRunner();

    public List<Book> findAllBook() {
        String sql = "SELECT * FROM book";
        try {
            return dbRunner.queryObjectList(sql, Book.bookConvertor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Book> findByCategory(String cid) {
        String sql = "SELECT * FROM book WHERE cid = ?";
        try {
            return dbRunner.queryObjectList(sql, Book.bookConvertor, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Book findBookByBid(String bid) {
        String sql = "SELECT * FROM book WHERE bid = ?";
        try {
            return dbRunner.queryObject(sql, Book.bookConvertor, bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getBookNumByCid(String cid) {
        try {
            String sql = "SELECT count(*) FROM book WHERE cid = ?";
            return dbRunner.queryObject(sql, ResultSetConvertor.INTEGER_CONVERTOR, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBook(Book book) {
        try {
            String sql = "INSERT INTO book VALUES(?, ?, ?, ?, ? ,?)";
            dbRunner.update(sql, book.getBid(), book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(), book.getCategory().getCid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteBook(String bid) {
        try {
            String sql = "DELETE FROM book WHERE bid = ?";
            dbRunner.update(sql, bid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editBook(Book book) {
        try {
            String sql = "UPDATE book SET bname = ?, price = ?, author = ?, image = ?, cid = ? WHERE bid = ?";
            dbRunner.update(sql, book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(), book.getCategory().getCid(), book.getBid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
