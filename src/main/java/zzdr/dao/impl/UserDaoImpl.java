package zzdr.dao.impl;

//import org.junit.Test;
//import org.junit.Test;
import zzdr.dao.UserDao;
import zzdr.domain.User;
import zzdr.domain.SharedUser;
import zzdr.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
//        User user = null;
        try {
            //1.定义sql
            String sql = "SELECT * FROM register_user WHERE username = ?";
            //2.执行sql
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class),username);
            return user;
        } catch (Exception e) {
            return null;
        }

    }
    @Override
    public void save(User user) {
        //1.定义sql
//        System.out.println("find2ddd"+user);
        String sql = "insert into register_user(username,password,email,telephone,borrownumber,landnumber) values(?,?,?,?,?,?)";
        //2.执行sql
        template.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getTelephone(),
                0,
                0
        );

    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        try {
            //1.定义sql
            String sql = "SELECT * FROM register_user WHERE username = ? AND password = ?";
            //2.执行sql
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveLocation(SharedUser sharedUser) {
        String sql = "insert into share_user(username,telephone,email,usbType,latitude,longitude) values(?,?,?,?,?,?)";
        //2.执行sql
        template.update(sql,
                sharedUser.getUsername(),
                sharedUser.getTelephone(),
                sharedUser.getEmail(),
                sharedUser.getUsbType(),
                sharedUser.getLatitude(),
                sharedUser.getLongitude()
        );

    }

    @Override
    public SharedUser findByShareUsername(String username) {
        try {
            //1.定义sql
            String sql = "SELECT * FROM share_user WHERE username = ?";
            //2.执行sql
            SharedUser sharedUser = template.queryForObject(sql, new BeanPropertyRowMapper<SharedUser>(SharedUser.class),username);
            return sharedUser;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List GetAllSharedUser(Integer needUsbType) {

        String sql = "select * from share_user where usbType = " + needUsbType;
        List<SharedUser> list = template.query(sql, new BeanPropertyRowMapper<SharedUser>(SharedUser.class));
        return list;
    }

    @Override
    public void incShareNumber(String username) {
        User u = findByUsername(username);
        System.out.println("借出"+u.getLandNumber());
        Integer landNumber = u.getLandNumber() +1;
        System.out.println(landNumber);
        u.setLandNumber(landNumber);
        System.out.println("借出后"+u.getLandNumber());
        saveShareNumber(u);
        System.out.println("借出后2"+u.getLandNumber());
    }

    @Override
    public void saveShareNumber(User user) {
        String sql = "update register_user set landnumber = ? where username = ?";
        template.update(sql,user.getLandNumber(),user.getUsername());
    }

    @Override
    public void incneedNumber(String username) {
        User u = findByUsername(username);
        System.out.println("借入"+u.getBorrowNumber());
        Integer borrowNumber = u.getBorrowNumber() +1;
        System.out.println("借入1"+u.getBorrowNumber());

        u.setBorrowNumber(borrowNumber);
        System.out.println("借入2"+u.getBorrowNumber());
        saveneedNumber(u);
    }

    @Override
    public void saveneedNumber(User user) {
        String sql = "update register_user set borrownumber = ? where username = ?";
        template.update(sql,user.getBorrowNumber(),user.getUsername());
    }

    @Override
    public void removeshareUser(String username) {
        String sql = "delete from share_user where username = ?";
        template.update(sql, username);
    }

    //    @Test
    public void test() {
        String sql = "select * from share_user where usbType = " + 3;
        List<SharedUser> list = template.query(sql, new BeanPropertyRowMapper<SharedUser>(SharedUser.class));
        for (SharedUser sharedUser : list) {
            System.out.println(sharedUser.getUsername());
            System.out.println(sharedUser.getUsbType());
        }
    }

}

