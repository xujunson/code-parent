package com.atu.entity.sys;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户信息扩展表 实体类
 */
@Entity
@Table(name = "sys_user_info_ext")
public class UserInfoExt implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	* 头像地址
	*/
	@Column(name="photo")
	private String photo;
	@Column(name="attach_id")
	private String attachId;
	/**
	* 外键
	*/
	@Column(name="obj_id")
	private String objId;
	/**
	* 主键
	*/
	@Id
	@Column(name="id")
	private String id;


	public String getPhoto(){
		return photo;
	}
	public void setPhoto(String photo){
		this.photo=photo;
	}
	public String getObjId(){
		return objId;
	}
	public void setObjId(String objId){
		this.objId=objId;
	}
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}

	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}
}
