package com.qa.testcase;

import com.qa.util.ApiUtil;
import com.qa.util.HttpUtil;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @ClassName RolesTester
 * @Author vinson.hu
 * @Date 2020/9/24
 * @Version V1.0
 **/
public class RolesTester {
    @Test
    public void test001() {
        Map<String, String> headermap = new HashMap<String, String>();
        headermap.put("token", "8edaecf39bbca13bb9db91d7921eb812");
        headermap.put("appKey", "373bea3cf69972cc");
        String url = ApiUtil.getURLByApiId("7");
        String result = HttpUtil.get(url, headermap);
        System.out.println("-------" + result + "---------");
    }

}
