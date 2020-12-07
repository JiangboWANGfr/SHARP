package zzdr.service.impl;

import zzdr.dao.UserDao;
import zzdr.dao.impl.UserDaoImpl;
import zzdr.domain.SharedUser;
import zzdr.domain.User;
import zzdr.service.ShareUserService;

public class ShareUserServiceImpl implements ShareUserService {
    private  UserDao userDao = new UserDaoImpl();

    @Override
    public boolean saveLocation(SharedUser sharedUser) {
        SharedUser su = userDao.findByShareUsername(sharedUser.getUsername());
        if (su!=null){
            //已经在分享中，则不作处理
            System.out.println("get_there");
            return false;
        }else {
            userDao.saveLocation(sharedUser);

            String username = sharedUser.getUsername();
            userDao.incShareNumber(username);
            return true;
        }
    }

    @Override
    public boolean remove(String shareUsername) {
        SharedUser removeShare = userDao.findByShareUsername(shareUsername);
        if(removeShare != null){
            userDao.removeshareUser(shareUsername);
            return true;
        }
        return false;
    }
}
