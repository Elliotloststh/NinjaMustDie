package com.chen.ren3;

import org.apache.ibatis.annotations.*;

@Mapper
public interface GiftDao {
    @Insert("insert into gift value(#{code})")
    int insert(String code);

    @Select("select * from gift where code=#{code}")
    String query(String code);

    @Delete("delete from gift where 1=1")
    int deleteAll();
}
