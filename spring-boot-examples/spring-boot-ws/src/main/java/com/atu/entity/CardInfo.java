package com.atu.entity;

import java.io.Serializable;

public class CardInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Integer cardNetID;
    private String ecardNO;
    private Long ecardID;
    private Integer ecardType;
    private String ecardPwd;
    private Integer certifiType;
    private String certifiNO;
    private Integer state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCardNetID() {
        return cardNetID;
    }

    public void setCardNetID(Integer cardNetID) {
        this.cardNetID = cardNetID;
    }

    public String getEcardNO() {
        return ecardNO;
    }

    public void setEcardNO(String ecardNO) {
        this.ecardNO = ecardNO;
    }

    public Long getEcardID() {
        return ecardID;
    }

    public void setEcardID(Long ecardID) {
        this.ecardID = ecardID;
    }

    public Integer getEcardType() {
        return ecardType;
    }

    public void setEcardType(Integer ecardType) {
        this.ecardType = ecardType;
    }

    public String getEcardPwd() {
        return ecardPwd;
    }

    public void setEcardPwd(String ecardPwd) {
        this.ecardPwd = ecardPwd;
    }

    public Integer getCertifiType() {
        return certifiType;
    }

    public void setCertifiType(Integer certifiType) {
        this.certifiType = certifiType;
    }

    public String getCertifiNO() {
        return certifiNO;
    }

    public void setCertifiNO(String certifiNO) {
        this.certifiNO = certifiNO;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
