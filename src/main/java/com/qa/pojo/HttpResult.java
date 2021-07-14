package com.qa.pojo;

import lombok.Data;
import org.apache.http.Header;

/**
 * @ClassName HttpResult
 * @author: vinson.hu
 * @Description 接口响应结果
 * @date 2021/1/13 23:06
 * @Version 1.0版本
 */
@Data
public class HttpResult {

    /**
     * 响应状态码
     */
    private int statusCode;
    /**
     * 响应内容
     */
    private String result;
    /**
     * 响应的header信息
     */
    private Header[] headers;


}
