package com.qa.pojo;


public class CellData {
    /**
     * 测试用例的id
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


    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public CellData(String caseId, int cellNum, String dataStr) {
        super();
        this.caseId = caseId;
        this.cellNum = cellNum;
        this.dataStr = dataStr;
    }

    @Override
    public String toString() {
        return "CellData [caseId=" + caseId + ", cellNum=" + cellNum + ", dataStr=" + dataStr + "]";
    }
}
