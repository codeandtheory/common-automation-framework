package com.yml.framework.bdd.stepdef;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.service.ExtentService;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.yml.framework.common.Platform;
import com.yml.framework.prerequisite.PlatformDriverManager;
import com.yml.framework.reporting.ExtentManager;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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

    @Inject
    @Named("uiEnv")
    public String uiEnv;

    @Inject
    @Named("apiEnv")
    public String apiEnv;


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
        ExtentService.getInstance().setSystemInfo("FE/UI Env", uiEnv);
        ExtentService.getInstance().setSystemInfo("BE/API Env", apiEnv);

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
                    String screenshotPath = commonUtil.getScreenShot(driver, scenario.getName());
                    currentTestCase.fail("SCENARIO FAILED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    break;
                case 3:
                default:
                    String screenshotSkip = commonUtil.getScreenShot(driver, scenario.getName());
                    currentTestCase.skip("SCENARIO SKIPPED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(screenshotSkip).build());
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
