package com.atu.repository;

import com.atu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa 是利用 Hibernate 生成各种自动化的 sql，
 * 如果只是简单的增删改查，基本上不用手写了，Spring 内部已经封装实现了
 *
 *
 * dao 只要继承 JpaRepository 类就可以，几乎可以不用写方法，还有一个特别有尿性的功能非常赞，
 * 就是可以根据方法名来自动的生成 SQL，比如findByUserName 会自动生成一个以 userName 为参数的查询方法，
 * 比如 findAlll 自动会查询表里面的所有数据，比如自动分页等等
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);

    User findByUserNameOrEmail(String username, String email);
}
