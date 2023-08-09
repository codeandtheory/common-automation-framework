package com.yml.framework.aut.screens;

import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Inject;
import com.yml.framework.contracts.adapters.MobileDriverActionAdapter;
import com.yml.framework.common.Platform;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.logging.Logger;

public class BaseScreen {


    public Logger logger;
    public WebDriver driver;
    public ExtentTest currentTestCase;

    @Inject
    public Platform platform;

    @Inject
    public MobileDriverActionAdapter mobileDriverActions;

    @Inject
    public BaseScreen(WebDriver driver,Logger logger){
        this.logger=logger;
        this.driver=driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public ExtentTest getCurrentTestCase() {
        return currentTestCase;
    }

    public void setCurrentTestCase(ExtentTest currentTestCase) {
        this.currentTestCase = currentTestCase;
    }

    @AndroidFindBy(id = "com.saloncentric.app:id/ivNav")
    @iOSXCUITFindBy(id = "product")
    public WebElement navigationBack;

    @AndroidFindBy(id = "com.saloncentric.app:id/loading_animation")
    @iOSXCUITFindBy(id = "product")
    public WebElement loadingAnimation;


    @AndroidFindBy(id = "com.saloncentric.app:id/tvToolbarTitle")
    @iOSXCUITFindBy(id = "searchText")
    public WebElement screenTitle;




}
