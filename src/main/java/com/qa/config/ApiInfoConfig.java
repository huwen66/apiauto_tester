package com.qa.config;

import com.qa.pojo.ApiInfo;
import com.qa.util.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

import static com.qa.constants.Constants.EXCLE_PATH;


public class ApiInfoConfig {

    private static List<ApiInfo> apiInfoList;

    static {
        apiInfoList = new ArrayList<ApiInfo>();

        //加载excel中apiInfo的数据，存在ApiInfo对象中
        ExcelUtil.read(EXCLE_PATH, 0, ApiInfo.class);
    }

    //

    /**
     * 获取ApiInfo List数据
     *
     * @return java.util.List<com.qa.pojo.ApiInfo>
     * @author vinson.hu
     * @date 2020/9/24 15:53
     */
    public static List<ApiInfo> getApiInfoList() {
        return apiInfoList;
    }

    public static void main(String[] args) {
        for (ApiInfo apiInfo : apiInfoList) {
            System.out.println(apiInfo);
        }
    }

}
