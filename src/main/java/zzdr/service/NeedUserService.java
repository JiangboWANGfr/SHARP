package zzdr.service;

import zzdr.domain.NeedUser;
import zzdr.domain.SharedUser;

public interface NeedUserService {
    /**
     * 接收需要帮助的人的信息，返回可以帮助的人的信息
     * @param needUser
     * @return
     */
    public SharedUser GetUserforHelp(NeedUser needUser);
    public SharedUser findByShareUsername(String username);
    public boolean IfUserCanBorrow(String username);

}
