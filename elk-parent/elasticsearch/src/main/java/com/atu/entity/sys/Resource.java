package com.atu.entity.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;



/**
 * The persistent class for the resource database table.
 * 
 */
@Entity
@Table(name="sys_resource")
public class Resource implements Serializable,Comparable<Resource> {
	private static final long serialVersionUID = 1L;
	/** 主键 */
	@Id
	private String id;

	/** 资源名称 */
	@NotBlank
	private String name;

	/** 资源路径 */
	private String url;

	/** 打开方式 ajax,iframe */
	private String openMode;

	/** 资源介绍 */
	private String description;

	/** 资源图标 */
	@JsonProperty("iconCls")
	private String icon;

	/** 父级资源id */
	private String pid;
	@Transient
	private String pName;

	/** 排序 */
	private Integer seq;

	/** 状态 */
	private Integer status;

	/** 打开的 */
	private Integer opened;

	/** 资源类别 */
	private Integer resourceType;

	/** 创建时间 */
	private Date createTime;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOpenMode() {
		return openMode;
	}

	public void setOpenMode(String openMode) {
		this.openMode = openMode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPid() {
		return this.pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOpened() {
		return opened;
	}

	public void setOpened(Integer opened) {
		this.opened = opened;
	}

	public Integer getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	@Override
	public int compareTo(Resource o) {
		return this.seq.compareTo(o.getSeq());
	}
	public static void main(String[] args) {
		List<Resource>rsList=new ArrayList<Resource>();
		Resource r1=new Resource();
		r1.setId("1");
		r1.setName("r1");
		r1.setSeq(2);
		rsList.add(r1);
		Resource r2=new Resource();
		r2.setId("2");
		r2.setName("r2");
		r2.setSeq(0);
		rsList.add(r2);
		Collections.sort(rsList);//升序排列
		for(Resource r:rsList) {
			System.out.println(r.getName()+" "+r.getSeq());
		}
		Collections.reverse(rsList);//降序排列
		for(Resource r:rsList) {
			System.out.println(r.getName()+" "+r.getSeq());
		}
	}

}