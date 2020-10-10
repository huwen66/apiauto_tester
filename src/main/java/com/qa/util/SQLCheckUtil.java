package com.qa.util;

import com.alibaba.fastjson.JSONObject;
import com.qa.pojo.CheckResult;
import com.qa.pojo.SQLChecker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLCheckUtil {

    public static String getSQLCheckResult(String validateSql) {
        //验证这条sql--》解析出单条sql（把no，sql，expectedResult提取出来，解析出来） --》类-->SQLChecker
        List<SQLChecker> sqlCheckerList = JSONObject.parseArray(validateSql, SQLChecker.class);

        //一个测试用例sql验证多条sql对应有多个结果--》容器中间--》list
        List<CheckResult> checkResultList = new ArrayList<CheckResult>();

        for (SQLChecker sqlChecker : sqlCheckerList) {
            //实际的结果
            List<Map<String, String>> actualResultList = JDBCUtil.query(sqlChecker.getSql());
            String actualResultStr = JSONObject.toJSONString(actualResultList);
            //期望的结果
            List<Map<String, String>> expectedResultList = sqlChecker.getExpectedResultList();
            String expectedResultStr = JSONObject.toJSONString(expectedResultList);

            CheckResult checkResult = null;
            if (actualResultStr.equals(expectedResultStr)) {
                checkResult = new CheckResult(sqlChecker.getNo(), actualResultList, "成功");
            } else {
                checkResult = new CheckResult(sqlChecker.getNo(), actualResultList, "失败");
            }
            //添加到结果列表
            checkResultList.add(checkResult);
        }
        //要写会到excel中的json字符串
        return JSONObject.toJSONString(checkResultList);
    }

}
