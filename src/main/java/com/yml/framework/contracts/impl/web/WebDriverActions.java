package com.yml.framework.contracts.impl.web;

import com.yml.framework.common.Direction;
import com.yml.framework.contracts.adapters.MobileDriverActionAdapter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.logging.Logger;

public class WebDriverActions extends MobileDriverActionAdapter {

    private WebDriver webDriver;
    private Logger logger;

    public WebDriverActions(WebDriver driver, Logger logger) {
        super(driver,logger);
        this.webDriver=driver;
        this.logger=logger;
    }

    @Override
    public void tapByCoordinates(int x, int y) {

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
    public void swipe(Direction direction, WebElement element) {

    }

    @Override
    public void tapOnElementBasisPosition(Direction direction, WebElement element) {

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

    }

    @Override
    public String getChildElementText(WebElement parentElement, String locatorStrategy, String locator) {
        return null;
    }

    @Override
    public void scrollInMobile(String locatorStrategy, String targerLocator, String upOrDown, int maxSwipes) {

    }

    @Override
    public void reLaunchApp() {

    }

    @Override
    public void reLaunchAppWithClearData() {

    }

    @Override
    public void killApp() {

    }

    @Override
    public void goBack() {

    }

    @Override
    public void pressEnter() {

    }

    @Override
    public void searchActionViaScript() {

    }

    @Override
    public void launchApp() {

    }


}
