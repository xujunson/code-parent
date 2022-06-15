package com.atu.document;

import lombok.Data;

/**
 * @author: Tom
 * @date: 2022/6/15 16:17
 * @description:
 **/
@Data
public class Hero {
    private Integer id;
    private String name;
    private String country;
    private String birthday;
    private Integer longevity;
}
