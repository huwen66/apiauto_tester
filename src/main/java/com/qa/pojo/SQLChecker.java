package com.qa.pojo;

import java.util.List;
import java.util.Map;

/**
 * sql验证对象
 *
 * @author vinson
 * @date 2018年7月20日
 * @desc
 * @email
 */
public class SQLChecker {

    /**
     * sql的编号
     */
    private String no;

    /**
     * 需要验证sql
     */
    private String sql;

    /**
     * 期望的结果列表
     */
    private List<Map<String, String>> expectedResultList;


    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<Map<String, String>> getExpectedResultList() {
        return expectedResultList;
    }

    public void setExpectedResultList(List<Map<String, String>> expectedResultList) {
        this.expectedResultList = expectedResultList;

    }

    @Override
    public String toString() {
        return "SQLChecker [no=" + no + ", sql=" + sql + ", expectedResultList=" + expectedResultList + "]";
    }


}
