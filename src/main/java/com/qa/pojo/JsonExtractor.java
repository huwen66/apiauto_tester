package com.qa.pojo;

import lombok.Data;

/**
 * @ClassName JsonExtractor
 * @author: vinson.hu
 * @Description 根据jsonpath提取数据的实体类（用于后置数据处理）
 * @date 2021/3/26 10:21
 * @Version 1.0版本
 */

@Data
public class JsonExtractor {
    /**
     * jsonPath表达式
     */
    private String jsonPath;
    /**
     * 存放值的变量名称
     */
    private String variableName;

    @Override
    public String toString() {
        return "JsonExtractor{" +
                "jsonPath='" + jsonPath + '\'' +
                ", variableName='" + variableName + '\'' +
                '}';
    }
}
