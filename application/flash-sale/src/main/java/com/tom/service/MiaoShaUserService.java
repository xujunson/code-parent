package com.tom.service;

import com.tom.dao.MiaoShaUserDao;
import com.tom.domain.MiaoshaUser;
import com.tom.exception.GlobalException;
import com.tom.result.CodeMsg;
import com.tom.util.MD5Util;
import com.tom.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: Tom
 * @date: 2020-02-27 21:17
 * @description:
 */
@Service
public class MiaoShaUserService {
    @Autowired
    MiaoShaUserDao miaoShaUserDao;
    public MiaoshaUser getById(long id) {
        return miaoShaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null)
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        String mobile = loginVo.getMobile();
        String fromPass = loginVo.getPassword();
        MiaoshaUser miaoshaUser = getById(Long.parseLong(mobile));
        //1、判断手机号是否存在
        if(miaoshaUser == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        //2、验证密码
        String dbPass = miaoshaUser.getPassword();
        String saltDb = miaoshaUser.getSalt();
        String calcPass = MD5Util.formPassToDBPass(fromPass,saltDb);

        if(!dbPass.equals(calcPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        return true;
    }

}
