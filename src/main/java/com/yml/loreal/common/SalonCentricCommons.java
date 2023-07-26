package com.yml.loreal.common;

import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Inject;
import com.yml.loreal.contracts.adapters.MobileDriverActionAdapter;
import com.yml.loreal.pojo.Platform;
import com.yml.loreal.pojo.User;
import com.yml.loreal.screens.*;
import com.yml.loreal.screens.login.LoginScreen;
import com.yml.loreal.screens.profile.UserProfileScreen;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

import static com.yml.loreal.common.AppUiObjectNames.*;
import static com.yml.loreal.common.ApplicationConstants.*;

public class SalonCentricCommons {


    public static final String PERMISSION_ALLOW_WHILE_USING="while-using";
    public static final String PERMISSION_ALLOW_ONLY_THIS_TIME="only-this-time";
    public static final String PERMISSION_DENY="deny";
    public static final String PERMISSION_ALLOW="allow";


    @Inject
    WebDriver driver;

    @Inject
    MobileDriverActionAdapter mobileDriverActions;

    @Inject
    Platform platform;

    @Inject
    Logger logger;

    private WelcomeScreen welcomeScreen;
    private LoginScreen loginScreen;
    private ExtentTest currentTestCase;
    private ShareLocationScreen shareLocationScreen;
    private NewHomeScreen newHomeScreen;
            private HomeScreen homeScreen;
    private UserProfileScreen userProfileScreen;
    private EnableFaceIdScreen enableFaceIdScreen;






    public void setEnableFaceIdScreen(EnableFaceIdScreen enableFaceIdScreen) {
        this.enableFaceIdScreen = enableFaceIdScreen;
    }

    public void setShareLocationScreen(ShareLocationScreen shareLocationScreen) {
        this.shareLocationScreen = shareLocationScreen;
    }

    public void setNewHomeScreen(NewHomeScreen newHomeScreen) {
        this.newHomeScreen = newHomeScreen;
    }

    public void setHomeScreen(HomeScreen homeScreen) {
        this.homeScreen = homeScreen;
    }

    public void setUserProfileScreen(UserProfileScreen userProfileScreen) {
        this.userProfileScreen = userProfileScreen;
    }

    public void setWelcomeScreen(WelcomeScreen welcomeScreen) {
        this.welcomeScreen = welcomeScreen;
    }

    public void setLoginScreen(LoginScreen loginScreen) {
        this.loginScreen = loginScreen;
    }

     public SalonCentricCommons() {

        //CommonUtil.addFileHandler(logger);
    }

    public ExtentTest getCurrentTestCase() {
        return currentTestCase;
    }

    public void setCurrentTestCase(ExtentTest currentTestCase) {
        mobileDriverActions.setTestCase(currentTestCase);
        this.currentTestCase = currentTestCase;
    }

    public boolean loginToSalonCentric(User user) throws Exception{

        boolean isLoginSuccessful=false;

        if ((user.getUsername() == null || user.getPassword() == null) ) {
            this.currentTestCase.error("Either username or password is null. please check.");
            throw new Exception("Either username or password is null. please check.");
        }


        //condition added if user is already logged in . returns true.
        mobileDriverActions.waitForElementVisible(homeScreen.cartIcon,const_timeout_small,null);
        if (isUserLoggedIn()){
            logger.info("User already logged in .Returning true");
            this.currentTestCase.info("Already logged in ");
            return true;
        }
        mobileDriverActions.waitForElementVisible(welcomeScreen.loginScreenLink,const_timeout_medium, LOGIN_LINK_WELCOMESCREEN);

        if(mobileDriverActions.isElementFound(welcomeScreen.loginScreenLink,LOGIN_LINK_WELCOMESCREEN))
               mobileDriverActions.click(welcomeScreen.loginScreenLink,LOGIN_LINK_WELCOMESCREEN);

        this.currentTestCase.info("Trying to Login with "+user.getUsername()+ " and  "+user.getPassword());
        loginScreen.loginAction(user.getUsername(),user.getPassword());
        mobileDriverActions.waitForElementVisible(shareLocationScreen.allowLocationBtn,const_timeout_large,null);
        if (mobileDriverActions.isElementFound(shareLocationScreen.allowLocationBtn,null))
             return true;
        else {
            mobileDriverActions.waitForElementVisible(homeScreen.cartIcon,const_timeout_small,null);
            if (mobileDriverActions.isElementFound(homeScreen.cartIcon,null))
                return true;
            else
                return false;
        }
    }

    public boolean isLocationScreenDisplayed(){
        mobileDriverActions.waitForElementVisible(shareLocationScreen.allowLocationBtn,const_timeout_small,null);
        return mobileDriverActions.isElementFound(shareLocationScreen.allowLocationBtn,null);
    }


