package com.atu.entity.sys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * <p>
 * 系统日志
 * </p>
 */
@Entity
@Table(name="sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@Id
	private String id;
    /**
     * 登陆名
     */
	private String username;
    /**
     * 内容
     */
	@Column(name="opt_content")
	private String optContent;
	/**
	 * 操作类型
	 */
	@Column(name = "opt_type")
	private String optType;
    /**
     * 客户端ip
     */
	@Column(name="client_ip")
	private String clientIp;
    /**
     * 创建时间
     */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;
	@Transient
	@Temporal(TemporalType.DATE)
	private Date createTimeStart;
	@Transient
	@Temporal(TemporalType.DATE)
	private Date getCreateTimeEnd;
	/**
	 * 操作描述
	 */
	@Column(name = "opt_desc",columnDefinition = "TEXT")
	private String desc;


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


	public String getOptContent() {
		return optContent;
	}

	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(Date createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public Date getGetCreateTimeEnd() {
		return getCreateTimeEnd;
	}

	public void setGetCreateTimeEnd(Date getCreateTimeEnd) {
		this.getCreateTimeEnd = getCreateTimeEnd;
	}
}
