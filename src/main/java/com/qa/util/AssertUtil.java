package com.qa.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.qa.pojo.JsonPathValidate;
import org.apache.log4j.Logger;
import org.testng.Assert;

import java.util.List;

/**
 * 断言类，目前只支持响应结果断言
 */
public class AssertUtil {
    private static Logger logger = Logger.getLogger(Assert.class);

    /**
     * 响应结果断言（Json）
     * 如果expected是JsonArray，采用多字段匹配断言
     * 如果expected是Json，采用全匹配断言
     *
     * @param actual   实际响应结果
     * @param expected 期望响应结果
     * @return 返回boolean值
     */
    public static boolean assertResponse(String actual, String expected) {
        logger.info("----------------响应结果断言开始----------------");
//        logger.info(String.format("实际响应结果:%s-------期望响应实体：%s",actual,expected));
        boolean flag = false;
        Object jsonObject = JSONObject.parse(expected);
        if (jsonObject instanceof JSONArray) {
            logger.info("----------------JsonPath逻辑断言----------------");
            List<JsonPathValidate> list = JSONObject.parseArray(expected, JsonPathValidate.class);
            for (JsonPathValidate jonPathValidate : list) {
                String expression = jonPathValidate.getExpression();
                String value = jonPathValidate.getValue();
                String actualvlue = JSONPath.read(actual, expression) == null ? "" : JSONPath.read(actual, expression).toString();
                flag = value.equals(actualvlue);
                if (flag == false) {
                    logger.error("-----------断言失败---------------");
                    break;
                }
            }
            //如果是JSONArray，则直接对比校验
        } else if (jsonObject instanceof JSONObject) {
            logger.info("----------------全匹配模式断言----------------");
            flag = actual.equals(expected);
        }
        System.out.println("--------" + flag + "----------");
        Assert.assertTrue(flag, "断言失败");
        return flag;
    }

}
