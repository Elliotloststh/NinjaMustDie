package com.chen.ren3;

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

    public boolean insertId(String id) {
        log.info("in insertId");
        int num = userDao.insertId(id);
        log.info("id  " + String.valueOf(num));
        if (num == 0) {
            log.info("insertion failed");
            return false;
        } else {
            log.info("insertion success");
            return true;
        }
    }

    public List<String> getAllId() {
        log.info("in getAllId");
        return userDao.getAllId();
    }
}
