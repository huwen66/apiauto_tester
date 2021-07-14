package com.qa.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.qa.pojo.JsonAssertion;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.List;

/**
 * 断言类，目前只支持响应结果断言
 */
public class AssertUtil {
    private static Logger logger = Logger.getLogger(AssertUtil.class);
    static final String[] arr = new String[]{"reportUpdateTime", "taskId", "code", "createTime", "id", "calculatedTime", "name", "calculatedTime"};               //申请需要过滤的字段


    /**
     * 响应结果断言（Json）
     * 如果expected是JsonArray，采用多字段匹配断言
     * 如果expected是Json，采用全匹配断言
     *
     * @param actual   实际响应结果
     * @param expected 期望响应结果
     * @return void
     * @author vinson.hu
     * @date 2021/1/11 12:33
     */

    public static void assertResponse(String actual, String expected) {
        logger.info("----------------expected----------------" + expected);
        logger.info("----------------响应结果断言开始----------------");
        //替换期望响应结果中的变量
        expected =ParamUtil.replaceParams(expected);
        if (StringUtils.startsWithAny(expected, "[", "{")) {
            Object jsonObject = JSONObject.parse(expected);
            //如果是JSONArray，比较指定字段
            if (jsonObject instanceof JSONArray) {
                logger.info("----------------JsonPath逻辑断言----------------");
                List<JsonAssertion> list = JSONObject.parseArray(expected, JsonAssertion.class);
                for (JsonAssertion jsonAssertion : list) {
                    //获取期望结果的jsonpath
                    String jsonPath = jsonAssertion.getJsonPath();
                    //期望对比结果
                    String expectedValue = jsonAssertion.getValue();
                    //根据jsonpath，拿到实际对比的内容
                    String actualValue = JSONPath.read(actual, jsonPath) == null ? "" : JSONPath.read(actual, jsonPath).toString();
                    Assert.assertEquals(actualValue, expectedValue, "jsonArray断言失败");
                }
                //如果是JSON，则直接对比校验
            } else if (jsonObject instanceof JSONObject) {
                logger.info("----------------全匹配模式断言----------------");
                //过滤掉不比较的字段
                actual = JSONUtil.jsonFilter(JSONObject.parse(actual), arr).toString();
                expected = JSONUtil.jsonFilter(JSONObject.parse(expected), arr).toString();
                Assert.assertEquals(actual, expected, "json断言失败");
                //如果字符串，直接对比校验
            }
        }
        else {
            Assert.assertTrue(actual.contains(expected),"字符串对比断言失败,实际结果："+actual+"期望结果："+expected);
//            Assert.assertEquals(actual, expected, "字符串对比断言失败");
        }
        logger.info("----------------响应结果断言结束----------------");
    }

    /**
     * 断言响应状态，如果返回500直接断言失败
     * @param actual    实际响应状态
     * @param expected  期望响应状态
     */
    public static void assertStatus(int actual, String expected) {
        logger.info("----------------断言响应状态开始----------------");
        if(actual==500){
            Assert.fail();
        }
        Assert.assertEquals(actual,Integer.parseInt(expected));
        logger.info("----------------断言响应状态结束----------------");
    }


    //测试方法
    public static void main(String[] args) {
        String actual = "[{\"code\":\"$ALL\",\"name\":\"所有用户\",\"dynamic\":1,\"id\":1,\"userNumber\":null,\"status\":\"success\",\"calculatedTime\":\"2020-09-28 07:14:42\"},{\"code\":\"arkfq_2\",\"name\":\"autotest\",\"dynamic\":0,\"id\":2,\"userNumber\":2729,\"status\":\"success\",\"calculatedTime\":\"2021-02-20 11:33:34\"}]";
        String expected = "[{\"jsonPath\":\"[:0]\",\"value\":\"\"}]";
//        String actual = "123";
//        String expected = "345";
        AssertUtil.assertResponse(actual, expected);
    }
}
