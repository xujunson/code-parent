package com.atu.entity.sys;

import javax.persistence.*;

@Entity
@Table(name = "sys_user_position")
public class UserPosition {
    @Id
    private String id;
    private String userid;
    private String positionid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPositionid() {
		return positionid;
	}
	public void setPositionid(String positionid) {
		this.positionid = positionid;
	}
    
    
}
