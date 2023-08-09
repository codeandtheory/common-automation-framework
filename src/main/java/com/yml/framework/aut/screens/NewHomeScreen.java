package com.yml.framework.aut.screens;

import com.google.inject.Inject;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public class NewHomeScreen extends BaseScreen {

    @Inject
    public NewHomeScreen(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    @AndroidFindBy(id = "com.saloncentric.app:id/close")
    @iOSXCUITFindBy(id = "closeVideoTutorial")
    public WebElement closeNewHomeScreenBtn;


    @AndroidFindBy(id = "com.saloncentric.app:id/page_indicator")
    @iOSXCUITFindBy(className = "XCUIElementTypePageIndicator")
    public WebElement pageIndicatorSection;

    @AndroidFindBy(id = "com.saloncentric.app:id/tv_title")
    @iOSXCUITFindBy(id = "emailTextField")
    public WebElement screenTitleText;

    @AndroidFindBy(id = "com.saloncentric.app:id/tv_description")
    @iOSXCUITFindBy(id = "emailTextField")
    public WebElement screenDescText;

    @AndroidFindBy(id = "com.saloncentric.app:id/exo_subtitles")
    @iOSXCUITFindBy(id = "emailTextField")
    public WebElement screenContentImage;



}
