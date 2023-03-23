package com.atu.validation;

import com.atu.annotation.NotConflictUser;
import com.atu.annotation.UniqueUser;
import com.atu.mapper.UserRepository;
import com.atu.mapper.domain.User;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * @Author: Tom
 * @Date: 2022/3/1 10:50
 * @Description:
 */
@Slf4j
public class UserValidation<T extends Annotation> implements ConstraintValidator<T, User> {

    protected Predicate<User> predicate = c -> true;

    @Resource
    protected UserRepository userRepository;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository == null || predicate.test(user);
    }

    /**
     * 校验用户是否唯一
     * 即判断数据库是否存在当前新用户的信息，如用户名，手机，邮箱
     */
    public static class UniqueUserValidator extends UserValidation<UniqueUser> {
        @Override
        public void initialize(UniqueUser uniqueUser) {
            predicate = c -> !userRepository.existsByUserNameOrEmailOrUserPhone(c.getUserName(), c.getEmail(), c.getUserPhone());
        }
    }

    /**
     * 校验是否与其他用户冲突
     * 将用户名、邮件、电话改成与现有完全不重复的，或者只与自己重复的，就不算冲突
     */
    public static class NotConflictUserValidator extends UserValidation<NotConflictUser> {
        @Override
        public void initialize(NotConflictUser notConflictUser) {
            // 使用Predicate函数式接口对业务规则进行判断
            predicate = c -> {
                log.info("user detail is {}", c);
                Collection<User> collection = userRepository.findByUserNameOrEmailOrUserPhone(c.getUserName(), c.getEmail(), c.getUserPhone());
                // 将用户名、邮件、电话改成与现有完全不重复的，或者只与自己重复的，就不算冲突
                return collection.isEmpty() || (collection.size() == 1 && collection.iterator().next().getId().equals(c.getId()));
            };
        }
    }

}