package com.chen.ren3.service;
import com.chen.ren3.entity.Users;
import java.util.List;

public interface UserService {
    boolean insertId(String id, String nick_name);
    List<Users> getAllId();
    String getId(String id);
}
