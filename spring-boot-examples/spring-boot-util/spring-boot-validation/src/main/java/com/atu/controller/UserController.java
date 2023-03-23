package com.atu.controller;

import com.atu.annotation.NotConflictUser;
import com.atu.annotation.UniqueUser;
import com.atu.mapper.UserRepository;
import com.atu.mapper.domain.User;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author: Tom
 * @Date: 2022/3/1 11:08
 * @Description:
 */
@RestController
@RequestMapping("/senior/user")
@Slf4j
@Validated
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/createUser")
    public User createUser(@UniqueUser @Valid User user) {
        User savedUser = userRepository.save(user);
        log.info("save user id is {}", savedUser.getId());
        return savedUser;
    }

    @SneakyThrows
    @PostMapping("/updateUser")
    public User updateUser(@NotConflictUser @Valid @RequestBody User user) {
        User editUser = userRepository.save(user);
        log.info("update user is {}", editUser);
        return editUser;
    }
}
