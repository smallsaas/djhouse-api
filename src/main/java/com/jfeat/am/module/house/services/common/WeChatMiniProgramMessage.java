package com.jfeat.am.module.house.services.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfeat.am.module.house.services.utility.HttpClientUtil;
import com.jfeat.crud.base.exception.BusinessCode;
import com.jfeat.crud.base.exception.BusinessException;
import org.apache.hc.core5.http.HttpEntity;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * @description: 微信小程序消息
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/4/13 15:26
 * @author: hhhhhtao
 */
@Component
public class WeChatMiniProgramMessage {

    // 微信小程序凭证key
    private static final String ACCESS_TOKEN = "access_token";
    // 匠城小程序 appid
    private static final String HOUSE_APPID = "wx0723324978613c32";
    // 匠城小程序 appSecret
    private static final String HOUSE_APP_SECRET = "c97ecac59a40a68fc4160b957127fc70";
    // 匠城小程序消息模版id：订单进度提醒
    private static final String ORDER_PROGRESS_TEMPLATE_ID = "F-S6msjjL6lnmtH84LkOvgrbOPmJ8R3nk9J7-8qC260";

    // 微信小程序获取接口调用凭证api url,方法：GET
    private static final String WECHAT_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${appid}&secret=${secret}";

    // 微信小程序发送订阅信息api url,方法：POST
    private static final String WECHAT_SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=${access_token}";


    @Resource
    StringRedisTemplate stringRedisTemplate; // spring-boot

    /**
     * 获取接口调用凭据
     * 请求的是微信官方域名，不做证书验证
     * @return "{"access_token": 获取到的凭证，"expires_in": 有效时间}"
     */
    public String getAccessToken() {

        String accessToken = null;
        // 查看redis缓存中是否拥有access_token
        accessToken = stringRedisTemplate.opsForValue().get(ACCESS_TOKEN);
        if (accessToken == null) {
            // 替换url中的占位符
            String url = WECHAT_GET_ACCESS_TOKEN_URL.replace("${appid}",HOUSE_APPID).replace("${secret}",HOUSE_APP_SECRET);
            // 执行get请求,获取返回值
            String requestResult = HttpClientUtil.get(url);
            if (requestResult == null) throw new BusinessException(BusinessCode.CodeBase,"凭证获取失败");
            // 将字符串转为json以便获取凭证
            accessToken = JSON.parseObject(requestResult).getString(ACCESS_TOKEN);
            if (accessToken == null) throw new BusinessException(BusinessCode.CodeBase,"凭证获取失败,请求返回信息：" + requestResult);
            // 获取凭证，并存入redis缓存中，凭证有效期为7200s，我们设为7000s
            stringRedisTemplate.opsForValue().set(ACCESS_TOKEN,accessToken,7000, TimeUnit.SECONDS);
        }

        return accessToken;
    }

    /**
     *
     * 给指定用户发送一次性订阅消息
     * 微信指定格式：
     *  {
     *      access_token: "", // 接口调用凭证
     *      template_id: "", // 所需下发的订阅模板id
     *      page: "", // 跳转页面，暂时不需要
     *      touser: "", // 接收者（用户）的 openid
     *      data: "", // 模板内容，格式形如 { "key1": { "value": any }, "key2": { "value": any } }的object
     *      miniprogram_state: "", 跳转小程序类型：developer为开发版；trial为体验版；formal为正式版；默认为正式版
     *      lang: "" // 进入小程序查看”的语言类型，支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN
     *  }
     *
     * @param openid
     * @param templateId
     * @param data
     */
    public void sendMessage(String openid,String templateId,JSON data) {

        // 获取access_token
        String accessToken = this.getAccessToken();
        if (accessToken == null) throw new BusinessException(BusinessCode.EmptyNotAllowed,"access_token为空");

        // 替换url中的占位符
        String url = WECHAT_SEND_MESSAGE_URL.replace("${access_token}",accessToken);

        // 封装请求参数
        JSONObject params = new JSONObject();
        params.put("template_id", templateId);
        params.put("touser",openid);
        params.put("data",data);
        params.put("miniprogram_state","trial"); // 这个参数先写死，暂时没有变动需求
        params.put("lang","zh_CN"); // 这个参数先写死，暂时没有变动需求

        // 向微信发送post请求
        HttpClientUtil.postByBodyOfJson(url,params.toString());
    }

    /**
     * 发送订单进度提醒消息
     * 开始时间 {{time3.DATA}}
     * 订单进度 {{thing4.DATA}}
     * 订单编号 {{character_string5.DATA}}
     * 产品名称 {{thing10.DATA}}
     *
     * @param openid 微信用户openid
     */
    public void sendOrderProgressMessage(String openid) {

        // 封装消息内容
        JSONObject messageContent = new JSONObject();
        // 获取当前时间
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTime = LocalDateTime.now().format(dateTimeFormatter);
        JSONObject time3 = new JSONObject();
        time3.put("value",dateTime);
        messageContent.put("time3",time3);
        // 订单进度
        JSONObject thing4 = new JSONObject();
        thing4.put("value","待确认");
        messageContent.put("thing4",thing4);
        // 订单编号
        JSONObject character_string5 = new JSONObject();
        character_string5.put("value","1111111");
        messageContent.put("character_string5",character_string5);
        // 产品名称
        JSONObject thing10 = new JSONObject();
        thing10.put("value","团购商品");
        messageContent.put("thing10",thing10);

        // 发送消息
        this.sendMessage(openid,ORDER_PROGRESS_TEMPLATE_ID,messageContent);

    }

}
