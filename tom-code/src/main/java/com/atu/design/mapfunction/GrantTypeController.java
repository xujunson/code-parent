package com.atu.design.mapfunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Tom
 * @Date: 2022/1/18 10:37 上午
 * @Description:
 */
@RestController
public class GrantTypeController {

    @Autowired
    private QueryGrantTypeService queryGrantTypeService;

    @PostMapping("/grantType")
    public String test(String resourceType, String resourceName){
        return queryGrantTypeService.getResult(resourceType, resourceName);
    }
}
