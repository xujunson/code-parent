package com.atu.redis.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="boot_role")
public class Role implements Serializable {
	
	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = 8719878517384639149L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length=50,nullable=false)
	private String name;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Role(Long id, String name, Date createdate) {
		super();
		this.id = id;
		this.name = name;
		this.createdate = createdate;
	}

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

}
