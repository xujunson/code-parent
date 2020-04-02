package com.atu.extra.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * <h1>额外表实体类定义</h1>
 *
 * @Entity 标注为一个实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "extra_ad")
public class ExtraAd {

    @Id
    //数据库默认主键自增策略
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * <h2>提供 name 的构造函数</h2>
     */
    public ExtraAd(String name) {
        this.name = name;
    }
}
