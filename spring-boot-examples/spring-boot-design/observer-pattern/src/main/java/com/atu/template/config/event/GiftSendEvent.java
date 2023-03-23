package com.atu.template.config.event;

/**
 * @Author: Tom
 * @Date: 2022/7/12 14:18
 * @Description: 用户送礼物成功，然后发送广播
 */
public class GiftSendEvent extends BaseEvent {

    private String giftId;

    public GiftSendEvent(String giftId) {
        this.giftId = giftId;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }
}
