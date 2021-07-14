package com.qa.testcase;

import com.qa.pojo.ApiInfo;
import com.qa.pojo.ApiParams;
import com.qa.pojo.CellData;
import com.qa.pojo.HttpResult;
import com.qa.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

import static com.qa.constants.Constants.EXCLE_PATH;
import static com.qa.constants.Constants.EXCLE_WRITE_BACK_PATH;


public class BaseCase {
    private static Logger logger = Logger.getLogger(BaseCase.class);


    /**
     * 获取接口信息进行发包
     *
     * @param apiInfo
     * @param apiParams
     * @return java.lang.String
     * @author vinson.hu
     * @date 2021/1/15 13:32
     */
    public HttpResult call(ApiInfo apiInfo, ApiParams apiParams) {
        logger.info("--------------------准备执行用例--------------------");
        //请求头
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", ParamUtil.getGlobalData("token"));
        headerMap.put("appKey", ParamUtil.getGlobalData("appKey"));
        headerMap.put("xtoken", ParamUtil.getGlobalData("xtoken"));

        //替换url的请求参数中的变量
        String url = ParamUtil.replaceParams(apiInfo.getUrl());
        String Params = ParamUtil.replaceParams(apiParams.getRequestData());

        //接口请求的参数
        url = ParamUtil.getGlobalData("ip") + url;
        String type = apiInfo.getType();
        String submitType = apiInfo.getParameterType();
        //发包
        HttpResult httpResult = HttpClientUtil.requset(url, type, Params, headerMap, submitType);
        //提取响应参数赋值到全局变量中
        if (!StringUtils.isBlank(apiParams.getPostData())) {
            ExtractorUtil.dataExtractor(httpResult.getResult(), apiParams.getPostData());
        }
        //回写数据到excel
        addWBD(apiParams.getCaseId(), 6, httpResult.getResult());
        logger.info("------------------回写数据成功----------------------");
        Reporter.log("请求地址:" + url);
        Reporter.log("模块:" + apiInfo.getApiName());
        Reporter.log("请求参数:" + Params);
        Reporter.log("实际响应结果:" + httpResult.getResult());
        Reporter.log("期望响应结果:" + apiParams.getExpectedReponseData());
        Reporter.log("实际响应状态:" + httpResult.getStatusCode());
        Reporter.log("期望响应状态:" + apiParams.getExpectedStatusCode());
        logger.info("url:" + url);
        logger.info("headerMap:" + headerMap.get("token"));
        logger.info("appKey:" + headerMap.get("appKey"));
        logger.info("Params:" + Params);
        logger.info("result:" + httpResult.getResult());
        logger.info("--------------------结束执行用例--------------------");
        return httpResult;
    }

    @BeforeSuite
    public void beforeSuite() {

    }

    //批量回写数据
    @AfterSuite
    public void afterSuite() {
        logger.info("------回写数据到excel文件-----");
        //回写公共参数
        ExcelUtil.batchWriteVars(EXCLE_PATH,2);
        //回写接口响应结果
        ExcelUtil.batchWriteData(EXCLE_PATH, EXCLE_WRITE_BACK_PATH, 1);
    }

    /**
     * 执行接口的方法
     *
     * @param apiInfo
     * @param apiParams
     * @return java.lang.String
     * @author vinson.hu
     * @date 2021/1/15 13:32
     */

    public HttpResult excete(ApiInfo apiInfo, ApiParams apiParams) {
        return call(apiInfo, apiParams);
    }


    /**
     * 回写数据对象到集合中
     *
     * @param caseId  用例id
     * @param cellNum 行号
     * @param body    内容
     * @return void
     * @author vinson.hu
     * @date 2021/1/15 13:39
     */

    public void addWBD(String caseId, int cellNum, String body) {
        logger.info("-------填写回写的数据------");
        ExcelUtil.dataToWriteList.add(new CellData(caseId, cellNum, body));
        logger.info("-------回写单条数据成功------");
    }


}
