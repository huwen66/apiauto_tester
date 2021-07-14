package com.qa.listener;


import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.DisabledRetryAnalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
/**
 * @ClassName RetryListener
 * @author: vinson.hu
 * @Description 重跑测试用例监听类
 * @date 2021/5/26 11:10
 * @Version 1.0版本
 */
public class RetryListener implements IAnnotationTransformer {
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        Class<? extends IRetryAnalyzer> retry = annotation.getRetryAnalyzerClass();
        if (retry == DisabledRetryAnalyzer.class) {
            annotation.setRetryAnalyzer(RetryImpl.class);
        }
    }
}
