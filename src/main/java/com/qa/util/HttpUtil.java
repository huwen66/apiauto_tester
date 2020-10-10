package com.qa.util;

import com.alibaba.fastjson.JSONObject;
import com.qa.testcase.BaseTester;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.xml.ws.handler.LogicalHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 发包工具类
 *
 * @author vinson
 * @date 2018年7月13日
 * @desc
 * @email
 */
public class HttpUtil {

    private static Logger logger = Logger.getLogger(HttpUtil.class);

    /**
     * post方法
     *
     * @param url           请求地址
     * @param parametersMap 参数map
     * @return
     */
    public static String post(String url, Map<String, String> parametersMap) {
        logger.info("-----------开始调用接口请求---------------");

        try {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
            Set<String> keySet = parametersMap.keySet();
            for (String key : keySet) {
                parameterList.add(new BasicNameValuePair(key, parametersMap.get(key)));

            }
            post.setEntity(new UrlEncodedFormEntity(parameterList));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * post请求（包含请求头），json字符串请求体
     *
     * @param url       请求地址
     * @param jsonParam json字符串请求体
     * @param headers   请求头
     * @return 返回请求结果
     */
    public static String post(String url, String jsonParam, Map<String, String> headers) {
        try {
            HttpPost post = new HttpPost(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    post.addHeader(key, value);
                }
            }
            StringEntity stringEntity = new StringEntity(jsonParam, "utf-8");
            stringEntity.setContentType("application/json");
            post.setEntity(stringEntity);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * post请求（包含请求头），json字符串请求体
     *
     * @param url           请求地址
     * @param parametersMap 请求参数 map
     * @param headers       请求头
     * @return 返回请求结果
     */
    public static String post(String url, Map<String, String> parametersMap, Map<String, String> headers) {
        try {
            HttpPost post = new HttpPost(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    post.addHeader(key, value);
                }
            }
            List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
            Set<String> keySet = parametersMap.keySet();
            for (String key : keySet) {
                parameterList.add(new BasicNameValuePair(key, parametersMap.get(key)));

            }
            post.setEntity(new UrlEncodedFormEntity(parameterList));
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 不带参数，get请求
     *
     * @param url 请求地址
     * @return
     */
    public static String get(String url, Map<String, String> headers) {
        logger.info("-------------接口请求执行开始---------------");
        logger.info(String.format("请求地址：[%s],请求参数：[%s]", url, headers));
        try {
            HttpGet get = new HttpGet(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    get.addHeader(key, value);
                }
            }
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get);
            String result = EntityUtils.toString(response.getEntity());
            logger.info("-------------接口请求执行结束---------------");
            return result;
        } catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
        }
        return "";
    }


    /**
     * get请求,带请求头
     *
     * @param url           请求地址
     * @param parametersMap 请求参数，key、value形式
     * @param headers       请求头
     * @return java.lang.String
     * @author vinson.hu
     * @date 2020/9/24 12:36
     */
    public static String get(String url, Map<String, String> parametersMap, Map<String, String> headers) {
        try {
            List<NameValuePair> parameterList = new ArrayList<NameValuePair>();
            Set<String> keySet = parametersMap.keySet();
            for (String key : keySet) {
                parameterList.add(new BasicNameValuePair(key, parametersMap.get(key)));
            }
            //组合拼接参数
            String urlParamsStr = URLEncodedUtils.format(parameterList, "utf-8");

            HttpGet get = new HttpGet(url + "?" + urlParamsStr);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    get.addHeader(key, value);
                }
            }
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(get);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 发送 http put 请求，参数以原生字符串进行提交
     *
     * @param url
     * @param jsonParam
     * @param headers
     * @return java.lang.String
     * @author vinson.hu
     * @date 2020/9/24 12:34
     */
    public static String put(String url, String jsonParam, Map<String, String> headers) {
        try {
            HttpPut put = new HttpPut(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    put.addHeader(key, value);
                }
            }
            StringEntity stringEntity = new StringEntity(jsonParam, "utf-8");
            stringEntity.setContentType("application/json");
            put.setEntity(stringEntity);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse response = httpClient.execute(put);
            String result = EntityUtils.toString(response.getEntity());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 发起get或者post请求
     *
     * @param url            请求地址
     * @param type           请求类型：post、get
     * @param parametersMap： 参数列表
     * @return
     */

    public static String requset(String url, String type, Map<String, String> parametersMap) {
        String result = "";
        if ("post".equalsIgnoreCase(type)) {
            result = post(url, parametersMap);
        } else if ("get".equalsIgnoreCase(type)) {
            result = get(url, parametersMap);
        }
        return result;
    }


    private static String delete(String url, Map<String, String> headersMap) {
        return "";
    }

    public static String requset(String url, String type, String params, Map<String, String> headersMap, String submitType) {
        String result = "";
        Map<String, String> parametersMap = (Map<String, String>) JSONObject.parse(params);

        if ("json".equalsIgnoreCase(submitType)) {
            if ("post".equalsIgnoreCase(type)) {
                result = post(url, params, headersMap);
            } else if ("get".equalsIgnoreCase(type)) {
                result = get(url, headersMap);
            } else if ("put".equalsIgnoreCase(type)) {
                result = put(url, params, headersMap);
            } else if ("delete".equalsIgnoreCase(type)) {
                result = delete(url, headersMap);
            }
        } else if ("form".equalsIgnoreCase(submitType)) {
            if ("post".equalsIgnoreCase(type)) {
                result = post(url, parametersMap, headersMap);
            } else if ("get".equalsIgnoreCase(type)) {
                if (parametersMap == null) {
                    result = get(url, headersMap);
                } else
                    result = get(url, parametersMap, headersMap);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("mobilephone", "13825161923"));
        parameters.add(new BasicNameValuePair("password", "e10adc3949ba59abbe56e057f20f883e"));
        parameters.add(new BasicNameValuePair("type", "1"));
        String str = URLEncodedUtils.format(parameters, "utf-8");
        System.out.println(str);
    }
}
