package com.yml.framework.aut.screens;

import com.google.inject.Inject;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public class ShareLocationScreen extends BaseScreen {


    @Inject
    public ShareLocationScreen(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    @AndroidFindBy(id = "com.saloncentric.app:id/layout_location_permission")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Share your location']//parent:: XCUIElementTypeOther")
    public WebElement locationPermissionLayout;


    @AndroidFindBy(id = "com.saloncentric.app:id/got_it_btn")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Allow Location Access']")
    public WebElement allowLocationBtn;


    @AndroidFindBy(id = "com.saloncentric.app:id/btn_not_now")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'notNowButton'")
    public WebElement dontAllowLocationBtn;

    @AndroidFindBy(id = "com.android.permissioncontroller:id/grant_dialog")
    @iOSXCUITFindBy(id = "emailTextField")
    public WebElement locationPermissionPopUp;

    @AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_foreground_only_button")
    @iOSXCUITFindBy(id = "Allow While Using App")
    public WebElement allowLocationWhileUsingAppBtn;


    @AndroidFindBy(id = "com.android.permissioncontroller:id/permission_allow_one_time_button")
    @iOSXCUITFindBy(id = "Allow Once")
    public WebElement allowLocationOnlyThisTimeBtn;


    @AndroidFindBy(id = "com.android.permissioncontroller:id/permission_deny_button")
    @iOSXCUITFindBy(id = "Don't Allow")
    public WebElement denyLocationPermissionBtn;





}
