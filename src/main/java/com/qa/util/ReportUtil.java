package com.qa.util;

import org.testng.Reporter;

import java.util.Calendar;

/**
 * TODO
 *
 * @ClassName ReportUtil
 * @Author vinson.hu
 * @Date 2020/10/11
 * @Version V1.0
 **/
public class ReportUtil {
    private static String reportName = "API自动化测试报告";
    private static String splitTimeAndMsg = "===";
    public static void log(String msg) {
        long timeMillis = Calendar.getInstance().getTimeInMillis();
        Reporter.log(timeMillis + splitTimeAndMsg + msg, true);
    }

    public static String getReportName() {
        return reportName;
    }

    public static String getSpiltTimeAndMsg() {
        return splitTimeAndMsg;
    }
}
