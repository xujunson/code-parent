package com.atu.domian;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author: Tom
 * @create: 2023-02-13 19:37
 * @Description: status
 */
@Data
@ToString
@Accessors(chain = true)
public class Status {

    private Integer weight;
    private Integer height;

}
