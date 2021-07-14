package com.qa.util;

import com.alibaba.fastjson.JSONObject;
import com.qa.pojo.HttpResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName HttpClientUtil
 * @author: vinson.hu
 * @Description http请求工具类，支持post、get、delete、put
 * @date 2021/5/26 18:12
 * @Version 1.0版本
 */
public class HttpClientUtil {

    private static Logger logger = Logger.getLogger(HttpClientUtil.class);
    /**
     * 从连接池中获取连接的超时时间（单位：ms）
     */
    private static final int CONNECTION_REQUEST_TIMEOUT = 5000;

    /**
     * 与服务器连接的超时时间（单位：ms）
     */
    private static final int CONNECTION_TIMEOUT = 5000;

    /**
     * 从服务器获取响应数据的超时时间（单位：ms）
     */
    private static final int SOCKET_TIMEOUT = 10000;

    /**
     * 连接池的最大连接数
     */
    private static final int MAX_CONN_TOTAL = 100;

    /**
     * 每个路由上的最大连接数
     */
    private static final int MAX_CONN_PER_ROUTE = 50;

    /**
     * 用户代理配置
     */
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.67 Safari/537.36";

    /**
     * HttpClient对象
     */
    private static CloseableHttpClient httpClient = null;

    /**
     * Connection配置对象
     */
    private static ConnectionConfig connectionConfig = null;

    /**
     * Socket配置对象
     */
    private static SocketConfig socketConfig = null;

    /**
     * Request配置对象
     */
    private static RequestConfig requestConfig = null;

    /**
     * Cookie存储对象
     */
    private static BasicCookieStore cookieStore = null;

    /**
     * https
     */
    public static final String HTTPS = "https";
    /**
     * 默认编码
     */
    public static final String DEFAULT_ENCODING = "UTF-8";// 默认编码

    static {
        init();
    }


    /**
     * 全局对象初始化
     */
    private static void init() {
        // 创建Connection配置对象
        connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8).build();

        // 创建Socket配置对象
        socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();

        // 创建Request配置对象
        requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .setConnectTimeout(CONNECTION_TIMEOUT)
                .setSocketTimeout(SOCKET_TIMEOUT)
                .build();

        // 创建Cookie存储对象（服务端返回的Cookie保存在CookieStore中，下次再访问时才会将CookieStore中的Cookie发送给服务端）
        cookieStore = new BasicCookieStore();

