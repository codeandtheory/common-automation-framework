package com.yml.loreal.screens.login;

import com.google.inject.Inject;
import com.yml.loreal.screens.BaseScreen;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public class SignUpWebViewScreen extends BaseScreen {

    @Inject
    public SignUpWebViewScreen(WebDriver driver, Logger logger) {
        super(driver, logger);
    }



    @AndroidFindBy(xpath = "//android.widget.Button[contains(@text,'YES')]")
    @iOSXCUITFindBy(id = "product")
    public WebElement newCustomerYesBtn;

    @AndroidFindBy(id = "com.android.chrome:id/url_bar")
    @iOSXCUITFindBy(id = "product")
    public WebElement signUpUrl;

    @AndroidFindBy(id = "RegistrationForm")
    @iOSXCUITFindBy(id = "product")
    public WebElement registrationForm;

    @AndroidFindBy(id = "dwfrm_registration_contact_firstname")
    @iOSXCUITFindBy(id = "product")
    public WebElement firstNameTextBox;

    @AndroidFindBy(id = "dwfrm_registration_contact_lastname")
    @iOSXCUITFindBy(id = "product")
    public WebElement lastNameTextBox;

    @AndroidFindBy(id = "dwfrm_registration_contact_email")
    @iOSXCUITFindBy(id = "product")
    public WebElement emailTextBox;

    @AndroidFindBy(id = "dwfrm_registration_contact_phone")
    @iOSXCUITFindBy(id = "product")
    public WebElement phoneTextBox;

    @AndroidFindBy(xpath = "//*[@resource-id='RegistrationForm']//child::android.widget.Button")
    @iOSXCUITFindBy(id = "product")
    public WebElement nextBtn;





}
