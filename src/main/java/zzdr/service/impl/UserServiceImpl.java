package zzdr.service.impl;

import zzdr.dao.UserDao;
import zzdr.dao.impl.UserDaoImpl;
import zzdr.domain.User;
import zzdr.service.UserService;

public class UserServiceImpl implements UserService {
    /**
     * 用户注册方法
     * @param user
     * @return
     */
    private UserDao userDao = new UserDaoImpl();
    @Override
    public boolean register(User user) {
        User u = userDao.findByUsername(user.getUsername());
        if (u!=null){
            //用户名存在则注册失败
            return false;
        }else {
            userDao.save(user);
            return true;
        }
    }

    @Override
    public User login(User user) {

        return userDao.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
}
