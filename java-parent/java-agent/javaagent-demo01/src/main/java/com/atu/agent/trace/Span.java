package com.atu.agent.trace;

import java.util.Date;

/**
 * @author: Tom
 * @date: 2023/4/15 13:57
 * @description: TODO
 **/
public class Span {
    private String linkId;
    private Date enterTime;

    public Span(String linkId) {
        this.linkId = linkId;
        this.enterTime = new Date();
    }

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }
}
