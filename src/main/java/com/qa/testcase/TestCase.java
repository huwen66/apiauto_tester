package com.qa.testcase;

import com.qa.config.ExcelDataConfig;
import com.qa.pojo.ApiInfo;
import com.qa.pojo.ApiParams;
import com.qa.pojo.HttpResult;
import com.qa.pojo.PrepData;
import com.qa.util.AssertUtil;
import com.qa.util.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BaseCase
 * @author: vinson.hu
 * @Description 测试用例
 * @date 2021/4/1 16:43
 * @Version 1.0版本
 */
public class TestCase extends BaseCase {
    private static Logger logger = Logger.getLogger(TestCase.class);

    /**
     * 暂时只支持创建分群、虚拟事件、维度表，缺少自定义指标、标签
     */

    @Test(dataProvider = "getPerpDatas", description = "API前置数据")
    public void test_001(String ip, String token, String appkey, PrepData prepData) {
        logger.info("-----------生成Open API前置数据开始-----------");
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("token", token);
        headerMap.put("appKey", appkey);

        HttpResult httpResult = HttpClientUtil.requset(ip + prepData.getUrl(), prepData.getType(), prepData.getRequestData(), headerMap, prepData.getParameterType());
        if (httpResult.getStatusCode() == 200) {
            logger.info(String.format("生成:[%s]前置数据成功", prepData.getOperation()));
        } else if (httpResult.getResult().contains(prepData.getMessage())) {
            logger.info(String.format("检测到:[%s]前置数据已存在", prepData.getOperation()));
        } else {
            logger.info(prepData.getMessage() + httpResult.getResult());
            logger.error(String.format("创建前置数据异常：[%s]", prepData.getOperation()));
            logger.error("响应结果："+httpResult.getResult());
            Assert.assertTrue(false);
        }
    }


    //    @Test(dataProvider = "getDatasByApiId", description = "OpenApi接口",threadPoolSize = 1, invocationCount = 1, timeOut = 10000)
    @Test(dataProvider = "getDatasByApiId", description = "OpenApi接口", dependsOnMethods = "test_001")
    public void test_002(ApiInfo apiInfo, ApiParams apiParams) throws InterruptedException {
        HttpResult httpResult = super.excete(apiInfo, apiParams);
        String result = httpResult.getResult();
        int status = httpResult.getStatusCode();
        //判断是否休眠
        if (!StringUtils.isBlank(apiParams.getSleep())) {
            Thread.sleep(Integer.parseInt(apiParams.getSleep()) * 1000);
        }
        //判断是否校验响应状态
        if (!StringUtils.isBlank(apiParams.getExpectedStatusCode())) {
            AssertUtil.assertStatus(status, apiParams.getExpectedStatusCode());
        }
        // 校验响应结果
        AssertUtil.assertResponse(result, apiParams.getExpectedReponseData());

    }

    /**
     * 根据接口编号获取的测试用例（数据提供者）
     *
     * @return
     */
    @DataProvider(parallel = false)  //开启并发
//    @DataProvider
    public Object[][] getDatasByApiId() {
        Object[][] datas = ExcelDataConfig.getCaseByApiId("41");
        return datas;
    }

    /**
     * 获取所有测试用例（数据提供者）
     *
     * @return
     */
    @DataProvider(parallel = false)
    public Object[][] getAllDatas() {
        Object[][] datas = ExcelDataConfig.getAllCase();
        return datas;
    }

    /**
     * 获取指定测试用例（数据提供者）
     *
     * @return
     */
    @DataProvider(parallel = true)
    public Object[][] getDatasByCase() {
        Object[][] datas = ExcelDataConfig.getCaseByCaseId("case_403");
        return datas;
    }

    /**
     * 获取前置接口的数据（跑自动化的前置数据）
     *
     * @param
     * @return java.lang.Object[][]
     * @author vinson.hu
     * @date 2021/5/21 17:18
     */

    @DataProvider
    public Object[][] getPerpDatas() {
        Object[][] datas = ExcelDataConfig.getPrepData();
        return datas;
    }
}
