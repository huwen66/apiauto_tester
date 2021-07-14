package com.qa.testcase.testDome;

import org.testng.annotations.Test;


public class ExceptionCase {

    /**
     * case执行失败，实际结果没有异常
     */
    @Test(expectedExceptions = RuntimeException.class)
    void runTimeExceptionFail(){
        System.out.println("这是一个失败的异常测试");
    }


    /**
     * case执行成功，捕获到期望的异常
     */
    @Test(expectedExceptions = RuntimeException.class)
    void runTimeExceptionSuccess(){
        System.out.println("这是一个成功的异常测试");
        throw new RuntimeException();
    }
}
