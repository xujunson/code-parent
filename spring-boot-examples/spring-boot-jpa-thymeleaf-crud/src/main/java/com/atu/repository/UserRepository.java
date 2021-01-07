package com.atu.repository;

import com.atu.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：mark
 * @date ：Created in 2019/10/25 10:44
 * @description：
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    void deleteById(Long id);
}
