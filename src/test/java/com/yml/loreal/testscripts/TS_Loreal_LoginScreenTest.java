package com.yml.loreal.testscripts;

import com.yml.loreal.common.CommonUtil;
import com.yml.loreal.common.SalonCentricCommons;
import com.yml.loreal.contracts.adapters.MobileDriverActionAdapter;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import static com.yml.loreal.common.AppUiObjectNames.*;
import static com.yml.loreal.common.ApplicationConstants.*;

public class TS_Loreal_LoginScreenTest extends TS_Loreal_BaseTest {



    @Test(enabled = false ,description = "test",dataProvider = "loginDataProvider")
    public void loginDemoTest(String username,String password) throws InterruptedException {
        commonUtil.logExecutionStart();
        currentTestCase.assignAuthor("Deepak Tiwari");
        mobileDriverAction.waitForElementVisible(welcomeScreen.loginScreenLink,const_timeout_medium, LOGIN_LINK_WELCOMESCREEN);
        mobileDriverAction.click(welcomeScreen.loginScreenLink,LOGIN_LINK_WELCOMESCREEN);
        currentTestCase.info("Trying to Enter  username and Password  ");
        mobileDriverAction.sendText(loginScreen.usernameText,username,USERNAME_TEXT_LOGINSCREEN);
        mobileDriverAction.sendText(loginScreen.passwordText,password,PASSWORD_TEXT_LOGINSCREEN);
        TimeUnit.SECONDS.sleep(2);
        boolean isEnabled=new Boolean(loginScreen.loginButton.getAttribute("enabled"));
        if (isEnabled)
            currentTestCase.pass("Login Button is Enabled");
        else
            currentTestCase.fail("Login Button is not Enabled");

        Assert.assertTrue(isEnabled);
        mobileDriverAction.click(welcomeScreen.scLogo,null);
        mobileDriverAction.click(welcomeScreen.scLogo,null);
        commonUtil.logExecutionEnd();
    }

