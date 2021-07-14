package com.qa.util;

/**
 * @ClassName JSONUtil
 * @author: vinson.hu
 * @Description TODO
 * @date 2021/4/27 11:31
 * @Version 1.0版本
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;

public class JSONUtil {

    /**
     * 根据key获取字符串的value
     *
     * @param text 指定字符串
     * @param key  键
     * @return
     */
    public static String getValue(String text, String key) {
        JSONObject o = JSONObject.parseObject(text);
        String value = o.get(key) == null ? "" : o.get(key).toString();
        return value;
    }

    /**
     * 过滤指定字段
     *
     * @param json json对象
     * @param arr  过滤的字段
     */
    public static Object jsonFilter(Object json, String... arr) {

        PropertyFilter propertyFilter = new PropertyFilter() {
            @Override
            public boolean apply(Object object, String name, Object value) {
                for (String string : arr) {
                    if (name.equalsIgnoreCase(string)) {
                        return false;// 过滤掉
                    }
                }
                return true;// 不过滤
            }
        };
        json = JSON.toJSONString(json, propertyFilter);
        return json;
    }


    public static void main(String[] args) {
        String text = "{\"l1\":{\"l1_1\":[\"l1_1_1\",\"l1_1_2\"],\"l1_2\":{\"l1_2_1\":121}},\"l2\":{\"l2_1\":null,\"l2_2\":true,\"l2_3\":{}}}";
        String text1 = "[{\"l1\":{\"l1_1\":[\"l1_1_1\",\"l1_1_2\"],\"l1_2\":{\"l1_2_1\":121}},\"l2\":{\"l2_1\":null,\"l2_2\":true,\"l2_3\":{}}},{\"l1\":{\"l1_1\":[\"l1_1_1\",\"l1_1_2\"],\"l1_2\":{\"l1_2_1\":121}},\"l2\":{\"l2_1\":null,\"l2_2\":true,\"l2_3\":{},\"111\":222}}]";
        String text2 = "{\"taskId\":\"51a1a71f831c4c0a8401cf409ea1f77b\",\"status\":2,\"result\":{\"series\":[\"2020/08/01 00:00:00\",\"2020/08/02 00:00:00\",\"2020/08/03 00:00:00\",\"2020/08/04 00:00:00\",\"2020/08/05 00:00:00\",\"2020/08/06 00:00:00\",\"2020/08/07 00:00:00\"],\"measures\":[\"event.$startup.TRIGGER_USER_COUNT\"],\"byFields\":[],\"rows\":[{\"sum\":[1384],\"values\":[[227,264,237,238,241,215,236]],\"byValue\":[]}],\"numRows\":1,\"reportUpdateTime\":\"2020-11-10 15:30:50\",\"truncated\":false},\"queueNo\":null,\"queueIdle\":null}";
//        String key ="l1_1";
//        getValue(text,key);

        String str = "l1.l1_1";
//        System.out.println(getColumns(text2, "result"));
//        System.out.println(getValue(text2, "result"));
//        System.out.println(jsonFilter(JSONObject.parse(text), "l1_1"));
        System.out.println(getValue(text2, "series"));
    }

}
