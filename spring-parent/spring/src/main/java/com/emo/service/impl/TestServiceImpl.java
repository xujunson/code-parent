package com.emo.service.impl;

import com.emo.dao.TestDao;
import com.emo.entity.User;
import com.emo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：ta0567
 * @date ：Created in 2019/9/12 10:56
 * @description：test实现
 */
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestDao testDaoImpl;

    @Override
    public int addInfo() {
        User user = new User();

        return testDaoImpl.addInfo(user);
    }
}
