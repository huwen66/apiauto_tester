package com.qa.pojo;


import lombok.Data;

/**
 * @ClassName ApiInfo
 * @Author vinson.hu
 * @Date 2020/9/24
 * @Version V1.0
 **/

@Data
public class ApiInfo extends ExcelObject {


    /**
     * apiid
     */
    private String apiId;

    /**
     * api名
     */
    private String apiName;

    /**
     * 请求类型：post | get | delete |put
     */
    private String type;

    /**
     * 接口归属模块
     */
    private String model;

    /**
     * 接口请求地址
     */
    private String url;

    /**
     * 接口参数类型： json |  form
     */
    private String parameterType;


}
