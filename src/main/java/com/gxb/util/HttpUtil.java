package com.gxb.util;

import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: gxb
 * @Time: 2020/3/31 17:43
 * @Description:
 */
public final class HttpUtil {

    private static final int SOCKET_TIME_OUT = 5000;
    private static final int CONNECT_TIME_OUT = 5000;
    private static final int CONNECT_REQUEST_TIME_OUT = 5000;
    private static final boolean CONTENT_COMPRESSION_ENABLED = true;

    private static final int MAX_TOTAL = 100;
    private static final int DEFAULT_MAX_PER_ROUTE = 50;


    private HttpUtil() {

    }

    /**
     * 创建默认的使用了连接池的httpclient
     *
     * @return httpclient
     */
    public static CloseableHttpClient defaultPoolClient() {
        return HttpClients.custom()
                .setConnectionManager(defaultPoolManager())
                .setKeepAliveStrategy(defaultStrategy())
                .setDefaultRequestConfig(defaultConfig())
                .build();
    }


    /**
     * 创建默认的连接池
     *
     * @return httpclient连接池
     */
    public static PoolingHttpClientConnectionManager defaultPoolManager() {
        return defaultPoolManager(MAX_TOTAL, DEFAULT_MAX_PER_ROUTE);
    }

    /**
     * 创建连接池
     *
     * @param max 连接池最大连接数
     * @param per 每个路由最大连接数
     * @return httpclient 连接池
     */
    public static PoolingHttpClientConnectionManager defaultPoolManager(int max, int per) {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(max);
        cm.setDefaultMaxPerRoute(per);
        return cm;
    }

    /**
     * 默认的配置
     *
     * @return RequestConfig
     */
    public static RequestConfig defaultConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIME_OUT)
                .setConnectTimeout(CONNECT_TIME_OUT)
                .setConnectionRequestTimeout(CONNECT_REQUEST_TIME_OUT)
                .setContentCompressionEnabled(CONTENT_COMPRESSION_ENABLED)
                .build();
    }

    /**
     * 获取表单格式的参数
     *
     * @param params 参数
     * @return 表单
     */
    public static UrlEncodedFormEntity getFormEntity(Map<String, String> params) {
        List<NameValuePair> form = new ArrayList<>(params.size());
        params.forEach((k, v) -> form.add(new BasicNameValuePair(k, v)));
        return new UrlEncodedFormEntity(form, StandardCharsets.UTF_8);
    }

    /**
     * 获取默认的响应处理 返回字符串
     *
     * @return String
     */
    public static ResponseHandler<String> stringResponseHandler() {
        return response -> EntityUtils.toString(response.getEntity());
    }

    public static ConnectionKeepAliveStrategy defaultStrategy() {
        return new DefaultConnectionKeepAliveStrategy();
    }

}
