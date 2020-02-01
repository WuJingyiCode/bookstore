package cn.yi.bookstore.category.service;

import cn.yi.bookstore.book.dao.BookDao;
import cn.yi.bookstore.category.dao.CategoryDao;
import cn.yi.bookstore.category.bean.Category;

import java.util.List;

public class CategoryService {
    private CategoryDao categoryDao = new CategoryDao();
    private BookDao bookDao = new BookDao();

    public List<Category> findAllCategory() {
        return categoryDao.findAllCategory();
    }

    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    public void deleteCategory(String cid) throws CategoryException {
        int count = bookDao.getBookNumByCid(cid);
        if (count > 0) {
            throw new CategoryException("请先删除该分类下的图书！");
        }
        categoryDao.deleteCategory(cid);
    }

    public Category loadCategory(String cid) {
        return categoryDao.findCategoryByCid(cid);
    }

    public void editCategory(Category category) {
        categoryDao.updateCategory(category);
    }
}
