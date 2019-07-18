package com.chen.ren3;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {
    int insertId(@Param("id") String id);
    List<String> getAllId();

    @Select("select * from users where id=#{id}")
    String getId(String id);
}
