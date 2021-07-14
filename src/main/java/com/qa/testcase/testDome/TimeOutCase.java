package com.qa.testcase.testDome;

import org.testng.annotations.Test;

/**
 * @ClassName TimeOutCase
 * @author: vinson.hu
 * @Description TODO
 * @date 2021/3/17 15:37
 * @Version 1.0版本
 */
public class TimeOutCase {

    /**
     * case 执行成功，实际等待时间在3s内
     */
    @Test(timeOut = 3000)
    void testSuccess() throws InterruptedException {
        //线程等待2s
        Thread.sleep(2000);
    }

    /**
     * case 执行失败，实际等待时间超过3s
     */
    @Test(timeOut = 3000)
    void testFail() throws InterruptedException {
        Thread.sleep(4000);
    }

}
