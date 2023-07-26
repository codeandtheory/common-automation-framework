package com.yml.loreal.bdd.stepdef;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.inject.Inject;
import com.yml.loreal.common.CommonUtil;
import com.yml.loreal.common.SalonCentricCommons;
import com.yml.loreal.contracts.adapters.MobileDriverActionAdapter;
import com.yml.loreal.pojo.Platform;
import com.yml.loreal.pojo.User;
import com.yml.loreal.prerequisite.ConfigurationModule;
import com.yml.loreal.prerequisite.AppiumServerManager;
import com.yml.loreal.screens.*;
import com.yml.loreal.screens.login.ForgotPasswordScreen;
import com.yml.loreal.screens.login.LoginScreen;
import com.yml.loreal.screens.login.SignUpWebViewScreen;
import com.yml.loreal.screens.profile.SettingsScreen;
import com.yml.loreal.screens.profile.UserProfileScreen;
import io.cucumber.core.gherkin.Step;
import io.cucumber.java.*;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.Guice;
import org.testng.xml.XmlTest;

import java.util.logging.Logger;

@Guice(modules = {ConfigurationModule.class})
public class CommonSteps {

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
    public SalonCentricCommons salonCentricCommons;

    @Inject
    public ExtentReports extent;

    @Inject
    public AppiumServerManager driverHelper;

    @Inject
    BaseScreen baseScreen;

    @Inject
    public MobileDriverActionAdapter mobileDriverAction;

    @Inject
    public WelcomeScreen welcomeScreen;

    @Inject
    public LoginScreen loginScreen;

    @Inject
    public ForgotPasswordScreen forgotPasswordScreen;

    @Inject
    public ShareLocationScreen shareLocationScreen;

    @Inject
    public NewHomeScreen newHomeScreen;

    @Inject
    public HomeScreen homeScreen;

    @Inject
    public SettingsScreen settingsScreen;

    @Inject
    public UserProfileScreen userProfileScreen;

    @Inject
    public EnableFaceIdScreen enableFaceIdScreen;


    @Inject
    public SignUpWebViewScreen signUpWebViewScreen;


    public ExtentTest currentTestCase;


    @Before
    public void setScreens(Scenario scenario) {
        currentTestCase = extent.createTest(scenario.getName());
        salonCentricCommons.setLoginScreen(loginScreen);
        salonCentricCommons.setWelcomeScreen(welcomeScreen);
        salonCentricCommons.setHomeScreen(homeScreen);
        salonCentricCommons.setShareLocationScreen(shareLocationScreen);
        salonCentricCommons.setNewHomeScreen(newHomeScreen);
        salonCentricCommons.setUserProfileScreen(userProfileScreen);
        salonCentricCommons.setEnableFaceIdScreen(enableFaceIdScreen);
        mobileDriverAction.setTestCase(currentTestCase);
        //mobileDriverAction.reLaunchAppWithClearData();
        salonCentricCommons.setCurrentTestCase(currentTestCase);
    }

    @BeforeStep
    public void beforeEachTestStep(Scenario scenario, Step step) {


        String teststepName=step.getText();
        currentTestCase = extent.createTest(teststepName, teststepName);
        currentTestCase.assignCategory(scenario.getName());
        baseScreen.setCurrentTestCase(currentTestCase);
        mobileDriverAction.setTestCase(currentTestCase);
        salonCentricCommons.setCurrentTestCase(currentTestCase);
        commonUtil.setCurrentTestInstance(currentTestCase);
        baseScreen.setCurrentTestCase(currentTestCase);
    }

    @AfterStep
    public void afterEachStep(ITestResult result, XmlTest test) {
        int status = result.getStatus();
        try {
            switch (status) {
                case 1:
                    currentTestCase.pass("TEST PASSED");
                    break;
                case 2:
                    String screenshotPath = commonUtil.getScreenShot(driver, result.getName());
                    currentTestCase.log(Status.FAIL, "TEST FAILED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    currentTestCase.error(CommonUtil.getStringForReport(result.getThrowable().getMessage()));
                    break;
                case 3:
                default:
                    String screenshotSkip = commonUtil.getScreenShot(driver, result.getName());
                    currentTestCase.log(Status.FAIL, "TEST FAILED.REFER SCREENSHOT", MediaEntityBuilder.createScreenCaptureFromPath(screenshotSkip).build());
                    currentTestCase.skip("TEST SKIPPED ");
                    currentTestCase.skip(result.getThrowable());
                    break;

            }
        } catch (Exception e) {
            currentTestCase.error(e);
        }
        currentTestCase.info("<pre>ENDING TESTCASE...." + result.getName() + " </pre> ");

    }


    @AfterAll
    public void cleanUp() throws Exception {

        currentTestCase = extent.createTest("After Suite-Clean Up");
        mobileDriverAction.setTestCase(currentTestCase);
        mobileDriverAction.killApp();
        extent.flush();
        driver.quit();
        driverHelper.stopAppiumServer();

    }

}
