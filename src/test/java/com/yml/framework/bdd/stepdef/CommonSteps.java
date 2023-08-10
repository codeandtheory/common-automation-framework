package com.yml.framework.bdd.stepdef;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.google.inject.Inject;
import com.yml.framework.aut.commons.SalonCentricCommons;
import com.yml.framework.aut.pojo.User;
import com.yml.framework.aut.screens.*;
import com.yml.framework.aut.screens.login.ForgotPasswordScreen;
import com.yml.framework.aut.screens.login.LoginScreen;
import com.yml.framework.aut.screens.login.SignUpWebViewScreen;
import com.yml.framework.aut.screens.profile.SettingsScreen;
import com.yml.framework.aut.screens.profile.UserProfileScreen;
import com.yml.framework.common.CommonUtil;
import com.yml.framework.common.Platform;
import com.yml.framework.common.RequestUtil;
import com.yml.framework.contracts.adapters.MobileDriverActionAdapter;
import com.yml.framework.prerequisite.PlatformDriverManager;
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
    public SalonCentricCommons salonCentricCommons;

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
    @Inject
    public HomeScreen homeScreen;

    @Inject
    public ForgotPasswordScreen forgotPasswordScreen;

    @Inject
    public ShareLocationScreen shareLocationScreen;

    @Inject
    public NewHomeScreen newHomeScreen;

    @Inject
    public SettingsScreen settingsScreen;

    @Inject
    public UserProfileScreen userProfileScreen;

    @Inject
    public EnableFaceIdScreen enableFaceIdScreen;

    @Inject
    public SignUpWebViewScreen signUpWebViewScreen;


    public ExtentTest currentTestCase;

    public CommonSteps() {

    }


    public void setScreens() {
        ExtentTest currentStep = ExtentCucumberAdapter.getCurrentStep();
        mobileDriverAction.setTestCase(currentStep);
        //mobileDriverAction.reLaunchAppWithClearData();
        salonCentricCommons.setCurrentTestCase(currentStep);
        baseScreen.setCurrentTestCase(currentStep);
        mobileDriverAction.setTestCase(currentStep);
        salonCentricCommons.setCurrentTestCase(currentStep);
        commonUtil.setCurrentTestInstance(currentStep);
        baseScreen.setCurrentTestCase(currentStep);
        salonCentricCommons.setLoginScreen(loginScreen);
        salonCentricCommons.setWelcomeScreen(welcomeScreen);
        salonCentricCommons.setHomeScreen(homeScreen);
        salonCentricCommons.setShareLocationScreen(shareLocationScreen);
        salonCentricCommons.setNewHomeScreen(newHomeScreen);
        salonCentricCommons.setUserProfileScreen(userProfileScreen);
        salonCentricCommons.setEnableFaceIdScreen(enableFaceIdScreen);
    }


}
