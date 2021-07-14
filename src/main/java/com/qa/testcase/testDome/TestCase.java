package com.qa.testcase.testDome;

import org.testng.annotations.*;

public class TestCase {

    @Test
    public void testCase() {
        System.out.println("这是一个测试方法");
    }

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("套件执行前");
    }

    @BeforeClass
    public void beforClass() {
        System.out.println("测试类执行前");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("测试方法执行前");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("套件执行后");
    }

    @AfterClass
    public void afterClass() {
        System.out.println("测试类执行后");
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("测试方法执行后");
    }


}
