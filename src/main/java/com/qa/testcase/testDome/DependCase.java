package com.qa.testcase.testDome;

import org.testng.annotations.Test;


public class DependCase {

    @Test
    void test_001() {
        System.out.println("这是test_001方法");
//        throw new RuntimeException();
    }

    /**
     * 依赖test_001方法，先执行test_001方法，后执行test_002方法
     * 如test_001执行失败，则不执行test_002方法
     */
    @Test(dependsOnMethods = "test_001")
    void test_002() {
        System.out.println("这是test_002方法");
    }


    @Test(dependsOnMethods = "test_002")
    void test_003() {
        System.out.println("这是test_003方法");
    }
}
