package com.atu.ad.entity;

import com.atu.ad.constant.CommonStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Tom
 * @date: 2020-03-27 11:39
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity //实体类
@Table(name = "ad_user")
public class AdUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键的生成策略
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic //基本属性,默认注解可以不写
    @Column(name = "username", nullable = false)
    private String username;

    //@Transient非数据库字段
    @Basic
    @Column(name = "token", nullable = false)
    private String token;

    @Basic
    @Column(name = "user_status", nullable = false)
    private Integer userStatus;

    @Basic
    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Basic
    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public AdUser(String username, String token) {
        this.username = username;
        this.token = token;
        this.userStatus = CommonStatus.VALID.getStatus();
        this.createTime = new Date();
        this.updateTime = this.createTime;
    }
}
