package zzdr.service;

import zzdr.domain.User;

public interface UserService {
    /**
     * 用户注册方法
     * @param user
     * @return
     */
    boolean register(User user);
    User login(User user);


}
