package cn.yi.bookstore.user.service;

import cn.yi.bookstore.user.dao.UserDao;
import cn.yi.bookstore.user.domain.User;

public class UserService {
    private UserDao userDao = new UserDao();

    public void register(User form) throws UserException {
        if (form == null) {
            throw new UserException("表单参数错误！");
        }
        //校验用户名
        User user = userDao.findByUsername(form.getUsername());
        if (user != null) {
            throw new UserException("用户名已被注册！");
        }
        //校验email
        user = userDao.findByUserEmail(form.getEmail());
        if (user != null) {
            throw new UserException("邮箱已被注册！");
        }
        //插入数据
        userDao.add(form);
    }

    public User login(User form) throws UserException {
        if (form == null) {
            throw new UserException("表单参数错误！");
        }
        String username = form.getUsername();
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UserException("用户不存在！");
        }
        if (!user.getPassword().equals(form.getPassword())) {
            throw new UserException("密码错误！");
        }
        if (!user.isState()) {
            throw new UserException("用户尚未激活！");
        }
        return user;
    }
}
