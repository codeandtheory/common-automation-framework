package com.yml.loreal.screens;

import com.google.inject.Inject;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public class EnableFaceIdScreen extends BaseScreen {

    @Inject
    public EnableFaceIdScreen(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    @AndroidFindBy(id = "faceid")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='enableTouchIDButton']")
    public WebElement enableFaceIdBtn;


    @AndroidFindBy(id = "faceidnownow")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='notNowButton']")
    public WebElement notNowBtn;

}
