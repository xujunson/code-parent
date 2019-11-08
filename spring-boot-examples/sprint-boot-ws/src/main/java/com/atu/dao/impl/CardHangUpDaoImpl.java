package com.atu.dao.impl;

import com.atu.dao.CardHangUpDao;
import com.atu.entity.CardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardHangUpDaoImpl implements CardHangUpDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CardInfo> getCardInfoList() {
        StringBuffer sql = new StringBuffer();
        return null;
    }

    @Override
    public void updateCardInfo(CardInfo cardInfo) {
        //updateObject(cardInfo);
    }
}
