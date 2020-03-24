package com.base.utils.type;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**TODO http工具类
 * @author 夏家鹏
 * @date 2020/02/24 11:47:35
 * @version V1.0
 * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
 */
public class HttpClientUtils {

    /**TODO post请求
     * @author 夏家鹏
     * @date 2020/02/24 11:47:35
     * @version V1.0
     * @copyright 广东跑合中药材有限公司 Copyright (c) 2020
     */
    public static String doPostByxml(String url, String requestDataXml) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = null;
        String resultString = "";
        // 创建Http Post请求连接对象
        HttpPost httpPost = new HttpPost(url);
        // 超时时间设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(15000) //连接服务器主机超时时间
                .setConnectionRequestTimeout(60000) //连接请求超时时间
                .setSocketTimeout(60000) //设置读取响应数据超时时间
                .build();
        //为httpPost请求设置参数
        httpPost.setConfig(requestConfig);
        try {
            //将上传参数存放到entity属性中
            httpPost.setEntity(new StringEntity(requestDataXml, "UTF-8"));
            //添加头信息
            httpPost.addHeader("Content-Type", "text/xml");

            //发送请求
            httpResponse = httpClient.execute(httpPost);
            //从响应对象中获取返回内容
            HttpEntity httpEntity = httpResponse.getEntity();
            resultString = EntityUtils.toString(httpEntity, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultString;
    }
}
