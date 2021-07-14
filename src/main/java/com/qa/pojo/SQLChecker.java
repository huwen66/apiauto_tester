package com.qa.pojo;

import lombok.Data;

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

@Data
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
}
