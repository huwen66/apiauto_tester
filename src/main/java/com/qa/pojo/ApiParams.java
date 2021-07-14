package com.qa.pojo;


import lombok.Data;

@Data
public class ApiParams extends ExcelObject {
    /**
     * 案例id
     */
    private String caseId;
    private String caseName;
    private String apiId;//(接口编号)
    private String requestData;//(接口请求参数)
    private String expectedReponseData;//(期望响应数据)
    private String actualResponseData;//(实际响应数据)
    private String expectedStatusCode;//(期望响应状态)
    private String actualStatusCode;//(实际响应状态)
    private String preValidateSql;//(接口执行前的脚本验证)
    private String preValidateResult;//(接口执行前数据库验证结果)
    private String afterValidateSql;//(接口执行后的脚本验证)
    private String afterValidateResult;//(接口执行后数据库验证结果)
    private String postData;//(后置数据，赋值到变量)
    private String sleep; //(接口休眠时间,秒为单位)
    private String run;//(是否执行)
}
