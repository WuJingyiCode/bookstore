package cn.yi.bookstore.category.domain;

import cn.yi.bookstore.util.db.ResultSetConvertor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Category {
    private String cid;
    private String cname;

    public Category() {
    }

    public Category(String cid, String cname) {
        this.cid = cid;
        this.cname = cname;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public static final ResultSetConvertor<Category> categoryConvertor = new ResultSetConvertor<Category>() {
        @Override
        public Category convert(ResultSet rs) {
            try {
                String cid = rs.getString("cid");
                String cname = rs.getString("cname");
                return new Category(cid, cname);
            } catch (SQLException e) {
                throw new RuntimeException("Fail to convert Category");
            }
        }
    };

}
