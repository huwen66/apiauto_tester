package com.qa.util;

import com.qa.config.ExcelDataConfig;
import com.qa.pojo.ApiParams;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


/**
 * 接口参数工具类
 *
 * @author vinson.hu
 * @date 2021/1/13 18:13
 */

public class ApiParamUtil {

    //根据caseId拿到apiparams的所有信息
    private static Map<String, ApiParams> apiParamsMap;

    private static List<ApiParams> apiParamsList;

    static {
        if (apiParamsMap == null) {
            apiParamsMap = new HashMap<>();
        }
        if (apiParamsList == null) {
            //获取接口测试用例数据
            apiParamsList = ExcelDataConfig.getApiParamsList();
            //获取过滤不执行的测试用户数据
            apiParamsList = filterApiParamsList();

        }
    }

    /**
     * 获取接口请求参数list
     * 过滤掉不用执行的用例
     *
     * @return java.util.List<com.qa.pojo.ApiParams>
     * @author vinson.hu
     * @date 2021/1/4 17:43
     */
    public static List<ApiParams> getApiParamsList() {
        return apiParamsList;
    }

    /**
     * 过滤不执行的接口测试数据
     *
     * @return
     */
    private static List<ApiParams> filterApiParamsList() {
        Iterator<ApiParams> iterator = apiParamsList.iterator();
        while (iterator.hasNext()) {
            ApiParams apiParams = iterator.next();
            if (!StringUtils.equals(apiParams.getRun(), "是")) {
                iterator.remove();
            }
        }
        return apiParamsList;
    }

    /**
     * 获取接口请求的参数
     *
     * @return java.util.Map<java.lang.String, com.qa.pojo.ApiParams>
     * @author vinson.hu
     * @date 2021/1/13 18:13
     */

    public static Map<String, ApiParams> getApiParamsMap() {
        for (ApiParams apiParams : apiParamsList) {
            apiParamsMap.put(apiParams.getCaseId(), apiParams);
        }
        return apiParamsMap;
    }

    /**
     * 根据用例id获取API请求信息
     *
     * @param caseId
     * @return void
     * @author vinson.hu
     * @date 2021/1/13 18:24
     */
    public static ApiParams getApiParamByCaseId(String caseId) {

        return apiParamsMap.get(caseId);
    }

    /**
     * 根据apiId获取API请求信息
     *
     * @param apiId 接口的id
     * @return java.util.List<com.qa.pojo.ApiParams>
     * @author vinson.hu
     * @date 2021/1/15 11:34
     */

    public static List<ApiParams> getApiParamByApiId(String apiId) {
        List<ApiParams> list = new ArrayList<>();
        for (ApiParams apiParams : apiParamsList) {
            if (apiParams.getApiId().equals(apiId)) {
                list.add(apiParams);
            }
        }
        return list;
    }


    public static void main(String[] args) {
        List<ApiParams> list = getApiParamsList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
