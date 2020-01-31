package cn.yi.bookstore.util.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 使用c3p0连接池，提供连接获取，释放，事务的开启，提交，回滚功能。
 */
public class DbConnectionManager {
    /**
     * 使用c3p0连接池，需提供c3p0-config.xml配置文件
     */
    private static final DataSource ds = new ComboPooledDataSource();

    private static final ThreadLocal<Connection> tl = new ThreadLocal<>();

    /**
     * 获取连接
     * @return
     */
    public static Connection getConnection() throws SQLException {
        Connection con = tl.get();
        //返回事务的连接
        if (con != null) {
            return con;
        }
        return ds.getConnection();
    }

    /**
     * 开启事务
     * @throws SQLException
     */
    public static void begainTransaction() throws SQLException {
        Connection con = tl.get();
        if  (con != null) {
            throw new SQLException("事务已开启，请勿重复开启事务！");
        }
        con = ds.getConnection();
        //开启事务
        con.setAutoCommit(false);
        tl.set(con);
    }

    /**
     * 提交事务
     * @throws SQLException
     */
    public static void commitTransaction() throws SQLException {
        Connection con = tl.get();
        if (con == null) {
            throw new SQLException("事务未开启！");
        }
        //提交事务
        con.commit();
        con.close();
        //事务结束，移除事务连接
        tl.remove();
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public static void rollbackTransaction() throws SQLException {
        Connection con = tl.get();
        if (con == null) {
            throw new SQLException("事务未开启！");
        }
        //回滚
        con.rollback();
        con.close();
        //事务结束，移除事务连接
        tl.remove();
    }

    /**
     * 释放连接
     * 事务的连接应该由<code>commitTransaction()</code>或<code>rollbackTransaction()</code>去释放。
     * @param connection
     * @throws SQLException
     */
    public static void releaseConnection(Connection connection) throws SQLException {
        Connection con = tl.get();
        //要释放的连接
        if (con != connection) {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } else {
            throw new SQLException("无法释放事务连接");
        }
    }
}
