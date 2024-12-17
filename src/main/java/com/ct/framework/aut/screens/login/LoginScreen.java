package com.ct.framework.aut.screens.login;

import com.google.inject.Inject;
import com.ct.framework.aut.screens.BaseScreen;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;


public class LoginScreen extends BaseScreen {


    @Inject
    public LoginScreen(WebDriver driver,Logger logger) {
       super(driver,logger);
    }

    @AndroidFindBy(id = "com.saloncentric.app:id/et_login_email")
    @iOSXCUITFindBy(id = "logo")
    public WebElement logo;


    @FindBy(xpath = "//input[contains(@id,'username')]")
    @AndroidFindBy(id = "com.saloncentric.app:id/et_login_email")
    @iOSXCUITFindBy(id = "emailTextField")
    public WebElement usernameText;

    @FindBy(xpath = "//input[contains(@id,'password')]")
    @AndroidFindBy(id = "com.saloncentric.app:id/et_login_password")
    @iOSXCUITFindBy(id = "passwordTextField")
    public WebElement passwordText;

    @FindBy(xpath = "//a[contains(text(),'Log In')]")
    @AndroidFindBy(id = "com.saloncentric.app:id/btn_login")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Log In']")
    public WebElement loginButton;


    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[contains(@value,'We’re sorry, there is an error')]")
    public WebElement invalidCredErrorToastMsg;


    @AndroidFindBy(id = "com.saloncentric.app:id/btn_login")
    @iOSXCUITFindBy(id = "OK")
    public WebElement loginErrorPopUpOk;


    @AndroidFindBy(id = "com.saloncentric.app:id/tv_forget_pwd")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement forgotPasswordLink;

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement invalidEmailErrorToastMsg;


    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement noInternetErrorToastMsg;


    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    @iOSXCUITFindBy(id = "Forgot Password?")
    @FindBy(xpath = "//a[text()='Sign in']")
    public WebElement signInLink;

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    @iOSXCUITFindBy(id = "Forgot Password?")
    @FindBy(xpath = "(//ymlwebcl-textfield[@textfieldid='email'])[1]")
    public WebElement emailShadowRoot;

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    @iOSXCUITFindBy(id = "Forgot Password?")
    @FindBy(xpath = "//ymlwebcl-textfield[@textfieldid='password']")
    public WebElement passwordShadowRoot;

    @AndroidFindBy(xpath = "//android.widget.Toast[1]")
    @iOSXCUITFindBy(id = "Forgot Password?")
    @FindBy(css = "#email")
    public WebElement emailtextBox;

    @AndroidFindBy(id = "com.saloncentric.app:id/ivNav")
    @iOSXCUITFindBy(id = "product")
    @FindBy(xpath = "(//ymlwebcl-button[@partname='button-filled button-fullWidth'])[1]")
    public WebElement signinButtonShadowRoot;





    public WebElement getInvalidEmailErrorToastMsg() {
        return invalidEmailErrorToastMsg;
    }




    public boolean loginAction(String username, String password) throws Exception {

        logger.info("Starting login Action");
        TimeUnit.SECONDS.sleep(2);
        usernameText.clear();
        logger.info("Entering Username ");
        usernameText.sendKeys(username);
        TimeUnit.SECONDS.sleep(2);
        logger.info("Entering Password ");
        passwordText.sendKeys(password);
        TimeUnit.SECONDS.sleep(2);
        logger.info("Clicking on Login Button");
        loginButton.click();
        return true;
    }

    public String getInvalidCredErrorMsgString(){
        return invalidCredErrorToastMsg.getText();
    }
}
