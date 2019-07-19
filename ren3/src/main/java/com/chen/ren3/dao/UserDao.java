package com.chen.ren3.dao;

import com.chen.ren3.entity.Users;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {
    int insertId(@Param("id") String id, @Param("nick_name") String nick_name);

    List<Users> getAllId();

    @Select("select id from users where id=#{id}")
    String getId(String id);
}
