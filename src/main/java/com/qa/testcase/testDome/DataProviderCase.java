package com.qa.testcase.testDome;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataProviderCase {


    @Test(dataProvider = "data")
    public void test_001(String value, String key) {//参数个数必须和DataProvider返回的一致
        System.out.println(value + ":" + key);
    }

  // 定义了属性name，提取数据时可以使用name，没有定义时，提取数据时直接使用方法名
    @DataProvider(name = "data")
    public Object[][] getDatas() {
        return new Object[][]{
                {"JS", "网页"},
                {"IOS", "苹果"}};
    }
}
