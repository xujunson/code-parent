package com.atu.model;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"tradeNo", "oprStatus"})
@ObjectCreate(pattern = "Message")
public class Param1033Confirm implements Serializable {
    private static final long serialVersionUID = 8655348465201913910L;
    //挂起交易序号
    @BeanPropertySetter(pattern = "Message/Detail/TradeNo")
    private String tradeNo;
    @BeanPropertySetter(pattern = "Message/Detail/OprStatus")
    private int oprStatus = 2;

    @XmlElement(name = "TradeNo")
    public String getTradeNo() {
        return this.tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @XmlElement(name = "OprStatus")
    public int getOprStatus() {
        return oprStatus;
    }

    public void setOprStatus(int oprStatus) {
        this.oprStatus = oprStatus;
    }
}