    @Test( description = " Test case 32845: [Login ] Verify login button is enabled when user enter credentials")
    public void tc001_VerifyLoginBtnIsEnabled() throws Exception {

        commonUtil.logExecutionStart();
        currentTestCase.assignAuthor("Deepak Tiwari");
        mobileDriverAction.waitForElementVisible(welcomeScreen.loginScreenLink,const_timeout_medium, LOGIN_LINK_WELCOMESCREEN);
        mobileDriverAction.click(welcomeScreen.loginScreenLink,LOGIN_LINK_WELCOMESCREEN);
        currentTestCase.info("Trying to Enter  username and Password  ");
        mobileDriverAction.sendText(loginScreen.usernameText,user.getUsername(),USERNAME_TEXT_LOGINSCREEN);
        mobileDriverAction.sendText(loginScreen.passwordText,user.getPassword(),PASSWORD_TEXT_LOGINSCREEN);
        TimeUnit.SECONDS.sleep(2);
        boolean isEnabled=new Boolean(loginScreen.loginButton.getAttribute("enabled"));
         if (isEnabled)
            currentTestCase.pass("Login Button is Enabled");
        else
            currentTestCase.fail("Login Button is not Enabled");
        Assert.assertTrue(isEnabled);
        commonUtil.logExecutionEnd();
    }



//    @Test(dependsOnMethods = {"tc001_VerifyLoginBtnIsEnabled"},alwaysRun = true,description = "Test case 29622: [Login] : Verify that an error message is displayed on entering\n" +
//            "invalid email id and password")
//    public void tc002_LoginWithInvalidCredentials() throws Exception {
//
//        commonUtil.logExecutionStart();
//        String invalidPwd="xxxxxx";
//        currentTestCase.assignAuthor("Deepak Tiwari");
//        currentTestCase.info("Trying to login with username "+user.getUsername()+ " and Password is "+invalidPwd);
//        loginScreen.loginAction(user.getUsername(),invalidPwd);
//        currentTestCase.info("Looking for invalid credential error msg...");
//        mobileDriverAction.waitForElementVisible(loginScreen.invalidCredErrorToastMsg,const_timeout_medium,ERROR_MSG_LOGINSCREEN);
//        boolean isErrorMsgFound =loginScreen.invalidCredErrorToastMsg !=null;
//        String  errorMsgStr=mobileDriverAction.getText(loginScreen.invalidCredErrorToastMsg,ERROR_MSG_LOGINSCREEN);
//        if (isErrorMsgFound && commonUtil.removeWhiteSpaces(errorMsgStr).contains(commonUtil.removeWhiteSpaces(const_errorMsg_invalidCred))) {
//            currentTestCase.pass("Invalid Credential Error Msg Found");
//            currentTestCase.pass(CommonUtil.getStringForReport(errorMsgStr));
//        }
//        else
//            currentTestCase.fail("Invalid Credential Error Msg Not Found");
//        Assert.assertTrue(isErrorMsgFound);
//        commonUtil.logExecutionEnd();
//    }
//
//
//
//
//    @Test(dependsOnMethods = {"tc002_LoginWithInvalidCredentials"},alwaysRun = true,description = "Test case 29984: [Login] : Verify that on tapping the Forgot password?, user is\n" +
//            "    directed to the Forgot Password screen")
//    public void tc003_VerifyForgotPasswordScreen() throws Exception {
//
//        boolean isVerified=false;
//        commonUtil.logExecutionStart();
//        currentTestCase.assignAuthor("Deepak Tiwari");
//        mobileDriverAction.waitForElementVisible(loginScreen.forgotPasswordLink,const_timeout_medium, FORGOTPWD_LINK_LOGINSCREEN);
//        mobileDriverAction.click(loginScreen.forgotPasswordLink,FORGOTPWD_LINK_LOGINSCREEN);
//        mobileDriverAction.waitForElementVisible(forgotPasswordScreen.forgotPwdScreenTitleText,const_timeout_medium, null);
//       isVerified=forgotPasswordScreen.forgotPwdScreenTitleText.isDisplayed() &&
//                  forgotPasswordScreen.forgotPwdMsgText.isDisplayed() &&
//                  forgotPasswordScreen.forgotPwdEmailTextBox.isDisplayed() &&
//                  forgotPasswordScreen.forgotPwdBackImage.isDisplayed() &&
//                  forgotPasswordScreen.forgotPwdSubmitButton.isDisplayed() ;
//        if (isVerified) {
//            mobileDriverAction.click(forgotPasswordScreen.forgotPwdBackImage,null);
//            currentTestCase.pass("Password Screen is Verified Successfully");
//        }
//        else
//            currentTestCase.fail("Password Screen  Verification failed");
//        Assert.assertTrue(isVerified);
//        commonUtil.logExecutionEnd();
//    }
//
//
//    @Test(dependsOnMethods = {"tc003_VerifyForgotPasswordScreen"},alwaysRun = true,description = "Test case 29912:[Login] : Verify an error message is displayed as user tries to login\n" +
//            "when there is no internet connectivity")
//    public void tc004_VerifyLoginWithNoInternet() throws Exception {
//
//        commonUtil.logExecutionStart();
//        currentTestCase.assignAuthor("Deepak Tiwari");
//        currentTestCase.info("Trying to login with username "+user.getUsername()+ " and Password is "+user.getPassword());
//        mobileDriverAction.switchWifiAndData(MobileDriverActionAdapter.SWITCH_WIFI_DATA_OFF);
//        mobileDriverAction.waitForElementVisible(welcomeScreen.loginScreenLink,const_timeout_medium, LOGIN_LINK_WELCOMESCREEN);
//        mobileDriverAction.click(welcomeScreen.loginScreenLink,LOGIN_LINK_WELCOMESCREEN);
//        loginScreen.loginAction(user.getUsername(),user.getPassword());
//        currentTestCase.info("Looking for No Internet error msg...");
//        mobileDriverAction.waitForElementVisible(loginScreen.noInternetErrorToastMsg,const_timeout_medium,ERROR_MSG_LOGINSCREEN);
//        boolean isErrorMsgFound = loginScreen.noInternetErrorToastMsg !=null;
//        String  errorMsgStr=mobileDriverAction.getText(loginScreen.noInternetErrorToastMsg,ERROR_MSG_LOGINSCREEN);
//        if (isErrorMsgFound && commonUtil.removeWhiteSpaces(errorMsgStr).contains(commonUtil.removeWhiteSpaces(const_errorMsg_noInternet))) {
//            mobileDriverAction.reLaunchApp();
//            currentTestCase.pass("No Internet Error Msg Found \n");
//            currentTestCase.pass(CommonUtil.getStringForReport(errorMsgStr));
//
//        }
//        else
//            currentTestCase.fail("No Internet Error Msg Not Found");
//
//        mobileDriverAction.switchWifiAndData(MobileDriverActionAdapter.SWITCH_WIFI_DATA_ON);
//        Assert.assertTrue(isErrorMsgFound);
//        commonUtil.logExecutionEnd();
//    }
//
//
//    @Test(dependsOnMethods = {"tc004_VerifyLoginWithNoInternet"},alwaysRun = true,description = "Test case 29695:[Login] : Verify if user is able to login successfully on entering Valid Cerdentials")
//    public void tc005_VerifySuccessfulLogin() throws Exception {
//
//        commonUtil.logExecutionStart();
//        currentTestCase.assignAuthor("Deepak Tiwari");
//        currentTestCase.info("Trying to login with username "+user.getUsername()+ " and Password is "+user.getPassword());
//        boolean isLoginSuccess=salonCentricCommons.loginToSalonCentric(user);
//        if (isLoginSuccess) {
//            currentTestCase.pass("Login Is Successful");
//        }
//        else
//            currentTestCase.fail("Failed to Login..");
//        Assert.assertTrue(isLoginSuccess);
//        commonUtil.logExecutionEnd();
//    }
//
//
//    @Test(dependsOnMethods = {"tc005_VerifySuccessfulLogin"},description = "Test case 29562: [Permissions] : Verify that after successful Login, the Location\n" +
//            "access Permission screen is displayed")
//    public void tc006_VerifyLocationScreenAfterSuccessfulLogin() throws Exception {
//
//        commonUtil.logExecutionStart();
//        currentTestCase.assignAuthor("Deepak Tiwari");
//        boolean isLocationScreenFound=salonCentricCommons.isLocationScreenDisplayed();
//        if (isLocationScreenFound) {
//            salonCentricCommons.allowDenyLocation(SalonCentricCommons.PERMISSION_ALLOW);
//            currentTestCase.pass("Location Screen Found");
//        }
//        else
//            currentTestCase.fail("Location Screen not Found");
//        Assert.assertTrue(isLocationScreenFound);
//        commonUtil.logExecutionEnd();
//    }
//
//
//    @Test(dependsOnMethods = {"tc006_VerifyLocationScreenAfterSuccessfulLogin"},description = " Test case 29705: [Home] : Verify that after tutorial screens the Home screen displays\n" +
//            "    as the first screen in the application")
//    public void tc006_VerifyUserCanSkipTutorialsScreen() throws Exception {
//
//        commonUtil.logExecutionStart();
//        currentTestCase.assignAuthor("Deepak Tiwari");
//        mobileDriverAction.waitForElementVisible(newHomeScreen.closeNewHomeScreenBtn,const_timeout_medium,null);
//        if (mobileDriverAction.isElementFound(newHomeScreen.closeNewHomeScreenBtn,null))
//            mobileDriverAction.click(newHomeScreen.closeNewHomeScreenBtn, null);
//        else
//           currentTestCase.info("Tutorial Screen Not found");
//
//      boolean isTutorialScreenFound=mobileDriverAction.isElementFound(newHomeScreen.closeNewHomeScreenBtn,null);
//        if (isTutorialScreenFound)
//            currentTestCase.fail("Tutorial Screen Not Skipped");
//        else
//            currentTestCase.pass("Tutorial Screen Skipped");
//
//        Assert.assertFalse(isTutorialScreenFound);
//        commonUtil.logExecutionEnd();
//
//    }
//
//    @Test(dependsOnMethods = {"tc006_VerifyUserCanSkipTutorialsScreen"},description = " Test case 30210: [Tutorial] : Verify if user can skip the tutorial Screen and navigate\n" +
//            "to Home Screen")
//    public void tc007_VerifyHomeScreenAfterTutorialsScreen() throws Exception {
//
//
//        commonUtil.logExecutionStart();
//        currentTestCase.assignAuthor("Deepak Tiwari");
//        mobileDriverAction.waitForElementVisible(homeScreen.cartIcon,const_timeout_medium,null);
//        boolean isHomeScreenFound=mobileDriverAction.isElementFound(homeScreen.cartIcon,null);
//        if (isHomeScreenFound) {
//            currentTestCase.pass("Home  Screen Found");
//        }
//        else
//            currentTestCase.fail("Home Screen not Found");
//        Assert.assertTrue(isHomeScreenFound);
//        commonUtil.logExecutionEnd();
//    }
//
//
//    @AfterClass
//    public void logOut() throws Exception {
//        //salonCentricCommons.logoutFromApp();
//    }

    @DataProvider
    public Object[][] loginDataProvider(){
        //commonUtil.read
        String filePath = CommonUtil.getProjectDir() + "/src/main/resources/test-data/login-cred.json";
        JSONArray array = commonUtil.readFileAsJSONArray(filePath);
        Object[][] testdata = new Object[array.length()][2];
        for (int i = 0; i < array.length(); i++) {
            testdata[i][0] = array.getJSONObject(i).get("username");
            testdata[i][1] = array.getJSONObject(i).get("password");
        }
        return testdata;
    }
}
