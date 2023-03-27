package com.atu.mapper;

import com.atu.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: Tom
 * @create: 2023-03-27 16:28
 * @Description:
 */
@Mapper
public interface UserMapper {

    /**
     * 批量插入
     *
     * @param list 插入集合
     * @return 插入数量
     */
    int insertForeach(List<User> list);

    /**
     * 获取所有用户
     */
    List<User> selectAll();
}
