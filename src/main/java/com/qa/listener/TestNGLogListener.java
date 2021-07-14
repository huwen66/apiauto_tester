package com.qa.listener;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @ClassName TestNGLogListener
 * @author: vinson.hu
 * @Description testng日志监听类
 * @date 2021/1/13 16:58
 * @Version 1.0版本
 */
public class TestNGLogListener extends TestListenerAdapter {
    private static Logger logger = Logger.getLogger(TestNGLogListener.class);

    @Override
    public void onTestFailure(ITestResult tr) {
        log(String.format("[method: %s]", tr.getName()) + "--测试用例执行失败\n");
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        log(String.format("[method: %s]", tr.getName()) + "--测试用例执行跳过\n");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        log(String.format("[method: %s]", tr.getName()) + "--测试用例执行成功\n");
    }

    @Override
    public void onTestStart(ITestResult tr) {
        log(String.format("[method: %s]", tr.getName()) + "-- 开始执行用例");

    }

    private void log(String string) {
        logger.info(string);
    }
}
