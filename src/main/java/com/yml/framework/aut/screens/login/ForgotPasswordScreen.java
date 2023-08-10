package com.yml.framework.aut.screens.login;

import com.google.inject.Inject;
import com.yml.framework.aut.screens.BaseScreen;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public class ForgotPasswordScreen extends BaseScreen {


    @Inject
    public ForgotPasswordScreen(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    @AndroidFindBy(id = "com.saloncentric.app:id/reset_pwd_email")
    @iOSXCUITFindBy(iOSNsPredicate = "value == 'Email Address'")
    public WebElement forgotPwdEmailTextBox;

    @AndroidFindBy(id = "com.saloncentric.app:id/btn_submit_reset_pwd")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeButton[`label == 'Submit'`]")
    public WebElement forgotPwdSubmitButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Please enter your email')]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@name,'Please enter your email address')]")
    public WebElement forgotPwdMsgText;

    @AndroidFindBy(id = "com.saloncentric.app:id/ivNav")
    @iOSXCUITFindBy(id = "arrowL")
    public WebElement forgotPwdBackImage;

    @AndroidFindBy(id = "com.saloncentric.app:id/tvToolbarTitle")
    @iOSXCUITFindBy(iOSNsPredicate = "label == 'Forgot Password'")
    public WebElement forgotPwdScreenTitleText;

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement invalidEmailErrorToastMsg;
}
