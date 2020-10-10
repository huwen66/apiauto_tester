package com.qa.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonUtil {

    public static Object getValue(String text, String key) {
        JSONObject o = JSONObject.parseObject(text);
        Object value = o.get(key);
        System.out.println(value);
        return "";
    }

    public static String getColumns(String json, String... cols) {

        int len = cols.length;
        JSONObject jsonObject;
        String[][] keyWord = new String[len][];
        for (int i = 0; i < len; i++) {
            keyWord[i] = cols[i].split("\\.");
        }
        StringBuffer colVal = new StringBuffer();
        for (int i = 0; i < len; i++) {
            jsonObject = JSON.parseObject(json);
            for (int j = 0; j < keyWord[i].length - 1; j++) {
                jsonObject = (JSONObject) jsonObject.get(keyWord[i][j]);
            }
            if (colVal.toString().length() < 1) {
                colVal.append(jsonObject.get(keyWord[i][keyWord[i].length - 1]).toString());
            } else {
                colVal.append("," + jsonObject.get(keyWord[i][keyWord[i].length - 1]).toString());
            }
            jsonObject = null;
        }
        return colVal.toString();
    }

    public static String get(String json, String... cols) {
        int len = cols.length;
        String[][] keyWord = new String[len][];
        for (int i = 0; i < len; i++) {
            keyWord[i] = cols[i].split("\\.");
        }
        if (json.substring(0, 1).equals("[")) {
            JSONArray jsonObject = JSONArray.parseArray(json);
            for (int i = 0; i < jsonObject.size(); i++) {
                JSONObject ob = jsonObject.getJSONObject(i);
                ob.get("");
            }
            System.out.println(jsonObject);
        }
        return "";
    }

    public static void main(String[] args) {
        String text = "{\"l1\":{\"l1_1\":[\"l1_1_1\",\"l1_1_2\"],\"l1_2\":{\"l1_2_1\":121}},\"l2\":{\"l2_1\":null,\"l2_2\":true,\"l2_3\":{}}}";
        String text1 = "[{\"l1\":{\"l1_1\":[\"l1_1_1\",\"l1_1_2\"],\"l1_2\":{\"l1_2_1\":121}},\"l2\":{\"l2_1\":null,\"l2_2\":true,\"l2_3\":{}}},{\"l1\":{\"l1_1\":[\"l1_1_1\",\"l1_1_2\"],\"l1_2\":{\"l1_2_1\":121}},\"l2\":{\"l2_1\":null,\"l2_2\":true,\"l2_3\":{},\"111\":222}}]";
//        String key ="l1_1";
//        getValue(text,key);

        String str = "l1.l1_1";
        System.out.println(getColumns(text, str, "l1"));
    }
}
