package zzdr.service.impl;


import zzdr.dao.UserDao;
import zzdr.dao.impl.UserDaoImpl;
import zzdr.domain.NeedUser;
import zzdr.domain.SharedUser;
import zzdr.domain.User;
import zzdr.service.NeedUserService;

import javax.print.attribute.standard.SheetCollate;
import java.util.List;

public class NeedUserServiceImpl implements NeedUserService {

    UserDao userDao = new UserDaoImpl();
    static double EARTH_RADIUS = 6371.0;
    @Override
    public SharedUser GetUserforHelp(NeedUser needUser) {
        double needLatitude = needUser.getLatitude();
        double needLongitude = needUser.getLongitude();
        int  needUsbType = needUser.getUsbType();
        SharedUser fialSharedUser = null;
//        double distanceLast  = Integer.MAX_VALUE;
        double distanceMin = Integer.MAX_VALUE;
        List sharedUserList = userDao.GetAllSharedUser( needUsbType);

        if(sharedUserList != null){
            for (Object sharedUser : sharedUserList) {
                int shareUsbtype = ((SharedUser) sharedUser).getUsbType();
                double shareLatitude = ((SharedUser) sharedUser).getLatitude();
                double shareLongitude = ((SharedUser) sharedUser).getLongitude();
                double distance = Distance(needLatitude, needLongitude, shareLatitude, shareLongitude);
                if(distance <= distanceMin){
                    distanceMin = distance;
                    fialSharedUser = (SharedUser)sharedUser;
                }
            }
        }
        if(fialSharedUser != null){
            userDao.incneedNumber(needUser.getUsername());
            userDao.removeshareUser(fialSharedUser.getUsername());
        }
        return fialSharedUser;
    }

    @Override
    public SharedUser findByShareUsername(String username) {
        return userDao.findByShareUsername(username);
    }

    @Override
    public boolean IfUserCanBorrow(String username) {
        User u = userDao.findByUsername(username);
        int BorrowNumber = u.getBorrowNumber();
        int landNumber = u.getLandNumber();
        if (BorrowNumber <= landNumber *3){
            return true;
        }else{
            return false;
        }
    }
//    @Test
    public void testd(){
        User u = userDao.findByUsername("112018253344");
        int BorrowNumber = u.getBorrowNumber();
        int landNumber = u.getLandNumber();
        System.out.println(BorrowNumber);
        System.out.println(landNumber);
    }
    //    @Test
    public void test2(){
        double needLatitude = 34;
        double needLongitude = 118;
        SharedUser falSharedUser = null;
//        double distanceLast  = Integer.MAX_VALUE;
        double distanceMin = Integer.MAX_VALUE;
        List sharedUserList = userDao.GetAllSharedUser(3);
        for (Object sharedUser : sharedUserList) {
            double shareLatitude = ((SharedUser) sharedUser).getLatitude();
            double shareLongitude = ((SharedUser) sharedUser).getLongitude();
            double distance = Distance(needLatitude, needLongitude, shareLatitude, shareLongitude);
            if(distance <= distanceMin){
                distanceMin = distance;
                falSharedUser = (SharedUser)sharedUser;
            }
        }
        System.out.println(falSharedUser.getUsername());
        System.out.println(falSharedUser.getTelephone());
//        return falSharedUser;
    }
    /// <summary>
    /// 将角度换算为弧度。
    /// </summary>
    /// <param name="degrees">角度</param>
    /// <returns>弧度</returns>
    public static double ConvertDegreesToRadians(double degrees)
    {
        return degrees * Math.PI / 180;
    }

    public static double ConvertRadiansToDegrees(double radian)
    {
        return radian * 180.0 / Math.PI;
    }

    public static double HaverSin(double theta)
    {
        double v = Math.sin(theta / 2);
        return v * v;
    }

    /**
     * 返回的距离，单位km
     * @param needLatitude
     * @param needLongitude
     * @param shareLatitude
     * @param shareLongitude
     * @return
     */
    public static double Distance(double needLatitude,double needLongitude, double shareLatitude,double shareLongitude)
    {
        //用haversine公式计算球面两点间的距离。
        //经纬度转换成弧度
        needLatitude = ConvertDegreesToRadians(Math.abs(needLatitude));
        needLongitude = ConvertDegreesToRadians(Math.abs(needLongitude));
        shareLatitude = ConvertDegreesToRadians(Math.abs(shareLatitude));
        shareLongitude = ConvertDegreesToRadians(Math.abs(shareLongitude));

        //差值
        double vLon = Math.abs(needLongitude - shareLongitude);
        double vLat = Math.abs(needLatitude - shareLatitude);

        //h is the great circle distance in radians, great circle就是一个球体上的切面，它的圆心即是球心的一个周长最大的圆。
        double h = HaverSin(vLat) + Math.cos(needLatitude) * Math.cos(shareLatitude) * HaverSin(vLon);

        double distance = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(h));

        return distance;
    }
}
