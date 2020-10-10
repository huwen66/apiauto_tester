package com.qa.pojo;

/**
 * jsonpath多关键词匹配实体类
 */
public class JsonPathValidate {

    /**
     *匹配json表达式
     */

    private String expression;
    /**
     *实际的响应结果
     */
    private String value;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "JsonPathValidate{" +
                "expression='" + expression + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
