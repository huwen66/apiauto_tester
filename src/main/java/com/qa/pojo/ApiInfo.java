package com.qa.pojo;


/**
 * TODO
 *
 * @ClassName ApiInfo
 * @Author vinson.hu
 * @Date 2020/9/24
 * @Version V1.0
 **/
public class ApiInfo {

    /**
     * 行号
     */
    private int rowNum;

    /**
     * apiid
     */
    private String apiId;

    /**
     * api名
     */
    private String apiName;

    /**
     * 请求类型：post | get
     */
    private String type;

    /**
     * 接口请求地址
     */
    private String url;

    /**
     * 接口参数类型： json |  form
     */
    private String parameterType;

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
                "rowNum=" + rowNum +
                ", apiId='" + apiId + '\'' +
                ", apiName='" + apiName + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", parameterType='" + parameterType + '\'' +
                '}';
    }


}
