package com.ct.framework.contracts.impl.ios;

import com.ct.framework.common.Direction;
import com.ct.framework.contracts.adapters.MobileDriverActionAdapter;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.ct.framework.aut.commons.ApplicationConstants.const_timeout_medium;
import static com.ct.framework.aut.commons.ApplicationConstants.const_timeout_small;
import static freemarker.template.utility.Collections12.singletonList;


public class IOSDriverActions extends MobileDriverActionAdapter{

    private  IOSDriver iosDriver;
    private Logger logger;

    public IOSDriverActions(WebDriver driver,Logger logger) {
        super(driver,logger);
        this.iosDriver=(IOSDriver) driver;
        this.logger=logger;
    }

    @Override
    public void tapByCoordinates(int x, int y) {

    }

    @Override
    public List<WebElement> getWebElements(String locatorStrategy, String locator) {
        return null;
    }


    @Override
    public WebElement getWebElementByLocator(WebElement webElement, String locatorStrategy, String locator) {
        return null;
    }

    @Override
    public void scrollInMobile_cloud(String locatorStrategy, String targerLocator, String upOrDown) {

    }

    @Override
    public void switchToWebview(String webview) {

    }

    @Override
    public String getClipboardText() {
        return null;
    }

    @Override
    public void appiumScroll(RemoteWebElement elementToBeFound, String upOrDown,int maxScrolls) {
        js=(JavascriptExecutor)iosDriver;
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("direction", upOrDown);
        js.executeScript("mobile:scroll", scrollObject);
        for(int i=0; i<maxScrolls;i++) {
            if(isElementCurrentlyDisplayed(elementToBeFound)) {
                logger.info("element found.");
                break;
            }
            else {
                js.executeScript("mobile:scroll", scrollObject);
                continue;
            }
        }



    }

    @Override
    public String getChildElementText(WebElement parentElement, String locatorStrategy, String locator) {
        return null;
    }

    @Override
    public void scrollInMobile(String locatorStrategy, String targerLocator, String upOrDown, int maxSwipes) {
        TouchAction action = new TouchAction(iosDriver);
        PointOption startPoint = new PointOption().withCoordinates(500, 1000);
        PointOption endPoint = new PointOption().withCoordinates(300, 300);

        switch(upOrDown.toLowerCase()) {

            case "up":
                startPoint = new PointOption().withCoordinates(500, 1000);
                endPoint = new PointOption().withCoordinates(300, 300);
                break;
            case "down":

                startPoint = new PointOption().withCoordinates(500, 500);
                endPoint = new PointOption().withCoordinates(500, 1000);

                break;

        }



        //List<String> scrollCommandList=Arrays.asList(commandForScroll.split(" "));

        if(targerLocator != null)
        {
            for(int i=0; i<maxSwipes;i++) {
                if(isElementFound(locatorStrategy,targerLocator)) {
                    break;
                }
                else {
                    //AndroidDriverActions.executeAdbCommand(scrollCommandList);
                    action.press(startPoint)
                            .waitAction(new WaitOptions()
                                    .withDuration(Duration.ofMillis(500)))
                            .moveTo(endPoint).release().perform();
                    continue;
                }
            }
        }
        else {
            action.press(startPoint)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .moveTo(endPoint).release().perform();
            //AndroidDriverActions.executeAdbCommand(scrollCommandList);
        }
    }

    @Override
    public boolean isElementFound(WebElement element, String locatorStrategy, String locator) {
        return false;
    }

    @Override
    public String getWebViewContext() {
        return null;
    }

    @Override
    public void setWebViewContext() {

    }

    @Override
    public void switchWifiAndData(String onOffFlag) {

    }




    @Override
    public void sendKeys(String locatorStrategy, String locator, Keys key) {

    }


    @Override
    public boolean waitForElementNotVisible(String locatorStrategy, String locator, long timeOutInSeconds) {
        return false;
    }

    @Override
    public boolean waitForElementVisible(String locatorStrategy, String locator, long timeOutInSeconds) {
        return false;
    }

    @Override
    public void click(String locatorStrategy, String locator) {

    }

    @Override
    public void reLaunchApp(){
        try {
            testCase.info("Re launching App.");
            iosDriver.terminateApp(platform.getPlatformAppPackageName());
            testCase.info("Terminated App and waiting for "+const_timeout_small+ " seconds");
            TimeUnit.SECONDS.sleep(const_timeout_small);
            testCase.info("Starting App and waiting for "+const_timeout_medium+ " seconds");
            iosDriver.activateApp(platform.getPlatformAppPackageName());
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            testCase.pass("App Started Successfully ");
        }
        catch (Exception e){
            e.printStackTrace();
            testCase.fail(e);
        }

    }

    @Override
    public void reLaunchAppWithClearData(){

        try {
            testCase.info("Re launching App With clear Data.");
            iosDriver.terminateApp(platform.getPlatformAppPackageName());
            testCase.info("Terminated App and waiting for "+const_timeout_medium+ " seconds");
            //TimeUnit.SECONDS.sleep(const_timeout_medium);
          //  iosDriver.resetApp();
            testCase.info("Starting App and waiting for "+const_timeout_medium+ " seconds");
            iosDriver.activateApp(platform.getPlatformAppPackageName());
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            testCase.pass("App Started Successfully ");
        }
        catch (Exception e){
            e.printStackTrace();
            testCase.fail(e);
        }
    }


    public void killApp(){
        try {
            testCase.info("Killing App.");
            iosDriver.terminateApp(platform.getPlatformAppPackageName());
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            testCase.info("Terminated App and Waiting for "+const_timeout_medium+ " seconds");
        }
        catch (Exception e){
            e.printStackTrace();
            testCase.fail(e);
        }
    }

    @Override
    public void goBack() {
        iosDriver.navigate().back();
    }

    @Override
    public void pressEnter() {

    }

    @Override
    public void searchActionViaScript() {

    }

    public  void launchApp(){
        try {
            testCase.info("Launching App.");
            iosDriver.activateApp(platform.getPlatformAppPackageName());
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            testCase.pass("App Launched Successfully ");
        }
        catch (Exception e){
            e.printStackTrace();
            testCase.fail(e);
        }
    }

    public void  swipe(Direction direction, WebElement element) {

            iosDriver.perform(singletonList(super.getSwipeSequence(direction,element)));
            testCase.info("Swipe "+direction + " successful");
        }

    @Override
    public void tapOnElementBasisPosition(Direction direction, WebElement element) {
        iosDriver.perform(singletonList(super.getTapSequence(direction,element)));
        testCase.info("Swipe "+direction + " successful");
    }

}

