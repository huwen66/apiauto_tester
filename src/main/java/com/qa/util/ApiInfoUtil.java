package com.qa.util;

import com.qa.config.ExcelDataConfig;
import com.qa.pojo.ApiInfo;

import java.io.IOException;
import java.util.*;

import static com.qa.constants.Constants.CONFIG_FILE_PATH;


/**
 * api工具类
 * 通过apiId拿到api的基本信息
 *
 * @author vinson
 * @date 2018年7月18日
 * @desc
 */
public class ApiInfoUtil {
    //根据apiId拿到api的所有信息
    static Map<String, ApiInfo> apiInfoMap;
    static String host = "";

    static {
        if (apiInfoMap == null) {
            apiInfoMap = new HashMap<>();
        }

        List<ApiInfo> apiInfoList = ExcelDataConfig.getApiInfoList();
        for (ApiInfo apiInfo : apiInfoList) {
            apiInfoMap.put(apiInfo.getApiId(), apiInfo);
        }
        try {
            //读取配置文件，获取ip地址
            Properties properties = new Properties();
            properties.load(ApiInfoUtil.class.getResourceAsStream(CONFIG_FILE_PATH));
            host = properties.getProperty("Host");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过apiId获得api的url信息
     *
     * @param apiId
     * @return
     */
    public static String getURLByApiId(String apiId) {
        return host + apiInfoMap.get(apiId).getUrl();
    }

    /**
     * 通过apiId获得api的请求类型信息
     *
     * @param apiId
     * @return
     */
    public static String getRequestTypeByApiId(String apiId) {
        return apiInfoMap.get(apiId).getType();
    }

    /**
     * 通过apiId获得api的参数提交类型
     *
     * @param apiId
     * @return java.lang.String
     * @author vinson.hu
     * @date 2020/9/24 18:08
     */
    public static String getSubmitTypeByApiId(String apiId) {
        return apiInfoMap.get(apiId).getParameterType();
    }

    public static ApiInfo getApiInfoByApiId(String apiId) {
        return apiInfoMap.get(apiId);
    }


    public static void main(String[] args) {
        Set<String> keySet = apiInfoMap.keySet();
        for (String key : keySet) {
//            System.out.println(apiInfoMap.get(key));
        }

//        System.out.println(getRequestTypeByApiId("1222"));
        System.out.println(getApiInfoByApiId("3"));
    }

}
