package com.atu.model;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

import java.io.Serializable;

@ObjectCreate(pattern = "PageInfo")
public class Response1032Apply implements Serializable {
    @BeanPropertySetter(pattern = "PageInfo/ResultCode")
    /** 执行结果*/
    private int resultCode;
    @BeanPropertySetter(pattern = "PageInfo/AccessoryContents/ErrorInfo")
    private String errorInfo;

    @BeanPropertySetter(pattern = "PageInfo/AccessoryContents/TradeNo")
    /** 挂起交易序号*/
    public String tradeNo;


    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    /**
     * 账务充值交易序号
     */
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
