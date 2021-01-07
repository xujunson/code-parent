package com.atu.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlType(propOrder = {"userName", "password"})
public class AuthInfo implements Serializable {
    private String userName;
    private String password;

    public AuthInfo() {
    }

    public AuthInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @XmlElement(name = "UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @XmlElement(name = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
