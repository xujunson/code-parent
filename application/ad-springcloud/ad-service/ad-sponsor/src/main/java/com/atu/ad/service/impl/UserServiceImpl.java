package com.atu.ad.service.impl;

import com.atu.ad.constant.Constants;
import com.atu.ad.dao.AdUserRepository;
import com.atu.ad.entity.AdUser;
import com.atu.ad.exception.AdException;
import com.atu.ad.service.IUserService;
import com.atu.ad.utils.CommonUtils;
import com.atu.ad.vo.CreateUserRequest;
import com.atu.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author: Tom
 * @date: 2020-03-28 9:58
 * @description:
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    private AdUserRepository userRepository;

    @Autowired
    public UserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request)
            throws AdException {
        //1、参数验证
        if (!request.validate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        //2、重复验证
        AdUser oldUser = userRepository.findByUsername(request.getUsername());

        if (oldUser != null) {
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        //3、保存数据
        AdUser newUser = userRepository.save(new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())));

        return new CreateUserResponse(newUser.getId(), newUser.getUsername(), newUser.getToken(),
                newUser.getCreateTime(), newUser.getUpdateTime());
    }
}
