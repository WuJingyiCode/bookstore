package cn.yi.bookstore.util.db;

import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 不对外提供服务，只对<code>DbRunner</code>提供服务
 */
class DbExecutor {
    static final DbExecutor instance = new DbExecutor();

    /**
     * 返回什么，List还是单个Object，由resultSetHandler决定
     * @param sql
     * @param resultSetHandler
     * @param params
     * @param <T>
     * @return
     * @throws SQLException
     */
    <T> T executeQuery(String sql, ResultSetHandler<T> resultSetHandler, Object... params) throws SQLException {
        Connection con = DbConnectionManager.getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
        fillPrepareStatement(pstm, params);
        ResultSet rs = pstm.executeQuery();
        T result = resultSetHandler.handle(rs);
        DbConnectionManager.releaseConnection(con);
        return result;
    }

    int executeUpdate(String sql, Object... params) throws SQLException {
        Connection con = DbConnectionManager.getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
        fillPrepareStatement(pstm, params);
        int result = pstm.executeUpdate();
        DbConnectionManager.releaseConnection(con);
        return result;
    }

    private void fillPrepareStatement(PreparedStatement pstm, Object... params) throws SQLException {
        int paramSize = params.length;
        for (int i = 0; i < paramSize; i++) {
            pstm.setObject(i + 1, params[i]);
        }
    }

    void executeInsertBatch(String sql, Object[][] params) throws SQLException {
        Connection con = DbConnectionManager.getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
        for (Object[] param : params) {
            fillPrepareStatement(pstm, param);
            pstm.addBatch();
        }
        pstm.execute();
    }
}
