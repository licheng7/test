package com.kaoorange.test.util.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created with IDEA
 * author:licheng
 * Date:2019/9/4
 * Time:上午10:06
 **/
public class HttpUtil {

    static volatile PoolingHttpClientConnectionManager cm = null;
    static volatile ConnectionKeepAliveStrategy kaStrategy = null;
    static volatile HttpClientBuilder httpClientBuilder = null;

    public static CloseableHttpClient getClient() {
        if(cm == null) {
            cm = getCm();
        }

        if(kaStrategy == null) {
            kaStrategy = getKaStrategy();
        }

        if(httpClientBuilder == null) {
            httpClientBuilder = getHttpClientBuilder();
        }

        CloseableHttpClient httpClient = httpClientBuilder.build();
        return httpClient;
    }

    private static synchronized PoolingHttpClientConnectionManager getCm() {
        if(cm == null) {
            cm = new PoolingHttpClientConnectionManager();
            // 连接池最大连接数
            cm.setMaxTotal(200);
            // 单条链路最大连接数（一个ip+一个端口 是一个链路）
            cm.setDefaultMaxPerRoute(100);
        }
        return cm;
    }

    private static synchronized ConnectionKeepAliveStrategy getKaStrategy() {
        if(kaStrategy == null) {
            kaStrategy = new DefaultConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    long keepAlive = super.getKeepAliveDuration(response, context);
                    if (keepAlive == -1) {
                        keepAlive = 60000;
                    }
                    return keepAlive;
                }
            };
        }
        return kaStrategy;
    }

    private static synchronized HttpClientBuilder getHttpClientBuilder() {
        if(httpClientBuilder == null) {
            httpClientBuilder = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setKeepAliveStrategy(kaStrategy);
        }
        return httpClientBuilder;
    }

    public static String doGet(String url) {
        CloseableHttpClient closeableHttpClient = getClient();
        HttpUriRequest request = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            close(closeableHttpClient);
        }

        if(httpResponse != null) {
            try {
                HttpEntity entity = httpResponse.getEntity();
                return EntityUtils.toString(entity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public static void close(CloseableHttpClient httpClient) {
        if(httpClient == null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }
}
