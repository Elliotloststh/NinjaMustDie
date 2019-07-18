package com.chen.ren3;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {
    int insertId(@Param("id") String id);
    List<String> getAllId();
}
