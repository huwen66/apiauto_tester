package com.qa.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName VariablesUtil
 * @author: vinson.hu
 * @Description 接口参数工具类
 * @date 2021/1/4 14:21
 * @Version 1.0版本
 */
public class ParamUtil {


    private static Logger logger = Logger.getLogger(ParamUtil.class);//日志

    /**
     * 全部变量map
     */
    private static Map<String, String> globalData = new HashMap<>();

    /**
     * 匹配全局参数的正则表达式
     */
    private static final String PARAM_REGEX = "\\$\\{(.*?)\\}";

    /**
     * 匹配自定义方法的正则表达式，格式为__funtionname(参数)
     */
    private static final String FUN_REGEX = "__(\\w*?)\\(((\\w*,?)*)\\)";


    /**
     * 添加一个全局数据
     *
     * @param key
     * @param value
     */
    public static void addGlobalData(String key, String value) {
        logger.info(String.format("写入全局变量key:%s,value:%s", key, value));
        globalData.put(key, value);
    }

    /**
     * 获取全局变量的值
     *
     * @param key
     * @return
     */
    public static String getGlobalData(String key) {
        return globalData.get(key);
    }


    /**
     * 正则匹配自定义函数，进行结果值的替换
     *
     * @param content 匹配的内容
     * @return
     */
    public static String getFuntionStr(String content) {

        Pattern pattern = Pattern.compile(FUN_REGEX);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            //正则匹配到的原始值->____random(param1,param2)
            String totalStr = matcher.group(0);
            //得到方法名->random(匹配表达式第一个括号的值)
            String methodName = matcher.group(1);
            //得到参数->param1,param2(匹配表达式第二个括号的值)
            String params = matcher.group(2);
            Class clazz = FuntionUtil.class;
            try {
                String result = "";
                //如果参数为空
                if (StringUtils.isBlank(params)) {
                    Method method = clazz.getMethod(methodName);
                    result = (String) method.invoke(null);
                } else {
                    //参数放在数组中
                    String[] paramArray = params.split(",");
                    int length = paramArray.length;
                    //声明参数类型的数组对象
                    Class<?>[] typeAarry = new Class[length];
                    //循环进行赋值数据类型，统一定义为string类型
                    for (int i = 0; i < length; i++) {
                        typeAarry[i] = String.class;
                    }
                    Method method = clazz.getMethod(methodName, typeAarry);
                    result = (String) method.invoke(null, paramArray);
                }
                //替换内容中的值
                content = content.replace(totalStr, result);
            } catch (NoSuchMethodException e) {
                logger.error("不存在的方法",e);
            } catch (IllegalAccessException e) {
                logger.error(e);
            } catch (InvocationTargetException e) {
                logger.error(e);
            }
        }
        return content;
    }

    /**
     * 正则匹配替换变量，正则匹配规则为${value}
     *
     * @param content
     * @return java.lang.String
     * @author vinson.hu
     * @date 2021/1/4 20:08
     */
    public static String replaceParams(String content) {
            //编译这个正则表达式-->得到一个模式对象
            Pattern pattern = Pattern.compile(PARAM_REGEX);
            //进行字符串的模式匹配 -->得到匹配对象
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                //正则匹配到的原始值
                String totalStr = matcher.group(0);
                //正则匹配解析后的内容
                String paramsName = matcher.group(1);
                content = content.replace(totalStr, getGlobalData(paramsName) == null ? totalStr : getGlobalData(paramsName));

            }
        return getFuntionStr(content);
    }

    public static void main(String[] args) {
        addGlobalData("Test", "wwww");
        String str = "Test${Test}1,${test2}__RandomStr()";
//        String str = "TestTest}1,test2}RandomStr()";
        System.out.println(replaceParams(str));

    }
}
