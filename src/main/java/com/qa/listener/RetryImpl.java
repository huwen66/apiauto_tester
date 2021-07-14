package com.qa.listener;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * @ClassName RetryImpl
 * @author: vinson.hu
 * @Description 用例执行失败重跑机制（testng7.0+版本）
 * @date 2021/5/26 11:09
 * @Version 1.0版本
 */
public class RetryImpl implements IRetryAnalyzer {
    private int retryCount = 1;
    private static final int maxRetryCount = 2; // 控制用例失败自动重试几次

    @Override
    synchronized public boolean retry(ITestResult result) {
        if (retryCount < maxRetryCount) {
            Reporter.setCurrentTestResult(result);
            retryCount++;
            return true;
        }
        return false;
    }
}
