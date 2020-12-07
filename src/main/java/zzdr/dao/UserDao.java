package zzdr.dao;

import zzdr.domain.User;
import zzdr.domain.SharedUser;

import java.util.List;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 用户信息保存
     * @param user
     */
    public void save(User user);

    public User findByUsernameAndPassword(String getUsername, String getPassword);

    public void saveLocation(SharedUser sharedUser);

    public SharedUser findByShareUsername(String username);

    public List GetAllSharedUser(Integer needUsbType);

    public void incShareNumber(String username);

    public void saveShareNumber(User user);

    public void incneedNumber(String username);

    public void saveneedNumber(User user);

    public void removeshareUser(String username);

}
