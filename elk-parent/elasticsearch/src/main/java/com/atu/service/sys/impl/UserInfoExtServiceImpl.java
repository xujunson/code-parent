package com.atu.service.sys.impl;

import com.atu.dao.BaseDao;
import com.atu.entity.sys.UserInfoExt;
import com.atu.service.sys.UserInfoExtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
* 用户信息扩展表
*/
@Service("userInfoExtService")
public class UserInfoExtServiceImpl implements UserInfoExtService {
    @Autowired
    private BaseDao<UserInfoExt,String>userInfoExtDao;
    @Override
    public void save(UserInfoExt userInfoExt) {
        delete(userInfoExt.getObjId());
		userInfoExt.setId(UUID.randomUUID().toString());
        userInfoExtDao.save(userInfoExt);
    }

    @Override
    public void delete(String objId) {
        String hql="delete from UserInfoExt where objId=?";
        userInfoExtDao.execute(hql,objId);
    }

    @Override
    public UserInfoExt getByObjId(String objId) {
        String hql=" from UserInfoExt where objId=?";
        List<UserInfoExt>userInfoExts=new ArrayList<>();
        userInfoExts=userInfoExtDao.find(hql,objId);
        if(userInfoExts.size()>0){
            return userInfoExts.get(0);
        }
        return null;
    }
}
