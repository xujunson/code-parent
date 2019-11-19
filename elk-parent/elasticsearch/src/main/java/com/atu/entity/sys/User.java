package com.atu.entity.sys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	/**
	 * 姓名
	 */
	private String name;
	private String username;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private String salt;
	/**
	 * 身份证号码
	 */
	@Column(name="id_card")
	private String idCard;
	/**
	 * 部门
	 */
	@Column(name = "orgid")
	private String orgId;
	private String orgName;
	// 1-男,0-女
	private Integer sex;
	private String phone;
	private Integer status;
	/**
	 * 上级ID
	 */
	private String superior;
	/**
	 * 上级名字
	 */
	private String superiorName;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time")
	private Date createTime;
	@Transient
	private String roleIdList;
	@Transient
	private String roleNameList;
	@Transient
	private List<Role> roleList = new ArrayList<Role>();
	/**
	 * 职位ID
	 */
	private String positionId;
	private String positionName;
	private Boolean isShow=true;//控制显示和隐藏 1-显示,0-隐藏
	@Transient
	private UserInfoExt userInfoExt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRoleIdList() {
		return roleIdList;
	}
	public void setRoleIdList(String roleIdList) {
		this.roleIdList = roleIdList;
	}
	public String getRoleNameList() {
		return roleNameList;
	}
	public void setRoleNameList(String roleNameList) {
		this.roleNameList = roleNameList;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSuperior() {
		return superior;
	}
	public void setSuperior(String superior) {
		this.superior = superior;
	}
	public String getSuperiorName() {
		return superiorName;
	}
	public void setSuperiorName(String superiorName) {
		this.superiorName = superiorName;
	}
	public String getPositionId() {
		return positionId;
	}
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}
	public String getPositionName() {
		return positionName;
	}
	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}
	public Boolean getIsShow() {
		return isShow;
	}
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public UserInfoExt getUserInfoExt() {
		return userInfoExt;
	}

	public void setUserInfoExt(UserInfoExt userInfoExt) {
		this.userInfoExt = userInfoExt;
	}
}
