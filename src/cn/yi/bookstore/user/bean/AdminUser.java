package cn.yi.bookstore.user.bean;

import cn.yi.bookstore.util.db.ResultSetConvertor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUser {
    private String auid;
    private String adminName;
    private String password;

    /**
     * CommonUtil.toBean()需要用到
     */
    public AdminUser() {

    }

    public AdminUser(String auid, String adminName, String password) {
        this.auid = auid;
        this.adminName = adminName;
        this.password = password;
    }

    public String getAuid() {
        return auid;
    }

    public void setAuid(String auid) {
        this.auid = auid;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static ResultSetConvertor<AdminUser> adminConvertor = new ResultSetConvertor<AdminUser>() {
        @Override
        public AdminUser convert(ResultSet rs) {
            try {
                String auid = rs.getString("auid");
                String adminname = rs.getString("adminname");
                String password = rs.getString("password");
                return new AdminUser(auid, adminname, password);
            } catch (SQLException e) {
                throw new RuntimeException("Fail to convert AdminUser!");
            }
        }
    };
}