    public void goToHomeScreenAfterLogin(String locationPermissionAllowOrDeny){


        //if user is already on home screen. Returning from the method..
        if (isUserLoggedIn()){
            logger.info("User is already on Home screen .So Returning.");
            this.currentTestCase.info("User logged in. Already on Homescreen");
            return;
        }

       //condition for Iphone only
      if (platform.isIOS()) {
          mobileDriverActions.waitForElementVisible(enableFaceIdScreen.enableFaceIdBtn, const_timeout_medium, null);
          if (mobileDriverActions.isElementFound(enableFaceIdScreen.enableFaceIdBtn, null))
              mobileDriverActions.click(enableFaceIdScreen.notNowBtn, null);
      }

         if (isLocationScreenDisplayed())
                allowDenyLocation(locationPermissionAllowOrDeny);

        mobileDriverActions.waitForElementVisible(newHomeScreen.closeNewHomeScreenBtn,const_timeout_large,null);
        mobileDriverActions.click(newHomeScreen.closeNewHomeScreenBtn,null);
        mobileDriverActions.waitForElementVisible(homeScreen.cartIcon,const_timeout_small,null);
    }

    public void allowDenyLocation(String locationPermissionAllowOrDeny){
        mobileDriverActions.waitForElementVisible(shareLocationScreen.allowLocationBtn,const_timeout_medium,null);

        if (locationPermissionAllowOrDeny == null)
            locationPermissionAllowOrDeny="default";

        switch (locationPermissionAllowOrDeny){
            case PERMISSION_ALLOW:
                mobileDriverActions.click(shareLocationScreen.allowLocationBtn,null);
                mobileDriverActions.click(shareLocationScreen.allowLocationWhileUsingAppBtn,null);
                break;
            case PERMISSION_DENY:
                mobileDriverActions.click(shareLocationScreen.dontAllowLocationBtn,null);
                break;
            default:
                mobileDriverActions.click(shareLocationScreen.allowLocationBtn,null);
                this.currentTestCase.info("No Allow and Deny ");
                break;
        }
    }

    public boolean logoutFromApp() throws Exception{

        this.currentTestCase.info("Trying to Logout..");
        mobileDriverActions.click(homeScreen.profileTab,null);
        mobileDriverActions.waitForElementVisible(userProfileScreen.businessToolsMenuItem,const_timeout_small,null);
        while (!mobileDriverActions.isElementFound(userProfileScreen.logoutMenuItem,null)){
               mobileDriverActions.swipe(Direction.UP,userProfileScreen.settingsMenuItem);
        }
        mobileDriverActions.click(userProfileScreen.logoutMenuItem,null);

        //condition to check if alert
        if (mobileDriverActions.isElementFound(userProfileScreen.logoutAlert,null))
            mobileDriverActions.click(userProfileScreen.logoutAlertYes,null);

        mobileDriverActions.waitForElementVisible(welcomeScreen.loginScreenLink,const_timeout_medium,LOGIN_LINK_WELCOMESCREEN);
       if (mobileDriverActions.isElementFound(welcomeScreen.loginScreenLink,null)) {
           this.currentTestCase.pass("Logout successful..");
           return true;
       }
       else {
           this.currentTestCase.fail("Logout failed..");
           return false;
       }

    }


    public boolean isSplashScreenPresent() throws Exception{

        boolean isSplashScreenPresent=false;
        isSplashScreenPresent=mobileDriverActions.isElementFound(welcomeScreen.splashScreenImage,SPLASHSCREEN_IMAGE_WELCOMESCREEN);
        return isSplashScreenPresent;
    }


    public boolean isAllSplashScreenPresent() throws Exception{

        boolean isSplashScreenPresent=false;
        boolean result=true;
        int i=0;
        mobileDriverActions.waitForElementVisible(welcomeScreen.pageTitleText,const_timeout_medium,TITLE_TEXT_WELCOMESCREEN);
        while (i<4){
            i=i+1;
            isSplashScreenPresent=mobileDriverActions.isElementFound(welcomeScreen.splashScreenImage,SPLASHSCREEN_IMAGE_WELCOMESCREEN);
            mobileDriverActions.swipe(Direction.LEFT,welcomeScreen.pageTitleText);
            result=result && isSplashScreenPresent;
            mobileDriverActions.waitForElementVisible(welcomeScreen.splashScreenImage,const_timeout_small,SPLASHSCREEN_IMAGE_WELCOMESCREEN);
        }
        return result;
    }






    public void backToHomeScreen(){
        boolean isHomeScreen=
                mobileDriverActions.isElementFound(homeScreen.homeTab,null)||
                        mobileDriverActions.isElementFound(homeScreen.cartIcon,null);
        while (!isHomeScreen){
            mobileDriverActions.goBack();
            mobileDriverActions.waitForElementVisible(homeScreen.homeTab,const_timeout_extra_small,null);
            isHomeScreen= mobileDriverActions.isElementFound(homeScreen.homeTab,null)||
                    mobileDriverActions.isElementFound(homeScreen.cartIcon,null);
        }
    }


    public boolean isUserLoggedIn(){
        mobileDriverActions.waitForElementVisible(homeScreen.cartIcon,const_timeout_small,null);
        if (mobileDriverActions.isElementFound(homeScreen.cartIcon,null)){
            logger.info("User logged in .Returning true");
            return true;
        }
        else {
            logger.info("User Not logged in .Returning false");
            return false;
        }
    }


}