        // 创建HttpClient对象
        httpClient = HttpClients.custom()
                .setDefaultConnectionConfig(connectionConfig)
                .setDefaultSocketConfig(socketConfig)
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(cookieStore)
                .setUserAgent(USER_AGENT)
                .setMaxConnTotal(MAX_CONN_TOTAL)
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .build();
    }

    /**
     * post请求，表单传参
     *
     * @param url
     * @param header
     * @param params
     * @return
     */
    public static HttpResult post(String url, Map<String, String> header, Map<String, String> params) {
        HttpResult httpResult = null;

        try {
            //创建post请求
            HttpPost post = new HttpPost(url);
            //添加请求头
            if (header != null && !header.isEmpty()) {
                for (String key : header.keySet()) {
                    String value = header.get(key);
                    post.addHeader(key, value);
                }
            }
            //添加请求参数
            if (params != null && !params.isEmpty()) {
                post.setEntity(assemblyFormEntity(params));
            }
            //如果是https就信任证书，连接客户端
            if (StringUtils.startsWithIgnoreCase(url, HTTPS)) {
                httpClient = httpClientSSLTrustCert();
            } else {
                httpClient = HttpClients.createDefault();
            }
            // 发送Post请求并得到响应结果
            httpResult = httpClient.execute(post, getResponseHandler());
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return httpResult;
    }

    /**
     * post请求，json传参
     * @param url
     * @param header
     * @param jsonParam
     * @return
     */

    public static HttpResult post(String url, Map<String, String> header, String jsonParam) {
        HttpResult httpResult = null;
        try {
            //创建post请求
            HttpPost post = new HttpPost(url);
            //添加请求头
            if (header != null && !header.isEmpty()) {
                for (String key : header.keySet()) {
                    String value = header.get(key);
                    post.addHeader(key, value);
                }
            }
            //添加请求参数
            post.setEntity(assemblyStringEntity(jsonParam));
            //如果是https就信任证书，连接客户端
            if (StringUtils.startsWithIgnoreCase(url, HTTPS)) {
                httpClient = httpClientSSLTrustCert();
            } else {
                httpClient = HttpClients.createDefault();
            }
            // 发送Post请求并得到响应结果
            httpResult = httpClient.execute(post, getResponseHandler());
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return httpResult;
    }


    /**
     * get请求
     *
     * @param url     请求地址
     * @param params  请求参数，key、value形式
     * @param headers 请求头
     * @return java.lang.String
     * @author vinson.hu
     * @date 2020/9/24 12:36
     */
    public static HttpResult get(String url, Map<String, String> headers, Map<String, String> params) {

        HttpResult httpResult = new HttpResult();
        try {
            // 创建URI并设置参数
            URIBuilder builder = new URIBuilder(url);
            if (params != null && !params.isEmpty()) {
                builder.addParameters(assemblyParameter(params));
            }
            URI uri = builder.build();
            // 创建HttpGet
            HttpGet get = new HttpGet(uri);
            // 设置Header
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    get.addHeader(key, value);
                }
            }
            if (StringUtils.startsWithIgnoreCase(url, HTTPS)) {
                //信任所有证书
                httpClient = httpClientSSLTrustCert();
            } else {
                httpClient = HttpClients.createDefault();
            }
            httpResult = httpClient.execute(get, getResponseHandler());

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return httpResult;
    }

    /**
     * put请求(带请求体)，json请求体
     *
     * @param url
     * @param headers
     * @param jsonParam
     * @return com.qa.pojo.HttpResult
     * @author vinson.hu
     * @date 2021/1/14 11:51
     */

    public static HttpResult put(String url, Map<String, String> headers, String jsonParam) {
        HttpResult httpResult = new HttpResult();
        try {
            //创建http请求
            HttpPut put = new HttpPut(url);
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    put.addHeader(key, value);
                }
            }
            //添加请求参数
            put.setEntity(assemblyStringEntity(jsonParam));

            if (StringUtils.startsWithIgnoreCase(url, HTTPS)) {
                //信任所有证书
                httpClient = httpClientSSLTrustCert();
            } else {
                httpClient = HttpClients.createDefault();
            }
            httpResult = httpClient.execute(put, getResponseHandler());
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return httpResult;
    }

    /**
     * delete(带请求体)，json请求体
     *
     * @param url
     * @param headers
     * @param jsonParam
     * @return com.qa.pojo.HttpResult
     * @author vinson.hu
     * @date 2021/1/14 11:52
     */

    public static HttpResult delete(String url, Map<String, String> headers, String jsonParam) {
        HttpResult httpResult = new HttpResult();
        try {
            HttpDeleteWithBody delete = new HttpDeleteWithBody(url);
            if (headers != null && !headers.isEmpty()) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    delete.addHeader(key, value);
                }
            }
            delete.setEntity(assemblyStringEntity(jsonParam));
            if (StringUtils.startsWithIgnoreCase(url, HTTPS)) {
                //信任所有证书
                httpClient = httpClientSSLTrustCert();
            } else {
                httpClient = HttpClients.createDefault();
            }
            httpResult = httpClient.execute(delete, getResponseHandler());

        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return httpResult;
    }


    /**
     * delete（form表单提交）
     *
     * @param url
     * @param headers
     * @param params
     * @return
     */
    public static HttpResult delete(String url, Map<String, String> headers, Map<String, String> params) {

        HttpResult httpResult = new HttpResult();
        try {
            URIBuilder builder = new URIBuilder(url);
            if (params != null) {
                builder.addParameters(assemblyParameter(params));
            }
            URI uri = builder.build();
            HttpDelete delete = new HttpDelete(uri);
            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    delete.setHeader(entry.getKey(), entry.getValue());
                }
            }
            if (StringUtils.startsWithIgnoreCase(url, HTTPS)) {
                //信任所有证书
                httpClient = httpClientSSLTrustCert();
            } else {
                httpClient = HttpClients.createDefault();
            }
            // 执行http请求
            httpResult = httpClient.execute(delete, getResponseHandler());
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        return httpResult;
    }


    /**
     * 解决DELETE没有setEntity()的问题
     *
     * @author vinson.hu
     * @return
     * @date 2021/1/14 10:52
     */

    private static class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
        public static final String METHOD_NAME = "DELETE";

        @Override
        public String getMethod() {
            return METHOD_NAME;
        }

        public HttpDeleteWithBody(final String uri) {
            super();
            setURI(URI.create(uri));
        }

        public HttpDeleteWithBody(final URI uri) {
            super();
            setURI(uri);
        }

        public HttpDeleteWithBody() {
            super();
        }
    }


    /**
     * 组装Post请求的Json请求体
     *
     * @param jsonData 请求的Json数据
     */
    private static StringEntity assemblyStringEntity(String jsonData) {
        /**
         * jsonData不能为null和""，否则无法创建StringEntity。
         * Json类型的请求体，必须传一个不为null的StringEntity给服务端。
         * 如果jsonData为null或""时，则进行特殊处理。
         */
        if (jsonData == null || jsonData.equals("")) {
            jsonData = "{}";
        }
        StringEntity stringEntity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);
        stringEntity.setContentEncoding(DEFAULT_ENCODING);
        return stringEntity;
    }


    /**
     * 组装Post请求的Form请求体
     *
     * @param parameters 请求的表单参数
     */
    private static UrlEncodedFormEntity assemblyFormEntity(Map<String, String> parameters) {
        List<NameValuePair> formParameters = assemblyParameter(parameters);
        UrlEncodedFormEntity formEntity = null;
        try {
            formEntity = new UrlEncodedFormEntity(formParameters, DEFAULT_ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return formEntity;
    }

    /**
     * 组装Get请求的请求参数
     *
     * @param parameters 参数信息集合
     */
    private static List<NameValuePair> assemblyParameter(Map<String, String> parameters) {
        List<NameValuePair> allParameter = new ArrayList<>();
        if (parameters != null && !parameters.isEmpty()) {
            for (String name : parameters.keySet()) {
                NameValuePair parameter = new BasicNameValuePair(name, parameters.get(name));
                allParameter.add(parameter);
            }
        }
        return allParameter;
    }

    /**
     * 接口请求，请求地址+请求方式+请求头+请求体+请求类型
     * 返回响应结果（不包含响应体）
     *
     * @param url        请求地址
     * @param type       请求方式，支持post/get/delete/put
     * @param params     请求参数
     * @param headers    请求头
     * @param submitType 请求类型，支持json/form
     * @return java.lang.String
     * @author vinson.hu
     * @date 2020/10/12 21:34
     */

    public static HttpResult requset(String url, String type, String params, Map<String, String> headers, String submitType) {
        logger.info("----------------开始请求接口----------------");
        HttpResult httpResult = new HttpResult();
        if (!StringUtils.isBlank(url) && StringUtils.isBlank(type) && StringUtils.isBlank(submitType)) {
            logger.error(String.format("url:[%s]或请求方式:[%s]或参数类型:[%s]为空，接口执行失败", url, type, submitType));
            throw new NullPointerException("必填的请求参数不能为空");
        }
        logger.info(String.format("url:[%s],请求方式:[%s],请求参数:[%s],请求头:[%s],参数类型:[%s]", url, type, params, headers, submitType));
        long start = System.currentTimeMillis();
        if ("json".equalsIgnoreCase(submitType)) {
            if ("post".equalsIgnoreCase(type)) {
                httpResult = post(url, headers, params);
            } else if ("put".equalsIgnoreCase(type)) {
                httpResult = put(url, headers, params);
            } else if ("delete".equalsIgnoreCase(type)) {
                httpResult = delete(url, headers, params);
            }
        } else if ("form".equalsIgnoreCase(submitType)) {
            Map<String, String> parametersMap = (Map<String, String>) JSONObject.parse(params);
            if ("post".equalsIgnoreCase(type)) {
                httpResult = post(url, parametersMap, headers);
            } else if ("get".equalsIgnoreCase(type)) {
                if (parametersMap == null) {
                    httpResult = get(url, headers, null);
                } else
                    httpResult = get(url, headers, parametersMap);
            } else if ("delete".equalsIgnoreCase(type)) {
                httpResult = delete(url, headers, parametersMap);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("接口请求耗时:" + (end - start) + "ms");
        logger.info("----------------结束请求接口----------------");
        return httpResult;
    }



    /**
     * 获取响应结果处理器（把响应结果封装成HttpResult对象）
     */
    private static ResponseHandler<HttpResult> getResponseHandler() {
        ResponseHandler<HttpResult> responseHandler = new ResponseHandler<HttpResult>() {
            @Override
            public HttpResult handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                if (httpResponse == null) {
                    throw new ClientProtocolException("HttpResponse is null");
                }

                StatusLine statusLine = httpResponse.getStatusLine();
                HttpEntity httpEntity = httpResponse.getEntity();
                if (statusLine == null) {
                    throw new ClientProtocolException("HttpResponse contains no StatusLine");
                }
//                if (statusLine.getStatusCode() == 500) {
//                    throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
//                }
                if (httpEntity == null) {
                    throw new ClientProtocolException("HttpResponse contains no HttpEntity");
                }

                HttpResult httpResult = new HttpResult();
                httpResult.setStatusCode(statusLine.getStatusCode());
                httpResult.setResult(EntityUtils.toString(httpEntity));
                httpResult.setHeaders(httpResponse.getAllHeaders());

                return httpResult;
            }
        };
        return responseHandler;
    }


    /**
     * 信任所有证书，解决https报SSLHandshakeException的问题
     *
     * @return CloseableHttpClient
     * @author vinson.hu
     * @date 2020/10/19 14:33
     */
    private static CloseableHttpClient httpClientSSLTrustCert() {
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustAllStrategy()).build();
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return HttpClients.createDefault();
    }

    public static void main(String[] args) {
        String url = "https://arkpaastest.analysys.cn:4005/ark/uba/api/events/analyze";
        String url1 = "https://arkpaastest.analysys.cn:4005/uba/api/cohort/18";
        String url2 = "https://arkpaastest.analysys.cn:4005/uba/manage/enterprise/accounts";
        String url3 = "https://arkpaastest.analysys.cn:4005/uba/api/cohort/140?loginUser=admin@analysys.com.cn";
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", "4e3337214cbb7044ffebff5595fe92af");
        headerMap.put("appKey", "autotestplatform");
        headerMap.put("xtoken", "2A2294FA5E6B01EABB73CC9072725660");
        String json = "{\"fromDate\":\"2020-08-01\",\"unit\":\"DAY\",\"measures\":[{\"expression\":\"event.$startup\",\"aggregator\":\"TRIGGER_USER_COUNT\"}],\"useCache\":true,\"samplingFactor\":1,\"toDate\":\"2020-08-07\",\"limit\":50,\"crowds\":[\"$ALL\"]}";
        String putJson = "{ \n" +
                "\t\"email\": \"api@analysys.com.cn\",\n" +
                "\t\"name\": \"apitest\", \n" +
                "\t\"password\": \"111111\",\n" +
                "\t\"department\": \"研发一部\" \n" +
                "\t\n" +
                "}";
//        String post = post(url, headerMap, json).getResult();
//        System.out.println(post);
//        String put = put(url2,headerMap,putJson).getResult();
//        System.out.println(put);
//        String delete = delete(url3,headerMap,"1").getResult();
//        System.out.println(delete);
//        String delete = delete(url3, headerMap, "").getResult();
//        System.out.println(delete);
//        for (int i = 0; i < 9; i++) {
//            String delete = delete(url3, headerMap, null).getResult();
//            System.out.println(delete);
//        }
    }
}

