package com.qa.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 验证结果对象
 *
 * @author vinson
 * @date 2018年7月20日
 * @desc
 * @email
 */
@Data
public class CheckResult {

    /**
     * 验证sql编号
     */
    private String no;

    /**
     * 实际的结果
     */
    private List<Map<String, String>> actualResultList;

    /**
     * 验证的结果：通过  or 不通过
     */
    private String result;

    public CheckResult(String no,List<Map<String, String>> actualResultList,String result){

    }
}
