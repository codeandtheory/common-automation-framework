package com.yml.framework.contracts.adapters;

import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.yml.framework.aut.commons.AppUiObjectNames;
import com.yml.framework.common.Direction;
import com.yml.framework.common.Platform;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import static java.time.Duration.ofMillis;


public abstract class MobileDriverActionAdapter {


    @Inject
    @Named("appPackage")
    public String appPackage;

    @Inject
    @Named("browserName")
    public String browserName;


    public WebDriver driver;
    public Logger logger;

    public ExtentTest testCase;
    public Platform platform;
    public static final String LOCATOR_XPATH = "XPATH";
    public static final String LOCATOR_ID = "ID";
    public static final String SCROLL_UP = "up";
    public static final String SCROLL_DOWN = "down";
    public static final String SWITCH_WIFI_DATA_ON = "ON";
    public static final String SWITCH_WIFI_DATA_OFF = "OFF";

    public JavascriptExecutor js;

    public MobileDriverActionAdapter(WebDriver driver, Logger logger) {
        this.driver = driver;
        this.logger = logger;
    }

    public MobileDriverActionAdapter() {

    }

    public ExtentTest getTestCase() {
        return testCase;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setTestCase(ExtentTest testCase) {
        this.testCase = testCase;
    }

    public abstract void tapByCoordinates(int x, int y);

    public WebElement getWebElementByLocator(String locatorStrategy, String locator) {
        testCase.info("Trying to find the Element with " + locatorStrategy + "  : " + locator);
        WebElement element = null;
        try {
            switch (locatorStrategy.toUpperCase()) {
                case LOCATOR_XPATH:
                    element = this.driver.findElement(By.xpath(locator));
                    break;
                case LOCATOR_ID:
                    element = this.driver.findElement(By.id(locator));
                    break;
            }
            this.testCase.info("Element found");
        } catch (Exception e) {
            e.printStackTrace();
            this.testCase.fail("Failed to find the Element with " + locatorStrategy + "  : " + locator);
            this.testCase.fail(e);
            element = null;

        }
        this.testCase.info("Element Found with " + locatorStrategy + "  : " + locator);
        return element;

    }

    public boolean isElementCurrentlyDisplayed(RemoteWebElement elementToBeDisplayed) {
        boolean isDisplayed = false;
        try {
            isDisplayed = elementToBeDisplayed.isDisplayed();
        } catch (Exception e) {
            logger.info(e.getMessage());
            isDisplayed = false;
        }
        return isDisplayed;
    }

    public List<WebElement> getWebElements(String locatorStrategy, String locator) {
        this.testCase.info("Trying to find the Element with " + locatorStrategy + "  : " + locator);
        List<WebElement> elementList = null;
        try {
            switch (locatorStrategy) {
                case LOCATOR_XPATH:
                    elementList = this.driver.findElements(By.xpath(locator));
                    break;
                case LOCATOR_ID:
                    elementList = this.driver.findElements(By.id(locator));
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            this.testCase.fail("Failed to find the Element with " + locatorStrategy + "  : " + locator);
            this.testCase.fail(e);
            elementList = null;
        }
        this.testCase.info("Element Found with " + locatorStrategy + "  : " + locator);
        return elementList;

    }

    public WebElement getWebElementByLocator(WebElement webElement, String locatorStrategy, String locator) {

        WebElement element = null;
        try {
            switch (locatorStrategy) {
                case LOCATOR_XPATH:
                    element = webElement.findElement(By.xpath(locator));
                    break;
                case LOCATOR_ID:
                    element = webElement.findElement(By.id(locator));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            element = null;
        }
        return element;

    }

    public void click(String locatorStrategy, String locator) {
        WebElement element = getWebElementByLocator(locatorStrategy, locator);
        element.click();

    }

    public void click(WebElement element, AppUiObjectNames elementNameForLogging) {
        if (elementNameForLogging == null)
            elementNameForLogging = AppUiObjectNames.APP_UI_OBJECT;
        try {
            element.click();
            testCase.pass(elementNameForLogging + " Element Clicked");
        } catch (Exception e) {
            testCase.info("failed to click the element " + elementNameForLogging);
            testCase.info(e);
        }

    }

    public String getText(WebElement element, AppUiObjectNames elementNameForLogging) {

        if (elementNameForLogging == null)
            elementNameForLogging = AppUiObjectNames.APP_UI_OBJECT;
        try {
            String sText = element.getText();
            testCase.info("Text of element " + elementNameForLogging + " is\n" + sText);
            return sText;
        } catch (Exception e) {
            testCase.info("Failed to find Text of element " + elementNameForLogging);
            testCase.info(e);
            return null;
        }


    }

    public String getText(WebElement root, String locatorStrategy, String locator) {
        WebElement element = getWebElementByLocator(root, locatorStrategy, locator);
        try {
            testCase.info("Got text ");
            String sText = element.getText();
            testCase.info(sText);
            return sText;
        } catch (Exception e) {
            testCase.info("Could not get text");
            return null;
        }


    }

    public boolean waitForElementVisible(String locatorStrategy, String locator, long timeOutInSeconds) {

        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOutInSeconds));
        WebElement element = null;
        // testCase.info("Starting wait for " + getLocatorDisplayText(locator) + " to be visible " + getHoverStringForReport(locator));
        try {
            switch (locatorStrategy) {
                case LOCATOR_XPATH:
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                    break;
                case LOCATOR_ID:
                    element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
                    break;
            }

        } catch (Exception e) {
            return false;
        }
        return element.isDisplayed();

    }

    public boolean waitForElementVisible(WebElement element, long timeOutInSeconds, AppUiObjectNames elementNameForLogging) {

        try {

            if (element == null) {
                return false;
            }
            elementNameForLogging = getElementName(elementNameForLogging);

            WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOutInSeconds));
            testCase.info("Waiting  for the element  " + elementNameForLogging + " about " + timeOutInSeconds + " seconds..");

            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            // testCase.error(e);
            logger.severe("Not Found . ");
            return false;
        }
        testCase.info(elementNameForLogging + " Found ");
        return element.isDisplayed();
    }


    public boolean waitForElementInvisible(WebElement element, long timeOutInSeconds, AppUiObjectNames elementNameForLogging) {

        if (element == null) {
            return false;
        }
        elementNameForLogging = getElementName(elementNameForLogging);

        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOutInSeconds));
        testCase.info("Waiting  for the element  " + elementNameForLogging + " about " + timeOutInSeconds + " seconds..");
        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            // testCase.error(e);
            logger.severe("Not Found . ");
            return false;
        }
        testCase.info(elementNameForLogging + " Disappeared. ");
        return element.isDisplayed();
    }


    public boolean waitForElementsVisible(List<WebElement> elementList, long timeOutInSeconds, AppUiObjectNames elementNameForLogging) {

        if (elementList == null) {
            return false;
        }
        elementNameForLogging = getElementName(elementNameForLogging);

        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOutInSeconds));
        testCase.info("Waiting  for the element  " + elementNameForLogging + " about " + timeOutInSeconds + " seconds..");
        try {
            wait.until(ExpectedConditions.visibilityOf(elementList.get(0)));
        } catch (Exception e) {
            // testCase.error(e);
            logger.severe("Not Found . ");
            return false;
        }
        testCase.info(elementNameForLogging + " Found ");
        return elementList.get(0).isDisplayed();
    }


    public void sendText(WebElement element, String sText, AppUiObjectNames elementNameForLogging) {

        elementNameForLogging = getElementName(elementNameForLogging);
        if (element == null) {
            return;
        }
        element.clear();
        element.sendKeys(sText);
        testCase.info("Entered " + sText + "on element " + elementNameForLogging);

    }

    public void sendKeys(String locatorStrategy, String locator, Keys key) {

        WebElement element = getWebElementByLocator(locatorStrategy, locator);
        element.sendKeys(key);
        testCase.info("Entered " + key);

    }

    public void clickMobileELement(String locatorStrategy, String locator) {
        WebElement mobElement = getMobileElement(locatorStrategy, locator);
        mobElement.click();
        testCase.info("Element clicked..");
    }


    public void pressBack() {
        this.driver.navigate().back();
        testCase.info("BACK PRESSED..");
    }

    public WebElement getMobileElement(String locatorStrategy, String locator) {
        return (WebElement) getWebElementByLocator(locatorStrategy, locator);
    }


    public boolean waitForElementNotVisible(String locatorStrategy, String locator, long timeOutInSeconds) {

        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOutInSeconds));
        Boolean isAbsent = true;
        try {
            switch (locatorStrategy) {
                case LOCATOR_XPATH:
                    isAbsent = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
                    break;
                case LOCATOR_ID:
                    isAbsent = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(locator)));
                    break;
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            isAbsent = false;

        }

        return isAbsent;

    }


    public String getAttributeValue(String locatorStrategy, String locator, String attributeName) {
        WebElement element = getWebElementByLocator(locatorStrategy, locator);
        String sText = element.getAttribute(attributeName);
        return sText;

    }


    public void goBack(String locatorStrategy, String locator) {
        while (!waitForMobileElementVisible(locatorStrategy, locator, 10)) {
            driver.navigate().back();
            testCase.info("BACK PRESSED..");
        }
    }


    public void goBackTill(String locatorStrategy, String locator) {

        while (!waitForMobileElementVisible(locatorStrategy, locator, 1)) {
            driver.navigate().back();
        }
    }


    public boolean waitForMobileElementVisible(String locatorStrategy, String locator, long timeOutInSeconds) {

        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timeOutInSeconds));

        WebElement element = null;
        try {
            switch (locatorStrategy) {
                case LOCATOR_XPATH:
                    element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                    break;
                case LOCATOR_ID:
                    element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
                    break;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //			e.printStackTrace();
            testCase.info(locator + " not found ..");
            //testCase.info(e.getCause());
            return false;
        }

        return element.isDisplayed();

    }

    public void waitAndClickMobileElement(String locatorStrategy, String locator, long timeOutInSeconds) {
        try {
            WebElement mobElement = waitTillMobileElementClickable(locatorStrategy, locator, timeOutInSeconds);
            mobElement.click();
            testCase.info(" clicked");
        } catch (Exception e) {
            testCase.info(" not found ..");
            //testCase.info(e.getCause());
            e.printStackTrace();
        }
    }


    public WebElement waitTillMobileElementClickable(String locatorStrategy, String locator, long timeOutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));


        WebElement element = null;
        try {
            switch (locatorStrategy) {
                case LOCATOR_XPATH:
                    element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                    break;
                case LOCATOR_ID:
                    element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
                    break;
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            //			e.printStackTrace();

        }

        return element;

    }


    public WebElement waitTillMobileElementVisible(String locatorStrategy, String locator, long timeOutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));


        WebElement element = null;
        try {
            switch (locatorStrategy) {
                case LOCATOR_XPATH:
                    element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                    break;
                case LOCATOR_ID:
                    element = (WebElement) wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
                    break;
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            //			e.printStackTrace();

        }

        return element;

    }

    public boolean isElementFound(String locatorStrategy, String locator) {

        boolean isFound = false;
        try {
            if (getWebElementByLocator(locatorStrategy, locator) != null) {
                isFound = true;
            }
        } catch (Exception e) {
            isFound = false;
        }

        return isFound;
    }

    public boolean isElementFound(WebElement element, String locatorStrategy, String locator) {

        boolean isFound = false;
        try {
            if (getWebElementByLocator(element, locatorStrategy, locator) != null) {
                isFound = true;
            }
        } catch (Exception e) {
            isFound = false;
        }

        return isFound;
    }

    public boolean isElementFound(WebElement element, AppUiObjectNames elementNameForLogging) {

        boolean isFound = false;
        try {
            if (element != null) {
                isFound = element.isDisplayed();
            }
        } catch (Exception e) {
            isFound = false;
        }

        return isFound;
    }

    public boolean isElementsFound(List<WebElement> elementList, AppUiObjectNames elementNameForLogging) {

        boolean isFound = false;
        try {
            if (elementList != null) {
                isFound = elementList.get(0).isDisplayed();
            }
        } catch (Exception e) {
            isFound = false;
        }

        return isFound;
    }

    public Sequence getSwipeSequence(Direction direction, WebElement element) {

        int startX = 0, startY = 0, endX = 0, endY = 0;
        Point source = element.getLocation();
        switch (direction) {
            case LEFT:
                startX = source.x + element.getSize().width - 10;
                startY = source.y;
                endX = source.x;
                endY = source.y;
                break;
            case RIGHT:
                startX = source.x;
                startY = source.y;
                endX = source.x + element.getSize().width - 10;
                endY = source.y;
                break;
            case UP:
                startX = source.x;
                startY = source.y + element.getSize().height - 10;
                endX = source.x;
                endY = source.y;
                break;
            case DOWN:
                startX = source.x;
                startY = source.y;
                endX = source.x;
                endY = source.y + element.getSize().height - 10;
                break;

        }

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0), PointerInput.Origin.viewport(), startX, startY));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(600)));
        sequence.addAction(finger.createPointerMove(ofMillis(600),
                PointerInput.Origin.viewport(), endX, endY));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
        return sequence;

    }

    public Sequence getTapSequence(Direction direction, WebElement element) {

        int startX = 0, startY = 0, endX = 0, endY = 0;
        Point source = element.getLocation();
        switch (direction) {
            case LEFT:
                startX = source.x - element.getSize().width / 2 - 200;
                startY = source.y;
                break;
            case RIGHT:
                startX = source.x + element.getSize().width / 2 + 200;
                startY = source.y;
                break;
            case UP:
            case DOWN:
                startX = element.getSize().width / 2;
                startY = source.y;
                break;

        }

        PointerInput input = new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence tapSequence = new Sequence(input, 0);
        tapSequence.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        tapSequence.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tapSequence.addAction(new Pause(input, Duration.ofMillis(200)));
        tapSequence.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        return tapSequence;

    }

    public abstract String getWebViewContext();

    public abstract void setWebViewContext();

    public abstract void switchWifiAndData(String onOffFlag);

    public abstract void swipe(Direction direction, WebElement element);

    public abstract void tapOnElementBasisPosition(Direction direction, WebElement element);

    public abstract void scrollInMobile_cloud(String locatorStrategy, String targerLocator, String upOrDown);

    public abstract void switchToWebview(String webview);

    public abstract String getClipboardText();

    public abstract void appiumScroll(RemoteWebElement elementToBeFound, String upOrDown, int maxScrolls);

    public abstract String getChildElementText(WebElement parentElement, String locatorStrategy, String locator);

    public abstract void scrollInMobile(String locatorStrategy, String targerLocator, String upOrDown, int maxSwipes);

    public abstract void reLaunchApp();

    public abstract void reLaunchAppWithClearData();

    public abstract void killApp();

    public abstract void goBack();

    public abstract void pressEnter();

    public abstract void searchActionViaScript();

    public abstract void launchApp();

    public AppUiObjectNames getElementName(AppUiObjectNames elementNameForLogging) {
        if (elementNameForLogging == null)
            return AppUiObjectNames.APP_UI_OBJECT;
        else
            return elementNameForLogging;

    }


    public void executeScript(String script){
        this.testCase.info("Executing Script "+script);
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(script);
        }
        catch (Exception e){
            this.testCase.fail(e);
        }
    }


    public void clearBrowserCacheAndCookies() {

        switch (browserName.toLowerCase()){
            case "chrome":
                ChromeDriver chromeDriver=(ChromeDriver)driver;
                chromeDriver.getLocalStorage().clear();
                chromeDriver.getSessionStorage().clear();
                break;
            case "safari":
                SafariDriver safariDriver=(SafariDriver)driver;
                safariDriver.resetCooldown();
                break;
            case "firefox":
                FirefoxDriver firefoxDriver=(FirefoxDriver)driver;
                firefoxDriver.getLocalStorage().clear();
                firefoxDriver.getSessionStorage().clear();
                break;
            case "edge":
                EdgeDriver edgeDriver=(EdgeDriver)driver;
                edgeDriver.getLocalStorage().clear();
                edgeDriver.getSessionStorage().clear();
                driver=new EdgeDriver();
                break;
            default:
                driver.manage().getCookies().clear();
                break;
        }

    }



}
