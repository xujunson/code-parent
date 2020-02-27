package com.tom.dao;

import com.tom.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}") //不需要配置文件
    public User getById(@Param("id") int id);

    @Insert("insert into user(id, name)values(#{id}, #{name})")
    public int insert(User user);

}
