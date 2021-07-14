package com.qa.listener;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.Iterator;

/**
 * @ClassName ResultListener
 * @author: vinson.hu
 * @Description TODO
 * @date 2021/5/26 14:33
 * @Version 1.0版本
 */
public class ResultListener extends TestListenerAdapter {
    @Override
    public void onTestFailure(ITestResult tr) {
        if(tr.getMethod().getCurrentInvocationCount()==1)
        {
            super.onTestFailure(tr);
            return;
        }

        processSkipResult(tr);
        super.onTestFailure(tr);
    }
    @Override
    public void onTestSuccess(ITestResult tr) {
        if(tr.getMethod().getCurrentInvocationCount()==1)
        {
            super.onTestSuccess(tr);
            return;
        }
        processSkipResult(tr);
        super.onTestSuccess(tr);
    }
    public void processSkipResult(ITestResult tr)
    {
        ITestContext iTestContext = tr.getTestContext();
        Iterator<ITestResult> processResults = iTestContext.getSkippedTests().getAllResults().iterator();
        while (processResults.hasNext()) {
            ITestResult skippedTest = (ITestResult) processResults.next();
            if (skippedTest.getMethod().getMethodName().equalsIgnoreCase(tr.getMethod().getMethodName()) ) {
                processResults.remove();
            }
        }
    }
}
