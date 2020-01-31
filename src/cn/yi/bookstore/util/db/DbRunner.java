package cn.yi.bookstore.util.db;

import java.sql.SQLException;
import java.util.List;

/**
 * 对外提供执行SQL服务
 *
 * @author wujingyi
 */
public class DbRunner {

    public <T> T queryObject(String sql, ResultSetConvertor<T> convertor, Object... params) throws SQLException {
        return DbExecutor.instance.executeQuery(sql, convertor.firstRow2Object, params);
    }

    public <T> List<T> queryObjectList(String sql, ResultSetConvertor<T> convertor, Object... params) throws SQLException {
        return DbExecutor.instance.executeQuery(sql, convertor.rowByRow2Object, params);
    }

    public int update(String sql, Object... params) throws SQLException {
        return DbExecutor.instance.executeUpdate(sql, params);
    }

    public void insertBatch(String sql, Object[][] params) throws SQLException {
        DbExecutor.instance.executeInsertBatch(sql, params);
    }
}
