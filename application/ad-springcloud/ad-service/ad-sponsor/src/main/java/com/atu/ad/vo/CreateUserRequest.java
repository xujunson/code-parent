package com.atu.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * @author: Tom
 * @date: 2020-03-28 9:59
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String username;

    public boolean validate() {
        return !StringUtils.isEmpty(username);
    }
}
