package com.yml.loreal.screens;

import com.google.inject.Inject;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.logging.Logger;

public class WelcomeScreen extends BaseScreen {

    @Inject
    public WelcomeScreen(WebDriver driver, Logger logger) {
       super(driver, logger);
    }


    @FindBy(xpath = "//a[@class='logo']")
    @AndroidFindBy(id = "com.saloncentric.app:id/iv_logo")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell[contains(@name,'tutorialCell')]//child::XCUIElementTypeImage")
    public WebElement scLogo;

    @AndroidFindBy(id = "com.saloncentric.app:id/iv_tutorial")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeCell[contains(@name,'tutorialCell')]//child::XCUIElementTypeImage")
    public WebElement splashScreenImage;

    @FindBy(xpath = "//a[contains(text(),'Log In')]")
    @AndroidFindBy(id = "com.saloncentric.app:id/btn_login")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Log In']")
    public WebElement loginScreenLink;

    @AndroidFindBy(id = "com.saloncentric.app:id/layout_pager")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Log In']")
    public WebElement layoutPager;

    @AndroidFindBy(id = "com.saloncentric.app:id/tv_title")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeStaticText[`name IN { 'Discover', 'Learn', 'Be Rewarded','Shop' }`]")
    public WebElement pageTitleText;

    @AndroidFindBy(id = "com.saloncentric.app:id/tv_description")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Log In']")
    public WebElement pageDescText;

    @AndroidFindBy(id = "com.saloncentric.app:id/tv_description")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Log In']")
    public WebElement pageIndicatorView;


    @AndroidFindBy(id = "com.saloncentric.app:id/tv_register_discription")
    @iOSXCUITFindBy(id = "registerNowButton")
    public WebElement registerNowButton;
}
