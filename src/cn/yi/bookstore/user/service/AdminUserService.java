package cn.yi.bookstore.user.service;

import cn.yi.bookstore.user.dao.AdminUserDao;
import cn.yi.bookstore.user.domain.AdminUser;

public class AdminUserService {
    private AdminUserDao userDao = new AdminUserDao();

    public AdminUser login(AdminUser form) throws AdminUserException {
        AdminUser admin = userDao.findAdminUserByName(form.getAdminName());
        if (admin == null) {
            throw new AdminUserException("用户不存在！");
        }
        if (!admin.getPassword().equals(form.getPassword())) {
            throw new AdminUserException("密码错误！");
        }
        return admin;
    }
}
