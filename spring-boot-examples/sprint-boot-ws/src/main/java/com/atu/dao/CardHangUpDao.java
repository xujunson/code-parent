package com.atu.dao;

import com.atu.entity.CardInfo;

import java.util.List;

public interface CardHangUpDao {
    List<CardInfo> getCardInfoList();

    void updateCardInfo(CardInfo cardInfo);
}
