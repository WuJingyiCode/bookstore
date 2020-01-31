package cn.yi.bookstore.user.domain;

import cn.yi.bookstore.util.db.ResultSetConvertor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    /** 用户ID */
    private String uid;
    /** 用户名 */
    private String username;
    /** 密码 todo char[]*/
    private String password;
    /** 邮箱 */
    private String email;
    /** 激活码 */
    private String code;
    /** 是否激活 */
    private boolean state;

    /**
     * CommonUtil.toBean()需要用到
     */
    public User() {

    }

    public User(String uid, String username, String password, String email, String code, boolean state) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.code = code;
        this.state = state;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public static ResultSetConvertor<User> userConvertor = new ResultSetConvertor<User>() {
        @Override
        public User convert(ResultSet rs) {
            try {
                String uid = rs.getString("uid");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String email = rs.getString("email");
                String code = rs.getString("code");
                boolean state = rs.getInt("state") == 1;
                return new User(uid, username, password, email, code, state);
            } catch (SQLException e) {
                throw new RuntimeException("Fail to convert User");
            }
        }
    };
}
