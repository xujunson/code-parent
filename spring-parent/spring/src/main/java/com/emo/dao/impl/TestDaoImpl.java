package com.emo.dao.impl;

import com.emo.dao.TestDao;
import com.emo.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author ：ta0567
 * @date ：Created in 2019/9/12 10:57
 * @description：${description}
 */
@Repository
public class TestDaoImpl implements TestDao {
    /*@Resource
    JdbcTemplate jdbcTemplate;*/

    @Override
    public int addInfo(User user) {
        return 0;
    }
}
