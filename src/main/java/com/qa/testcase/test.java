package com.qa.testcase;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.qa.pojo.JsonPathValidate;

import java.util.List;

public class test {

    public static void main(String[] args) {
        String[] arr = {"IOS", "Android", "JS"};
        System.out.println(arr[(int) Math.floor(Math.random() * arr.length)]);
        String body = "[{\"expression\":\"$.value.test\",\"value\":\"dd\"}]";
        String body1 = "{\"expression\":\"$.value\",\"value\":{\"test\":\"1\",\"test1\":\"2\"}}";
        Object ob = JSONObject.parse(body);
        List<JsonPathValidate> list = JSONObject.parseArray(body, JsonPathValidate.class);
        for (JsonPathValidate jonPathValidate : list) {
            String expression = jonPathValidate.getExpression();
            String value = jonPathValidate.getValue();
            System.out.println(JSONPath.read(body1, expression).toString());
//            System.out.println(JSONPath.read(body, expression) == null ? "" : JSONPath.read(body, expression).toString());
        }
    }
}
