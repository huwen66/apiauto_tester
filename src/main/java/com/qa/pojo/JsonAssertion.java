package com.qa.pojo;


import lombok.Data;

/**
 * @ClassName JsonAssertion
 * @author: vinson.hu
 * @Description 根据jsonpath匹配关键词的实体类(用于断言)
 * @date 2021/3/26 10:21
 * @Version 1.0版本
 */
@Data
public class JsonAssertion {

    /**
     * jsonPath表达式
     */
    private String jsonPath;
    /**
     * 实际的响应结果
     */
    private String value;

}
