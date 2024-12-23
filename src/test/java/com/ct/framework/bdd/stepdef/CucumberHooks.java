package com.ct.framework.bdd.stepdef;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.service.ExtentService;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.ct.framework.common.Platform;
import com.ct.framework.prerequisite.PlatformDriverManager;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;

public class CucumberHooks extends CommonSteps {


    static WebDriver webDriver;
    static PlatformDriverManager serverManager;

    static Platform platform;
    @Inject
    @Named("url")
    public String url;


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

    }

//    @BeforeStep
//    public void beforeEachTestStep(Scenario scenario) {
//        commonSteps.currentTestCase = currentTestCase;
//        logger.info("Before BDD Step");
//        //commonSteps.setScreens(currentTestCase);
//
//    }


    public void getScenarioStatus(Scenario scenario) {
        ExtentTest currentTestCase = ExtentCucumberAdapter.getCurrentStep();
        int status = scenario.isFailed() ? 2 : 1;
        try {
            switch (status) {
                case 1:
                    currentTestCase.pass("SCENARIO PASSED");
                    break;
                case 2:
                    String base64StringFail= commonUtil.getImageAsBase64(driver,scenario.getName().substring(0,2));
                    currentTestCase.log(Status.FAIL, "TEST FAILED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromBase64String(base64StringFail).build());
                    break;
                case 3:
                default:
                    String base64StringSkip= commonUtil.getImageAsBase64(driver,scenario.getName().substring(0,2));
                    currentTestCase.log(Status.FAIL, "TEST SKIPPED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromBase64String(base64StringSkip).build());
                    currentTestCase.skip("SCENARIO SKIPPED ");
                    break;

            }
        } catch (Exception e) {
            currentTestCase.fail(e);
        }
    }


    @After
    public void cleanUp(Scenario scenario) {
        super.setScreens();
        getScenarioStatus(scenario);
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
    public static void stopHook() {

        try {
            webDriver.quit();
            ExtentService.getInstance().flush();
            if (platform.isIOS() || platform.isAndroid()) {
                CucumberHooks.serverManager.stopAppiumServer();

            }
        } catch (Exception e) {
            ExtentCucumberAdapter.getCurrentStep().fail(e);
        }

    }
}
