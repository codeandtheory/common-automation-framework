package com.ct.framework.prerequisite.providers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.ct.framework.prerequisite.ThreadScoped;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.ct.framework.common.Platform;
import com.ct.framework.prerequisite.PlatformDriverManager;
import org.openqa.selenium.WebDriver;


public class PlatformDriverProvider implements Provider<WebDriver> {

    @Inject
    Platform platform;

    @Inject
    PlatformDriverManager driverHelper;

    @Inject
    private ExtentReports extent;

    private ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    @ThreadScoped
    @Override
    public WebDriver get() {


        ExtentTest test=extent.createTest("Platform Specific Driver Creation");
        WebDriver platformSpecificDriver = null;
        try {
            if (platform.isAndroid()|| platform.isIOS()) {
                test.info("Starting Appium Server");
                driverHelper.invokeAppium();
                test.info("Appium Server Started Successfully");
                test.info("Creating Platform Specific Driver for " + platform.getPlatformName());
            }
            if (!platform.isRestApis()) {
                platformSpecificDriver = driverHelper.createDriver(platform.getPlatformName());
                test.info("Driver Creation is successful");
            }
            else {
                test.info("Driver Creation not Required for platform "+platform.getPlatformName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (threadLocalDriver.get() == null) {
            // Create a new WebDriver instance if it doesn't exist
           // or any driver you're using
            threadLocalDriver.set(platformSpecificDriver);
            System.out.println("Created a new WebDriver instance for thread: " + Thread.currentThread().getId());
        }
        //return platformSpecificDriver;
        return threadLocalDriver.get();
    }


}
