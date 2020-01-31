package cn.yi.bookstore.category.dao;

import cn.yi.bookstore.category.domain.Category;
import cn.yi.bookstore.util.db.DbRunner;

import java.sql.SQLException;
import java.util.List;

public class CategoryDao {
    private DbRunner dbRunner = new DbRunner();

    public List<Category> findAllCategory() {
        String sql = "SELECT * FROM category";
        try {
            return dbRunner.queryObjectList(sql, Category.categoryConvertor);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCategory(Category category) {
        try {
            String sql = "INSERT INTO category VALUES(?, ?)";
            dbRunner.update(sql, category.getCid(), category.getCname());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCategory(String cid) {
        try {
            String sql = "DELETE FROM category WHERE cid = ?";
            dbRunner.update(sql, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category findCategoryByCid(String cid) {
        try {
            String sql = "SELECT * FROM category WHERE cid = ?";
            return dbRunner.queryObject(sql, Category.categoryConvertor, cid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCategory(Category category) {
        try {
            String sql = "UPDATE category SET cname = ? WHERE cid = ?";
            dbRunner.update(sql, category.getCname(), category.getCid());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
