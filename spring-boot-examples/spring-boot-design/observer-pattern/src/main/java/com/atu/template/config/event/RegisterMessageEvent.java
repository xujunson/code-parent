package com.atu.template.config.event;

/**
 * @Author: Tom
 * @Date: 2022/7/12 14:17
 * @Description: 注册成功之后，发送消息
 */
public class RegisterMessageEvent extends BaseEvent {

    private String msgId;

    public RegisterMessageEvent(String msgId) {
        super();
        this.msgId = msgId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

}
