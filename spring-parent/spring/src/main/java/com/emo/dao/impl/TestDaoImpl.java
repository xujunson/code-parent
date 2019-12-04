package com.emo.dao.impl;

import com.emo.dao.TestDao;
import com.emo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author ：Tom
 * @date ：Created in 2019/9/12 10:57
 * @description：dao
 */
@Repository
public class TestDaoImpl implements TestDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public int addInfo(User user) {
        String sql = "INSERT INTO USER (USERID, USERNAME) VALUES (?,?)";
        return jdbcTemplate.update(sql, user.getUserId(), user.getUsername());
    }
}
