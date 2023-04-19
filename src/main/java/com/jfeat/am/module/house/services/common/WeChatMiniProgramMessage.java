package com.jfeat.am.module.house.services.common;

import com.jfeat.am.module.house.services.utility.HttpClientUtil;
import org.apache.hc.core5.http.HttpEntity;

/**
 * @description: 微信小程序消息
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/4/13 15:26
 * @author: hhhhhtao
 */
public class WeChatMiniProgramMessage {

    // 匠城小程序 appid
    private static final String HOUSE_APPID = "wx0723324978613c32";
    // 匠城小程序 appSecret
    private static final String HOUSE_APP_SECRET = "c97ecac59a40a68fc4160b957127fc70";

    // 微信小程序获取接口调用凭证api url,请求方法：GET
    private static final String WECHAT_GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${appid}&secret=${secret}";

    /**
     * 获取接口调用凭据
     * 请求的是微信官方域名，不做证书验证
     * @return "{"access_token": 获取到的凭证，"expires_in": 有效时间}"
     */
    public static String getAccessToken() {
        // 替换url中的占位符
        String url = null;
        url = WECHAT_GET_ACCESS_TOKEN_URL.replace("${appid}",HOUSE_APPID).replace("${secret}",HOUSE_APP_SECRET);
        // 执行get请求,获取返回值
        String requestResult = HttpClientUtil.get(url);
        return requestResult;
    }

}
