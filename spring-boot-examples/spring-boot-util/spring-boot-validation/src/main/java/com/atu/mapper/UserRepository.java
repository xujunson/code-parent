package com.atu.mapper;

import com.atu.mapper.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @Author: Tom
 * @Date: 2022/3/1 10:54
 * @Description:
 */
@Component
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUserNameOrEmailOrUserPhone(String userName, String email, String usePhone);

    Collection<User> findByUserNameOrEmailOrUserPhone(String userName, String email, String usePhone);
}
