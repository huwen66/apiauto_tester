package com.qa.pojo;

import lombok.Data;

/**
 * @ClassName PrepData
 * @author: vinson.hu
 * @Description TODO
 * @date 2021/5/21 16:41
 * @Version 1.0版本
 */
@Data
public class PrepData extends ExcelObject {

    /**
     * 执行的操作
     */
    private String operation;

    /**
     * 接口请求地址
     */
    private String url;

    /**
     * 接口参数类型： json |  form
     */
    private String parameterType;

    /**
     * 请求类型：post | get | delete |put
     */
    private String type;

    /**
     * 接口请求参数
     */
    private String requestData;

    /**
     * 接口响应内容(用来判断数据是否已生成)
     */
    private String message;
}
