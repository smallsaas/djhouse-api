package com.jfeat.am.module.house.services.utility;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;

/**
 * @description: http/https请求工具，依赖于apache httpclient5
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/4/17 14:31
 * @author: hhhhhtao
 */
public class HttpClientUtil {


    /**
     * GET请求方法
     * @param url 请求路径
     * @return
     */
    public static String get(String url) {

        // 返回的结果
        String result = null;
        // 创建get实例
        HttpGet httpGet = new HttpGet(url);
        // 创建httpClient
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // 执行请求e
            CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
            // 在response中获取实体
            HttpEntity httpEntity = httpResponse.getEntity();
            // 拆包获取结果
            result = EntityUtils.toString(httpEntity);
        } catch (IOException e ) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
