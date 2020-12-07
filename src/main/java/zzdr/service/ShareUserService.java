package zzdr.service;

import zzdr.domain.SharedUser;

public interface ShareUserService {
    boolean saveLocation(SharedUser sharedUser);
    boolean remove(String shareUsername);
}
