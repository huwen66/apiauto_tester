package com.qa.util;

import com.qa.config.ApiParamsConfig;
import com.qa.pojo.ApiParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * api参数工具类
 *
 * @author vinson
 * @date 2018年7月18日
 * @desc
 * @email
 */
public class ApiParamUtil {


    private static Map<String, ApiParams> apiParamsMap;

    static {
        if (apiParamsMap == null) {
            apiParamsMap = new HashMap<String, ApiParams>();
        }

        List<ApiParams> apiParamsList = ApiParamsConfig.getApiParamsList();
        for (ApiParams apiParams : apiParamsList) {
            apiParamsMap.put(apiParams.getCaseId(), apiParams);
        }

    }


    public static Map<String, ApiParams> getApiParamsMap() {
        return apiParamsMap;
    }

    public static void main(String[] args) {

    }

}
