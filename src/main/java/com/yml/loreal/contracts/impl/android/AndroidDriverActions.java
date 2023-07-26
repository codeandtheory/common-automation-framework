package com.yml.loreal.contracts.impl.android;

import com.google.common.collect.ImmutableMap;
import com.yml.loreal.common.Direction;
import com.yml.loreal.contracts.adapters.MobileDriverActionAdapter;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.connection.ConnectionStateBuilder;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.yml.loreal.common.ApplicationConstants.*;
import static freemarker.template.utility.Collections12.singletonList;


public class AndroidDriverActions extends MobileDriverActionAdapter {


    private AndroidDriver androidDriver;
    private Logger logger;


    public AndroidDriverActions(WebDriver driver, Logger logger) {
        super(driver, logger);
        this.androidDriver = (AndroidDriver) driver;
        this.logger = logger;

    }

    public AndroidDriverActions() {


    }


    @Override
    public void tapByCoordinates(int x, int y) {

    }

    @Override
    public String getWebViewContext() {
        ArrayList contexts = new ArrayList(androidDriver.getContextHandles());
        for (Object context : contexts) {
            if (!context.toString().equals("NATIVE_APP")) {
                return context.toString();
            }
        }
        return null;
    }

    @Override
    public void setWebViewContext() {
        androidDriver.context(getWebViewContext());
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
    public void appiumScroll(RemoteWebElement elementToBeFound, String upOrDown, int maxScrolls) {
        js = (JavascriptExecutor) androidDriver;
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("direction", upOrDown);
        //scrollObject.put("elementId", elementToBeFound.getId());
        scrollObject.put("strategy", "-android uiautomator");
        scrollObject.put("xpath", elementToBeFound.getAttribute("xpath"));
        js.executeScript("mobile:scroll", scrollObject);
        for (int i = 0; i < maxScrolls; i++) {
            if (isElementCurrentlyDisplayed(elementToBeFound)) {
                logger.info("element found.");
                break;
            } else {
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

        TouchAction action = new TouchAction(androidDriver);
        PointOption startPoint = new PointOption().withCoordinates(500, 1000);
        PointOption endPoint = new PointOption().withCoordinates(300, 300);

        switch (upOrDown.toLowerCase()) {

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

        if (targerLocator != null) {
            for (int i = 0; i < maxSwipes; i++) {
                if (isElementFound(locatorStrategy, targerLocator)) {
                    break;
                } else {
                    //AndroidDriverActions.executeAdbCommand(scrollCommandList);
                    action.press(startPoint)
                            .waitAction(new WaitOptions()
                                    .withDuration(Duration.ofMillis(500)))
                            .moveTo(endPoint).release().perform();
                    continue;
                }
            }
        } else {
            action.press(startPoint)
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(500)))
                    .moveTo(endPoint).release().perform();
            //AndroidDriverActions.executeAdbCommand(scrollCommandList);
        }
    }


    @Override
    public void reLaunchApp() {

        try {
            testCase.info("Re launching App.");
            androidDriver.terminateApp(platform.getPlatformAppPackageName());
            testCase.info("Terminated App and waiting for " + const_timeout_medium + " seconds");
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            testCase.info("Starting App and waiting for " + const_timeout_medium + " seconds");
            androidDriver.activateApp(platform.getPlatformAppPackageName());
            TimeUnit.SECONDS.sleep(const_timeout_large);
            testCase.pass("App Started Successfully ");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.error(e);
        }
    }


    @Override
    public void reLaunchAppWithClearData() {

        try {
            testCase.info("Re launching App With clear Data.");
            androidDriver.terminateApp(platform.getPlatformAppPackageName());
            testCase.info("Terminated App and waiting for " + const_timeout_medium + " seconds");
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            androidDriver.resetApp();
            testCase.info("Starting App and waiting for " + const_timeout_medium + " seconds");
            androidDriver.activateApp(platform.getPlatformAppPackageName());
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            testCase.pass("App Started Successfully ");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.error(e);
        }
    }


    public void killApp() {
        try {
            testCase.info("Killing App.");
            androidDriver.terminateApp(platform.getPlatformAppPackageName());
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            testCase.info("Terminated App and Waiting for " + const_timeout_medium + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.error(e);
        }
    }

    @Override
    public void goBack() {

        androidDriver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    @Override
    public void pressEnter() {

//        Dimension dimensions = driver.manage().window().getSize();
//        int yMax = dimensions.getHeight();
//        int xMax = dimensions.getWidth();
//        int xCoordinate=(int) (xMax * 0.95);
//        int yCoordinate=(int) (yMax * 0.95);
        //    androidDriver.sendKeys(Keys.RETURN);
        androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    @Override
    public void searchActionViaScript() {

        androidDriver.executeScript("mobile: performEditorAction", ImmutableMap.of("action", "Search"));

    }


    public void launchApp() {
        try {
            testCase.info("Launching App.");
            androidDriver.resetApp();
            androidDriver.activateApp(platform.getPlatformAppPackageName());
            TimeUnit.SECONDS.sleep(const_timeout_medium);
            testCase.pass("App Launched Successfully ");
        } catch (Exception e) {
            e.printStackTrace();
            testCase.error(e);
        }
    }


    public void swipe(Direction direction, WebElement element) {

        androidDriver.perform(singletonList(super.getSwipeSequence(direction, element)));
        logger.info("Swipe " + direction + " successful");
    }

    @Override
    public void tapOnElementBasisPosition(Direction direction, WebElement element) {

        androidDriver.perform(singletonList(super.getTapSequence(direction, element)));
        testCase.info("Tap " + direction + " successful");
    }

    public void switchWifiAndData(String onOffFlag) {

        switch (onOffFlag) {
            case MobileDriverActionAdapter.SWITCH_WIFI_DATA_OFF:
                androidDriver.setConnection(new ConnectionStateBuilder().withWiFiDisabled().withDataDisabled().build());
                break;
            case MobileDriverActionAdapter.SWITCH_WIFI_DATA_ON:
                androidDriver.setConnection(new ConnectionStateBuilder().withWiFiEnabled().withDataEnabled().build());
                break;

        }
        try {
            TimeUnit.SECONDS.sleep(const_timeout_small);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.testCase.info("Wifi and Data set to " + onOffFlag);
    }


}

