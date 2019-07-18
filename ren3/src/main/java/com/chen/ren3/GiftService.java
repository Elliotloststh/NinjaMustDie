package com.chen.ren3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GiftService {

    @Autowired
    GiftDao giftDao;

    public String query(String code) {
        return giftDao.query(code);
    }

    public int insert(String code) {
        return giftDao.insert(code);
    }

    public int deleteAll() {
        return giftDao.deleteAll();
    }
}
