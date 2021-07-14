package com.qa.pojo;

import lombok.Data;

@Data
public class PublicVariables extends ExcelObject {

    /**
     * 变量名
     */
    private String key;
    /**
     * 变量的值
     */
    private String value;

    /**
     * 备注
     */
    private String remark;
}
