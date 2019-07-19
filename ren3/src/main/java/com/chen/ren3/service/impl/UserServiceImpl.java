package com.chen.ren3.service.impl;

import com.chen.ren3.dao.UserDao;
import com.chen.ren3.entity.Users;
import com.chen.ren3.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author ：Chen Xin
 * @date ：Created in 2019/7/18 19:25
 * @modified By：
 */
@Service(value = "UserService")
public class UserServiceImpl implements UserService {
    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean insertId(String id, String nick_name) {
        log.info("in insertId  " + id + "  " + nick_name);
        int num = userDao.insertId(id, nick_name);
        log.info("num  " + String.valueOf(num));
        if (num == 0) {
            log.info("insertion failed");
            return false;
        } else {
            log.info("insertion success");
            return true;
        }
    }

    public List<Users> getAllId() {
        log.info("in getAllId");
        return userDao.getAllId();
    }

    public String getId(String id) {
        return userDao.getId(id);
    }
}
