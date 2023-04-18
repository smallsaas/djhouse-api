package com.jfeat.am.module.house.services.utility;


import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;

import java.io.IOException;

/**
 * @description: http/https请求工具，依赖于apache httpclient5
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/4/17 14:31
 * @author: hhhhhtao
 */
public class HttpClientUtil {


    public static String get(String url) {

        String result = null;

        HttpGet httpGet = new HttpGet(url);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse httpResponse = httpClient.execute(httpGet)) {
            HttpEntity entity = httpResponse.getEntity();
            result = entity.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
