package com.yml.loreal.screens.profile;

import com.google.inject.Inject;
import com.yml.loreal.screens.BaseScreen;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public class SettingsScreen extends BaseScreen {



    @Inject
    public SettingsScreen(WebDriver driver, Logger logger) {
        super(driver, logger);
    }


    @AndroidFindBy(id = "com.saloncentric.app:id/tvToolbarTitle")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement screenTitleText;

    @AndroidFindBy(id = "com.saloncentric.app:id/tvMyInfo")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement myInfoMenuItem;

    @AndroidFindBy(id = "com.saloncentric.app:id/tvFollowBrand")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement followBrandMenuItem;


    @AndroidFindBy(id = "com.saloncentric.app:id/tvResetPassword")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement resetPasswordMenuItem;


    @AndroidFindBy(id = "com.saloncentric.app:id/notification_layout")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement notificationMenuItem;


    @AndroidFindBy(id = "com.saloncentric.app:id/notification_switch")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement notificationSwitch;


    @AndroidFindBy(id = "com.saloncentric.app:id/pintrest_login_layout")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement pintrestMenuItem;


    @AndroidFindBy(id = "com.saloncentric.app:id/pintrest_login_switch")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement pintrestSwitch;



}
