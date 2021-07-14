package com.qa.testcase.testDome;

import org.testng.annotations.Test;

/**
 * @ClassName IgnoreCase
 * @author: vinson.hu
 * @Description TODO
 * @date 2021/3/17 15:48
 * @Version 1.0版本
 */
public class IgnoreCase {


    @Test(enabled = true)
    void test_001(){
        System.out.println("这是用例test_001");
    }

    @Test(enabled = false)
    void test_002(){
        System.out.println("这是用例test_002");
    }
}
