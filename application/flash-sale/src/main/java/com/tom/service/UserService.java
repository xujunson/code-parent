package com.tom.service;

import com.tom.dao.UserDao;
import com.tom.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Tom
 * @date: 2020-02-27 15:11
 * @description:
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User getId(int id) {
        return userDao.getById(id);
    }

    //测试事务
    @Transactional
    public boolean tx() {
        User u1= new User();
        u1.setId(2);
        u1.setName("2222");
        userDao.insert(u1);

        User u2= new User();
        u2.setId(3);
        u2.setName("3333");
        userDao.insert(u2);
        return true;
    }
}
