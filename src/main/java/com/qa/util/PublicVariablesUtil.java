package com.qa.util;

import com.qa.config.ExcelDataConfig;
import com.qa.pojo.PublicVariables;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PublicVariablesUtil
 * @author: vinson.hu
 * @Description 公共变量工具类
 * @date 2021/5/17 15:23
 * @Version 1.0版本
 */
public class PublicVariablesUtil {

    //根据key拿到PublicVariables的所有信息
    private static Map<String, PublicVariables> pubVarsMap;

    static {
        if (pubVarsMap == null) {
            pubVarsMap = new HashMap<>();
        }
        List<PublicVariables> pubVarsList = ExcelDataConfig.getPubVarsList();
        for (PublicVariables publicVariables : pubVarsList) {

            pubVarsMap.put(publicVariables.getKey(), publicVariables);
        }
    }

    /**
    *   根据key获取整行数据
    * @param key
    * @return com.qa.pojo.PublicVariables
    * @author vinson.hu
    * @date 2021/5/17 17:27
    */

    public static PublicVariables getPubVarsByKey(String key) {
        return pubVarsMap.get(key);
    }

    /**
     * 根据变量名获取变量值
     * @param key
     * @return com.qa.pojo.String
     * @author vinson.hu
     * @date 2021/5/17 17:27
     */
    public static String getPubValueByKey(String key) {
        return pubVarsMap.get(key).getValue();
    }

    public static Map<String, PublicVariables> getPubVarsMap() {
        return pubVarsMap;
    }



    public static void main(String[] args) {
        System.out.println(getPubVarsMap().get("phone"));
    }
}
