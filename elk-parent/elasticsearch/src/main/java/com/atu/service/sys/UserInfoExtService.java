package com.atu.service.sys;
import com.atu.entity.sys.UserInfoExt;
public interface UserInfoExtService {
    void save(UserInfoExt userInfoExt);
    void delete(String objId);
    UserInfoExt getByObjId(String objId);

}
