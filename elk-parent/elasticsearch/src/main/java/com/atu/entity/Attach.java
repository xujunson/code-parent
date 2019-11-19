package com.atu.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 附件 实体类
 *
 */
@Entity
@Table(name = "doc_attach")
public class Attach implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	* id
	*/
	@Id
	@Column(name="id")
	private String id;
	/**
	* 附件名称
	*/
	@Column(name="name")
	private String name;
	/**
	* 附件格式
	*/
	@Column(name="type")
	private String type;
	/**
	* 附件地址
	*/
	@Column(name="path")
	private String path;
	
	/**
	* 上传时间
	*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	/**
	 * 文件大小
	 */
	private String size;

	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id=id;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getType(){
		return type;
	}
	public void setType(String type){
		this.type=type;
	}
	public String getPath(){
		return path;
	}
	public void setPath(String path){
		this.path=path;
	}
	public Date getCreateTime(){
		return createTime;
	}
	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
}
