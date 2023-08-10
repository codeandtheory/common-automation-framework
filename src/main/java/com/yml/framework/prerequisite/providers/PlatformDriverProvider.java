package com.yml.framework.prerequisite.providers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.yml.framework.common.Platform;
import com.yml.framework.prerequisite.PlatformDriverManager;
import org.openqa.selenium.WebDriver;

public class PlatformDriverProvider implements Provider<WebDriver> {

    @Inject
    Platform platform;

    @Inject
    PlatformDriverManager driverHelper;

    @Inject
    private ExtentReports extent;

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
        return platformSpecificDriver;
    }


}
