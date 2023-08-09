package com.yml.framework.bdd.stepdef;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.service.ExtentService;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.yml.framework.common.Platform;
import com.yml.framework.prerequisite.PlatformDriverManager;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;

public class CucumberHooks extends CommonSteps {


    static WebDriver webDriver;
    static PlatformDriverManager serverManager;

    static Platform platform;
    @Inject
    @Named("url")
    public String url;


    @Inject
    @Named("browserName")
    public String browserName;

    @BeforeAll
    public static void setInfo() {


    }

    @Before(order = 0)
    public void setScreens(Scenario scenario) throws Exception {

        CucumberHooks.platform = super.platform;
        CucumberHooks.webDriver = super.driver;
        CucumberHooks.serverManager = super.driverHelper;
        super.setScreens();
        if (platform.isWeb()) {
            driver.navigate().to(url);
            driver.manage().window().maximize();
        } else if (platform.isIOS() || platform.isAndroid())
            mobileDriverAction.reLaunchAppWithClearData();
        else {

        }
        ExtentService.getInstance().setSystemInfo("Platform Name", platform.getPlatformName());
        ExtentService.getInstance().setSystemInfo("Operating System", System.getProperty("os.name"));
        ExtentService.getInstance().setSystemInfo("Browser Name", browserName);
    }

//    @BeforeStep
//    public void beforeEachTestStep(Scenario scenario) {
//        commonSteps.currentTestCase = currentTestCase;
//        logger.info("Before BDD Step");
//        //commonSteps.setScreens(currentTestCase);
//
//    }

    @After(order = 0)
    public void afterEachStep(Scenario scenario) {
        ExtentTest currentTestCase = ExtentCucumberAdapter.getCurrentStep();
        int status = scenario.isFailed() ? 2 : 1;
        try {
            switch (status) {
                case 1:
                    currentTestCase.pass("TEST PASSED");
                    break;
                case 2:
                    String screenshotPath = commonUtil.getScreenShot(driver, scenario.getName());
                    currentTestCase.fail("TEST FAILED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    break;
                case 3:
                default:
                    String screenshotSkip = commonUtil.getScreenShot(driver, scenario.getName());
                    currentTestCase.skip("TEST SKIPPED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(screenshotSkip).build());
                    currentTestCase.skip("TEST SKIPPED ");
                    break;

            }
        } catch (Exception e) {
            currentTestCase.fail(e);
        }
    }


    @After(order = 1)
    public void cleanUp() {
        super.setScreens();
        try {
            if (platform.isWeb()) {
                mobileDriverAction.clearBrowserCacheAndCookies();
            } else if (platform.isIOS() || platform.isAndroid())
                mobileDriverAction.killApp();
            else {

            }


        } catch (Exception e) {
            ExtentCucumberAdapter.addTestStepLog(e.getMessage());
        }

    }

    @AfterAll
    public static void cleanUpA() {

        try {
            webDriver.quit();
            if (platform.isIOS() || platform.isAndroid()) {
                CucumberHooks.serverManager.stopAppiumServer();

            }
        } catch (Exception e) {
            ExtentCucumberAdapter.getCurrentStep().fail(e);
        }

    }
}
