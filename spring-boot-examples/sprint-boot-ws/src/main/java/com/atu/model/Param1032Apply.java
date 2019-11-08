package com.atu.model;

import org.apache.commons.digester3.annotations.rules.BeanPropertySetter;
import org.apache.commons.digester3.annotations.rules.ObjectCreate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

@XmlType(propOrder = {"clientNo", "clientOprDt", "suspendType", "ECardType",
        "ECardNo", "ECardId", "remark", "certifiNo", "certifiType", "cardPwd", "oprID"})
@ObjectCreate(pattern = "Message")
public class Param1032Apply implements Serializable {
    private static final long serialVersionUID = 8655348465201913910L;
    // 统一提供
    @BeanPropertySetter(pattern = "Message/Detail/ClientNo")
    private String clientNo = "00036";
    @BeanPropertySetter(pattern = "Message/Detail/ClientOprDt")
    private Date clientOprDt;
    @BeanPropertySetter(pattern = "Message/Detail/SuspendType")
    private int suspendType = 1; // 被动挂起
    @BeanPropertySetter(pattern = "Message/Detail/ECardType")
    private Integer ECardType;
    @BeanPropertySetter(pattern = "Message/Detail/ECardNo")
    private String ECardNo;
    @BeanPropertySetter(pattern = "Message/Detail/ECardId")
    private String ECardId;
    @BeanPropertySetter(pattern = "Message/Detail/Remark")
    private String remark;
    @BeanPropertySetter(pattern = "Message/Detail/CertifiNo")
    private String certifiNo;
    @BeanPropertySetter(pattern = "Message/Detail/CertifiType")
    private int certifiType;
    @BeanPropertySetter(pattern = "Message/Detail/CardPwd")
    private String cardPwd;
    @BeanPropertySetter(pattern = "Message/Detail/OprID")
    private String oprID = "B11064DE-239A-372B-831B-3208DEAD4AE0";

    @XmlElement(name = "ClientNo")
    public String getClientNo() {
        return this.clientNo;
    }

    @XmlElement(name = "ClientOprDt")
    public Date getClientOprDt() {
        return clientOprDt;
    }

    /**
     * 充值申请时间
     * 格式：yyyy-MM-ddTHH:mm:ss
     *
     * @param clientOprDt
     */
    public void setClientOprDt(Date clientOprDt) {
        this.clientOprDt = clientOprDt;
    }

    @XmlElement(name = "ECardNo")
    public String getECardNo() {
        return ECardNo;
    }

    public void setECardNo(String eCardNo) {
        ECardNo = eCardNo;
    }

    @XmlElement(name = "ECardId")
    public String getECardId() {
        return ECardId;
    }

    public void setECardId(String eCardId) {
        ECardId = eCardId;
    }

    @XmlElement(name = "SuspendType")
    public int getSuspendType() {
        return suspendType;
    }

    @XmlElement(name = "ECardType")
    public Integer getECardType() {
        return ECardType;
    }

    public void setECardType(Integer ECardType) {
        this.ECardType = ECardType;
    }

    @XmlElement(name = "Remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlElement(name = "CertifiNo")
    public String getCertifiNo() {
        return certifiNo;
    }

    public void setCertifiNo(String certifiNo) {
        this.certifiNo = certifiNo;
    }

    @XmlElement(name = "CardPwd")
    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }

    @XmlElement(name = "OprID")
    public String getOprID() {
        return oprID;
    }

    public void setOprID(String oprID) {
        this.oprID = oprID;
    }

    @XmlElement(name = "CertifiType")
    public int getCertifiType() {
        return certifiType;
    }

    public void setCertifiType(int certifiType) {
        this.certifiType = certifiType;
    }
}