package com.yml.loreal.reporting;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.yml.loreal.common.CommonUtil;
import com.yml.loreal.pojo.Platform;


public class ExtentManager {


    public static String platformName;

    public static String reportTitle;

	public static String runMode;
	
    private static ExtentReports extent;
    private static ExtentTest test;
    public static ExtentReports getInstance() {
    	if (extent == null)
    		 createInstance(CommonUtil.getProjectDir()+"//reports//automation-report.html");

    	return extent;
    }
    
    public static ExtentReports createInstance(String fileName) {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setEncoding("utf-8");
        if (reportTitle == null || reportTitle.equalsIgnoreCase(""))
            reportTitle="UI Automation Report";

        htmlReporter.config().setDocumentTitle(reportTitle);
        htmlReporter.config().setReportName(reportTitle);
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        //extent.setSystemInfo("Host Name", "LOREAL-YML");
        extent.setSystemInfo("Automation Platform",platformName);
         return extent;
    }
    
    
    public static ExtentTest createTest(String name, String description){
		test = extent.createTest(name, description);
		return test;
	}
    
}