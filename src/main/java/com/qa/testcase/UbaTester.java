package com.qa.testcase;


import com.alibaba.fastjson.JSONObject;
import com.qa.config.ApiParamsConfig;
import com.qa.pojo.CellData;
import com.qa.util.ApiUtil;
import com.qa.util.AssertUtil;
import com.qa.util.ExcelUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.util.HttpUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UbaTester extends BaseTester {

    @Test(dataProvider = "getDatas")
    public void test_001(String caseId, String apiId, String requestData, String expectedReponseData,
                         String preValidateSql, String afterValidateSql) {
        String url = ApiUtil.getURLByApiId(apiId);
        String type = ApiUtil.getRequestTypeByApiId(apiId);
        String submitType = ApiUtil.getsubmitTypeByApiId(apiId);
        //请求头
        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("token", "8edaecf39bbca13bb9db91d7921eb812");
        headerMap.put("appKey", "373bea3cf69972cc");
        //发起请求
        String result = HttpUtil.requset(url, type, requestData, headerMap, submitType);
        System.out.println("-------------" + caseId + "--------" + result);
        //回写响应结果到excel
        ExcelUtil.dataToWriteList.add(new CellData(caseId, 5, result));
        AssertUtil.assertResponse(result,expectedReponseData);
    }

    @DataProvider
    public Object[][] getDatas() {
        Object[][] datas = ApiParamsConfig.getApiParameters();
        return datas;
    }

    public static void main(String[] args) {
        String mapStr = "{\"name\":\"张三\",\"性别\":\"女\"]";
        Map<String, String> parametersMap = (Map<String, String>) JSONObject.parse(mapStr);
        Set<String> keySet = parametersMap.keySet();
        for (String key : keySet) {
            System.out.println(key + "--" + parametersMap.get(key));
        }
    }
}
