package com.qa.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.qa.pojo.CellData;
import com.qa.pojo.JsonExtractor;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;

import static com.qa.constants.Constants.EXCLE_PATH;

/**
 * @ClassName ExtractorUtil
 * @author: vinson.hu
 * @Description 响应结果提取工具类
 * @date 2021/3/26 14:36
 * @Version 1.0版本
 */
public class ExtractorUtil {

    private static Logger logger = Logger.getLogger(ExtractorUtil.class);

    /**
     * 数据提取器，获取响应结果赋值到变量中
     *
     * @param result     接口的响应结果
     * @param expression 提取参数表达式，格式为："{\"jsonPath\":\"json路径\",\"variableName\":\"变量名\"}";
     */
    public static void dataExtractor(String result, String expression) {
        logger.info("-----------提取响应结果开始-----------");
        if (StringUtils.startsWithAny(expression, "[", "{")) {
            Object jsonObject = JSONObject.parse(expression);
            //如果是jsonarry，循环赋值变量
            if (jsonObject instanceof JSONArray) {
                List<JsonExtractor> list = JSONObject.parseArray(expression, JsonExtractor.class);
                for (JsonExtractor jsonExtractor : list) {
                    String jsonPath = jsonExtractor.getJsonPath();
                    String variableName = jsonExtractor.getVariableName();
                    String value = JSONPath.read(result, jsonPath) == null ? "" : JSONPath.read(result, jsonPath).toString();
                    //赋值全局变量
                    ParamUtil.addGlobalData(variableName, value);
                    //写入excel

                    ExcelUtil.publicVarsList.add(new CellData(variableName, 2, value));
//                    ExcelUtil.writeExcel(EXCLE_PATH,2,variableName,2,value);
                }
            } else {
                //json字符串转为JsonExtractor实体类
                JsonExtractor jsonExtractor = JSONObject.parseObject(expression, JsonExtractor.class);
                String jsonPath = jsonExtractor.getJsonPath();
                String variableName = jsonExtractor.getVariableName();
                String value = JSONPath.read(result, jsonPath) == null ? "" : JSONPath.read(result, jsonPath).toString();
                ParamUtil.addGlobalData(variableName, value);
                ExcelUtil.writeExcel(EXCLE_PATH,2,variableName,2,value);
            }
        } else {
            if ( StringUtils.contains(expression,"=")){
               String [] array= expression.split("=");
                ParamUtil.addGlobalData(array[0], array[1]);
                ExcelUtil.writeExcel(EXCLE_PATH,2,array[0],2, array[1]);
            }
        }
        logger.info("-----------提取响应结果结束-----------");
    }

    public static void main(String[] args) {
//        String str = "[{\"jsonPath\":\"$.code\",\"variableName\":\"test\"},{\"jsonPath\":\"$.code\",\"variableName\":\"test1\"}]";
        String str = "{\"jsonPath\":\"$.code\",\"variableName1\":\"test\"}";
        String result = "{\"code\":\"200\"}";
        dataExtractor(result, str);
    }
}
