package com.yml.ios;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.inject.Inject;
import com.yml.framework.aut.pojo.User;
import com.yml.framework.aut.screens.BaseScreen;
import com.yml.framework.common.CommonUtil;
import com.yml.framework.common.Platform;
import com.yml.framework.common.RequestUtil;
import com.yml.framework.contracts.adapters.MobileDriverActionAdapter;
import com.yml.framework.prerequisite.ConfigurationModule;
import com.yml.framework.prerequisite.PlatformDriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import javax.inject.Named;
import java.lang.reflect.Method;
import java.util.logging.Logger;

@Guice(modules = {ConfigurationModule.class})
public class TS_YML_UI_BaseTest {

    @Inject
    @Named("apiUrl")
    public String apiUrl;

    @Inject
    @com.google.inject.name.Named("executionMode")
    public String executionMode;

    @Inject
    public WebDriver driver;

    @Inject
    public Logger logger;

    @Inject
    public Platform platform;

    @Inject
    public User user;

    @Inject
    public CommonUtil commonUtil;

    @Inject
    public RequestUtil requestUtil;



    @Inject
    public ExtentReports extent;

    @Inject
    public PlatformDriverManager driverHelper;

    @Inject
    BaseScreen baseScreen;

    @Inject
    public MobileDriverActionAdapter mobileDriverAction;


    public ExtentTest currentTestCase;


    @BeforeSuite()
    public void setUp() throws Exception {

        //add file handler to the logger.
        logger.info("Before Suite..");
        currentTestCase = extent.createTest("Before Suite");
        currentTestCase.info("Before Suite finished");
    }

    @BeforeTest
    public void initializeTest() {

        logger.info("Before Test");
    }


    @BeforeClass
    public void setScreens() {
       currentTestCase = extent.createTest("Before Class");
//        smcCoreUiCommons.setLoginScreen(loginScreen);
//        smcCoreUiCommons.setWelcomeScreen(welcomeScreen);
//        smcCoreUiCommons.setHomeScreen(homeScreen);
//        smcCoreUiCommons.setShareLocationScreen(shareLocationScreen);
//        smcCoreUiCommons.setNewHomeScreen(newHomeScreen);
//        smcCoreUiCommons.setUserProfileScreen(userProfileScreen);
//        smcCoreUiCommons.setEnableFaceIdScreen(enableFaceIdScreen);
        mobileDriverAction.setTestCase(currentTestCase);
        if (platform.isIOS() || platform.isAndroid())
            mobileDriverAction.reLaunchAppWithClearData();
//        smcCoreUiCommons.setCurrentTestCase(currentTestCase);
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeEachTestCase(Method method, XmlTest xmlTest, ITestResult result) {


        String testCaseName = result.getMethod().getDescription();
        if (testCaseName == null || testCaseName.equalsIgnoreCase(""))
            testCaseName = method.getName();
        currentTestCase = extent.createTest(testCaseName, method.getName());
        currentTestCase.assignCategory(method.getDeclaringClass().getSimpleName());
        baseScreen.setCurrentTestCase(currentTestCase);
        mobileDriverAction.setTestCase(currentTestCase);
        // smcCoreUiCommons.setCurrentTestCase(currentTestCase);
        commonUtil.setCurrentTestInstance(currentTestCase);
        baseScreen.setCurrentTestCase(currentTestCase);
    }

    @AfterMethod(alwaysRun = true)
    public void afterEachTestCase(ITestResult result, XmlTest test) {
        int status = result.getStatus();
        try {
            switch (status) {
                case 1:
                    currentTestCase.pass("TEST PASSED");
                    break;
                case 2:
                    //String screenshotPath = commonUtil.getScreenShot(driver, result.getName());
                    String base64StringFail= commonUtil.getImageAsBase64(driver,result.getName());
                    currentTestCase.log(Status.FAIL, "TEST FAILED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromBase64String(base64StringFail).build());
                    currentTestCase.fail(CommonUtil.getStringForReport(result.getThrowable().getMessage()));
                    break;
                case 3:
                default:
                    //String screenshotSkip = commonUtil.getScreenShot(driver, result.getName());
                    String base64StringSkip= commonUtil.getImageAsBase64(driver,result.getName());
                    currentTestCase.log(Status.FAIL, "TEST FAILED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromBase64String(base64StringSkip).build());
                    currentTestCase.skip("TEST SKIPPED ");
                    currentTestCase.skip(result.getThrowable());
                    break;

            }
        } catch (Exception e) {
            currentTestCase.fail(e);
        }
        currentTestCase.info("<pre>ENDING TESTCASE...." + result.getName() + " </pre> ");

    }


    @AfterSuite(alwaysRun = true)
    public void cleanUp() throws Exception {

        currentTestCase = extent.createTest("After Suite-Clean Up");
        mobileDriverAction.setTestCase(currentTestCase);
        if (platform.isIOS() || platform.isAndroid())
            mobileDriverAction.killApp();
        extent.flush();
        driver.quit();
        if ((platform.isIOS() || platform.isAndroid())&&!executionMode.equalsIgnoreCase("cloud"))
            driverHelper.stopAppiumServer();
    }

}
