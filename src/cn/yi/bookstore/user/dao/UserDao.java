package cn.yi.bookstore.user.dao;

import cn.yi.bookstore.user.bean.User;
import cn.yi.bookstore.util.db.DbRunner;

import java.sql.SQLException;

public class UserDao {

    private DbRunner dbRunner = new DbRunner();

    /**
     * 通过用户名查找用户
     * @param username 用户名
     * @return 结果
     */
    public User findByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try {
            return dbRunner.queryObject(sql, User.userConvertor, username);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按email查找用户
     * @param email 邮箱
     * @return 结果
     */
    public User findByUserEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try {
            return dbRunner.queryObject(sql, User.userConvertor, email);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 插入用户
     * @param user 用户
     */
    public void add(User user) {
        String sql = "INSERT INTO user VALUES(?, ?, ?, ?, ?, ?)";
        try {
            Object[] params = new Object[]{user.getUid(), user.getUsername(), user.getPassword(), user.getEmail(), 1, user.getCode()};
            dbRunner.update(sql, User.userConvertor, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
