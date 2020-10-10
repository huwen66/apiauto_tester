package com.qa.testcase;

import com.alibaba.fastjson.JSONObject;
import com.qa.config.ApiParamsConfig;
import com.qa.util.*;
import org.apache.http.client.ClientProtocolException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.pojo.CellData;

import java.io.IOException;
import java.util.Map;
import java.util.Set;


/**
 * @author tommy
 * @date 2018年7月11日
 * @desc
 * @email
 */
public class RegisterTester extends BaseTester {

    @Test(dataProvider = "getDatas")
    public void test_case_01(String caseId, String apiId, String requestData, String expectedReponseData,
                             String preValidateSql, String afterValidateSql)
            throws ClientProtocolException, IOException {
        System.out.println(caseId + "," + apiId + "" + requestData);
        //0：前置验证，把验证结果写回excel
        if (!StringUtil.isEmpty(preValidateSql)) {
            String preValidateResultStr = SQLCheckUtil.getSQLCheckResult(preValidateSql);
            ExcelUtil.dataToWriteList.add(new CellData(caseId, 7, preValidateResultStr));
        }

        //1：确定请求的url
        String url = ApiUtil.getURLByApiId(apiId);
        //2：确定请求的方式：post、get
        String type = ApiUtil.getRequestTypeByApiId(apiId);
        //3:确定请求的参数
        Map<String, String> parametersMap = (Map<String, String>) JSONObject.parse(requestData);
        //4:发包
        String resultStr = HttpUtil.requset(url, type, parametersMap);
        //5：结果会写
        //		ExcelUtil.writeExcel("/rest_info.xlsx", 1, caseId, 5, resultStr);
        //频繁的读写不合理--》一次性写--》当我的所有的测试用例全部执行完的时候，只写一次--》afterSuite方法
        ExcelUtil.dataToWriteList.add(new CellData(caseId, 5, resultStr));

        //6:校验或其他操作
        System.out.println("---------------------------------------");
        System.out.println(resultStr);
        System.out.println("---------------------------------------");
        //7：后置验证，把验证结果写回excel
        if (!StringUtil.isEmpty(afterValidateSql)) {
            String afterValidateResultStr = SQLCheckUtil.getSQLCheckResult(afterValidateSql);
            ExcelUtil.dataToWriteList.add(new CellData(caseId, 9, afterValidateResultStr));
        }

    }

    /**
     * 数据提供者
     *
     * @return
     */
    @DataProvider
    public Object[][] getDatas() {
        //读取出我测试用例需要的各种参数来--》
//		Object[][] datas = ExcelUtil.readExcel("/rest_info.xlsx", 1, 2, 12, 1, 8);
        Object[][] datas = ApiParamsConfig.getApiParameters();
        return datas;
    }

}
