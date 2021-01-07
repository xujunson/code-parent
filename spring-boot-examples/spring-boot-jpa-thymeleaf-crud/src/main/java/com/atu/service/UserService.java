package com.atu.service;

import com.atu.model.User;

import java.util.List;

/**
 * @author ：mark
 * @date ：Created in 2019/10/25 10:45
 * @description：
 */
public interface UserService {
    public List<User> getUserList();

    public User findUserById(long id);

    public void save(User user);

    public void edit(User user);

    public void delete(long id);
}
