package com.atu.business.impl;

import com.atu.business.CardHangUpManager;
import com.atu.dao.CardHangUpDao;
import com.atu.entity.CardInfo;
import com.atu.model.*;
import com.atu.util.ETCClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class CardHangUpManagerImpl implements CardHangUpManager {
    @Autowired
    private CardHangUpDao cardHangUpDaoImpl;

    @Override
    public void hangUpCard() {
        log.info("====开始执行挂起操作===");
        try {
            List<CardInfo> cardInfoList = cardHangUpDaoImpl.getCardInfoList();
            if (cardInfoList != null && cardInfoList.size() > 0) {
                for (CardInfo cardInfo : cardInfoList) {
                    if (doHangUpApply(cardInfo)) {
                        cardInfo.setState(1);

                        cardHangUpDaoImpl.updateCardInfo(cardInfo);
                        log.info("卡号：" + cardInfo.getEcardNO() + "，挂起成功");
                    }
                }
            } else {
                log.warn("未查询到符合要求的卡信息");
            }
            log.info("====结束执行挂起操作===");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 挂起操作
     *
     * @param cardInfo
     * @return
     * @throws Exception
     */
    private boolean doHangUpApply(CardInfo cardInfo) throws Exception {
        try {
            Param1032Apply param1032Apply = new Param1032Apply();
            param1032Apply.setClientOprDt(new Date());
            param1032Apply.setECardId((cardInfo.getEcardID() == null ? "0" : cardInfo.getEcardID().toString()));
            param1032Apply.setECardNo(cardInfo.getEcardNO());
            param1032Apply.setECardType(cardInfo.getEcardType());
            param1032Apply.setCertifiType(cardInfo.getCertifiType());
            param1032Apply.setCertifiNo(cardInfo.getCertifiNO());
            Request1032Apply request1032Apply = new Request1032Apply();
            request1032Apply.setDetail(param1032Apply);
            //挂起申请
            Response1032Apply response1032Apply = ETCClient.exe1032(request1032Apply);
            if (doHangUpConfirm(response1032Apply, cardInfo.getEcardNO())) {//挂起确认成功
                log.info("卡号：" + cardInfo.getEcardNO() + "，挂起成功");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private boolean doHangUpConfirm(Response1032Apply response1032Apply, String ecardNo) {
        try {
            Param1033Confirm param1033Confirm = new Param1033Confirm();
            if (response1032Apply.getResultCode() == 0) {//挂起申请成功
                param1033Confirm.setOprStatus(1);
                param1033Confirm.setTradeNo(response1032Apply.getTradeNo());
                Request1033Confirm request1033Confirm = new Request1033Confirm();
                request1033Confirm.setDetail(param1033Confirm);
                //挂起确认
                Response1033Confirm response1033Confirm = ETCClient.exe1033(request1033Confirm);
                if (response1033Confirm.getResultCode() == 0) {//挂起确认成功
                    return true;
                } else {
                    log.error("卡号：" + ecardNo + ",挂起确认失败，原因：" + response1033Confirm.getErrorInfo());
                    return false;
                }
            } else {
                log.error("卡号：" + ecardNo + ",挂起申请失败，原因：" + response1032Apply.getErrorInfo());
                return false;
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }
}
