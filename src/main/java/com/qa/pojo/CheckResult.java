package com.qa.pojo;

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

    public CheckResult(String no, List<Map<String, String>> actualResultList, String result) {
        super();
        this.no = no;
        this.actualResultList = actualResultList;
        this.result = result;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Map<String, String>> getActualResultList() {
        return actualResultList;
    }

    public void setActualResultList(List<Map<String, String>> actualResultList) {
        this.actualResultList = actualResultList;
    }

    @Override
    public String toString() {
        return "CheckResult [no=" + no + ", actualResultList=" + actualResultList + ", result=" + result + "]";
    }
}
