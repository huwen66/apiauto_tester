package com.qa.pojo;

import lombok.Data;

@Data
public class CellData {
    /**
     * 测试用例的id(或者第一行的key)
     */
    private String caseId;

    /**
     * 列号
     */
    private int cellNum;

    /**
     * 要写的数据
     */
    private String dataStr;

    public CellData(String caseId, int cellNum, String dataStr) {
        super();
        this.caseId = caseId;
        this.cellNum = cellNum;
        this.dataStr = dataStr;

    }

}
