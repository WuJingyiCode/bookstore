package cn.yi.bookstore.user.dao;

import cn.yi.bookstore.user.domain.AdminUser;
import cn.yi.bookstore.util.db.DbRunner;

import java.sql.SQLException;

public class AdminUserDao {

    private DbRunner dbRunner = new DbRunner();

    public AdminUser findAdminUserByName(String name) {
        try {
            String sql = "SELECT * FROM adminuser WHERE adminname = ?";
            return dbRunner.queryObject(sql, AdminUser.adminConvertor, name);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
