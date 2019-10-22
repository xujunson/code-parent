package com.atu.web;

import com.atu.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：mark
 * @date ：Created in 2019/10/21 17:42
 * @description： 控制层
 */
@RestController
public class UserController {
    @RequestMapping("/getUser")
    @Cacheable(value = "user-key")
    public User getUser() {
        User user=new User("aa@126.com", "aa", "aa123456", "aa","123");
        System.out.println("success");
        return user;
    }
}
