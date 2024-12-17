package com.ct.framework.bdd.stepdef;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.ct.framework.aut.pojo.User;
import com.ct.framework.aut.screens.BaseScreen;
import com.ct.framework.aut.screens.WelcomeScreen;
import com.ct.framework.aut.screens.login.LoginScreen;
import com.ct.framework.common.CommonUtil;
import com.ct.framework.common.Platform;
import com.ct.framework.common.RequestUtil;
import com.ct.framework.contracts.adapters.MobileDriverActionAdapter;
import com.ct.framework.prerequisite.PlatformDriverManager;
import com.google.inject.Inject;
import org.openqa.selenium.WebDriver;

import javax.inject.Named;
import java.util.logging.Logger;


public class CommonSteps {


    @Inject
    @Named("apiUrl")
    public String apiUrl;

    @Inject
    public RequestUtil requestUtil;

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
    public ExtentReports extent;

    @Inject
    public PlatformDriverManager driverHelper;

    @Inject
    public BaseScreen baseScreen;

    @Inject
    public MobileDriverActionAdapter mobileDriverAction;

    @Inject
    public WelcomeScreen welcomeScreen;

    @Inject
    public LoginScreen loginScreen;


    public ExtentTest currentTestCase;

    public CommonSteps() {

    }


    public void setScreens() {
        ExtentTest currentStep = ExtentCucumberAdapter.getCurrentStep();
        mobileDriverAction.setTestCase(currentStep);
        //mobileDriverAction.reLaunchAppWithClearData();

        baseScreen.setCurrentTestCase(currentStep);
        mobileDriverAction.setTestCase(currentStep);

        commonUtil.setCurrentTestInstance(currentStep);
        baseScreen.setCurrentTestCase(currentStep);


    }


}
