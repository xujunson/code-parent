package com.tom.dao;

import com.tom.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author: Tom
 * @date: 2020-02-27 21:15
 * @description:
 */
@Mapper
public interface MiaoShaUserDao {
    @Select("select * from miaosha_user where id = #{id} ")
    public MiaoshaUser getById(@Param("id") long id);
}
