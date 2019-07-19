package com.chen.ren3.controller;

import com.alibaba.fastjson.JSONObject;
import com.chen.ren3.entity.Users;
import com.chen.ren3.service.impl.GiftService;
import com.chen.ren3.ResponseData;
import com.chen.ren3.service.UserService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * @author ：Chen Xin
 * @date ：Created in 2019/7/18 19:22
 * @modified By：
 */
@Controller
public class Ren3Controller {
    private static Logger log = LoggerFactory.getLogger(Ren3Controller.class);

    private final UserService userService;

    @Autowired
    GiftService giftService;

    @Autowired
    public Ren3Controller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/ren3")
    public String mainPage(ModelMap map) {
        return "home.html";
    }


    @GetMapping("/add/{id}")
    @ResponseBody
    public ResponseData add(@PathVariable("id") String id) {
        log.info("in add  " + id);
        try {
            String result = sendGet("http://statistics.pandadastudio.com/player/simpleInfo?uid=" + id);
            log.info(result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            Integer status = (Integer) jsonObject.get("code");
            JSONObject data = JSONObject.parseObject(jsonObject.getString("data"));
            if (status == 0) {
                String nick_name = data.getString("name");
                if(userService.getId(id) != null) {
                    ResponseData responseData = ResponseData.customerError();
                    responseData.putDataValue("msg", "你已经添加过了");
                    return responseData;
                }
                if (userService.insertId(id, nick_name)) {
                    giftService.deleteAll();
                    return ResponseData.ok();
                } else {
                    ResponseData responseData = ResponseData.serverInternalError();
                    responseData.putDataValue("msg", "插入数据库失败，请找小陈同学");
                    return responseData;
                }
            } else {
                ResponseData responseData = ResponseData.customerError();
                responseData.putDataValue("msg", "ID错了");
                return responseData;
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            ResponseData responseData = ResponseData.serverInternalError();
            responseData.putDataValue("msg", "发生了错误，请找小陈同学");
            return responseData;
        }
    }

    @GetMapping("/key/{key}")
    @ResponseBody
    public ResponseData gift(@PathVariable("key") String key) {
        if(giftService.query(key)!=null) {
            ResponseData responseData = ResponseData.customerError();
            responseData.putDataValue("msg", "已经有人帮你领过了哦~");
            return responseData;
        }

        log.info("in key  " + key);
        List<Users> ids = userService.getAllId();
        ResponseData responseData = ResponseData.ok();
        String url = "http://statistics.pandadastudio.com/player/giftCode?uid=%s&code=%s";
        boolean flag = false;
        boolean flag2 = false;
        for (Users id: ids) {
            try {
                String tmp_url = String.format(url, id.getId(), key);
                log.info(tmp_url);
                String result = sendGet(tmp_url);
                log.info(result);
                JSONObject jsonObject = JSONObject.parseObject(result);
                Integer status = (Integer) jsonObject.get("code");
                if (status == 0) {
                    responseData.putDataValue(id.getNick_name(), "小白爱你哦");
                    flag = true;
                } else if(status == 425) {
                    responseData.putDataValue(id.getNick_name(), "礼包已领取");
                } else {
                    responseData.putDataValue(id.getNick_name(), "兑换码错误");
                    flag2 = true;
                    break;
                }
            } catch (IOException e) {
                log.info(e.getMessage());
                responseData.putDataValue(id.getNick_name(), "小白Ma没了");
            }
        }


        if (flag) {
            giftService.insert(key);
            return responseData;
        } else {
            if(!flag2) {
                responseData = ResponseData.customerError();
                responseData.putDataValue("msg", "所有人都领取过辣");
                return responseData;
            } else {
                responseData = ResponseData.customerError();
                responseData.putDataValue("msg", "领取失败，你的兑换码正确吗");
                return responseData;
            }

        }
    }

    private static String sendGet(String url) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new IOException("状态码不为200" + String.valueOf(response.getStatusLine().getStatusCode()));
        }
        HttpEntity entity = response.getEntity();
        String result = EntityUtils.toString(entity);
        response.close();
        return result;
    }

    private static String unicodeToString(String unicode) {
        StringBuilder sb = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            sb.append((char) index);
        }
        return sb.toString();
    }

}
