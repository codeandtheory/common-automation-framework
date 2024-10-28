package com.yml.framework.reporting;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;
import com.yml.framework.common.CommonUtil;


public class ExtentManager {


    public static ExtentSparkReporter htmlReporter;
    public static String reportTitle;

    private static ExtentReports extent;
    private static ExtentTest test;


    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance(CommonUtil.getProjectDir() + "//reports//automation-report.html");

        return extent;
    }

    public static ExtentReports createInstance(String fileName) {
        // ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter = new ExtentSparkReporter(fileName);
        htmlReporter.viewConfigurer()
                .viewOrder()
                .as(new ViewName[]{ViewName.DASHBOARD,ViewName.TEST,ViewName.CATEGORY,ViewName.EXCEPTION,ViewName.LOG})
                .apply();
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("utf-8");
        setReportTitle(reportTitle);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        //extent.setSystemInfo("Host Name", "LOREAL-YML");
        return extent;
    }


    public static void setReportTitle(String reportTitle){
        if (reportTitle == null || reportTitle.equalsIgnoreCase(""))
            reportTitle = "UI Automation Report";
        htmlReporter.config().setDocumentTitle(reportTitle);
        htmlReporter.config().setReportName(reportTitle);
    }

    public static ExtentTest createTest(String name, String description) {
        test = extent.createTest(name, description);
        return test;
    }

}