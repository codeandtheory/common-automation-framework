package com.yml.loreal.prerequisite;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.yml.loreal.pojo.Platform;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.netty.util.concurrent.BlockingOperationException;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.checkerframework.checker.units.qual.C;
import org.json.JSONObject;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Guice;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * contains all the methods to create a new session and destroy the
 * session after the test(s) execution is over. Each test extends
 * this class.
 */

@Guice(modules = {ConfigurationModule.class})
public class AppiumServerManager {


    public WebDriver driver;

    @Inject
    public Logger logger;

    @Inject
    public Platform platform;

    @Inject
    @Named("appiumJsPathMac")
    public String appiumJsPathMac;

    @Inject
    @Named("appiumJsPathWindows")
    public String appiumJsPathWindows;

    @Inject
    @Named("appiumJsPathLinux")
    public String appiumJsPathLinux;

    @Inject
    @Named("appPackage")
    public String appPackage;

    @Inject
    @Named("appActivity")
    public String appActivity;

    @Inject
    @Named("executionMode")
    public String executionMode;

    @Inject
    @Named("teamId")
    public String teamId;

    @Inject
    @Named("noReset")
    public boolean noResetFlag;

    @Inject
    @Named("chromeDriverPathMac")
    public String chromeDriverPathMac;

    @Inject
    @Named("freshInstallApp")
    public boolean freshInstall;

    @Inject
    @Named("browserName")
    public String browserName;


    @Inject
    @Named("url")
    public String url;


    /**
     * this method starts Appium server. Calls startAppiumServer method to start the session depending upon your OS.
     *
     * @throws Exception Unable to start appium server
     */

    public void invokeAppium() throws Exception {
        logger.info(platform.getPlatformName());
        if (executionMode.equalsIgnoreCase("cloud")) {
            logger.info("Tests are executing in cloud. So Not starting the appium server locally.");
            return;
        }

        String OS = System.getProperty("os.name").toLowerCase();
        try {
            startAppiumServer();
            logger.info("Appium server started successfully");
        } catch (Exception e) {
            logger.severe("Unable to start appium server");
            throw new Exception(e.getMessage());
        }
    }

    /**
     * this method stops Appium server.Calls stopAppiumServer method to
     * stop session depending upon your OS.
     *
     * @throws Exception Unable to stop appium server
     */

    public void stopAppium() throws Exception {
        if (executionMode.equalsIgnoreCase("cloud")) {
            logger.info("Tests are executing in cloud. So Not stopping the appium server locally.");
            return;
        }
        try {
            stopAppiumServer();

            logger.info("Appium server stopped successfully");

        } catch (Exception e) {
            logger.severe("Unable to stop appium server");
            throw new Exception(e.getMessage());
        }
    }


    /**
     * this method creates the driver depending upon the passed parameter (android or iOS)
     * and loads the properties files (config and test data properties files).
     *
     * @param platformName android or iOS
     * @throws Exception issue while loading properties files or creation of driver.
     */

    public WebDriver createDriver(String platformName) throws Exception {

        WebDriver plateformSpecificDriver = null;
        if (platformName.equalsIgnoreCase("android")) {
            plateformSpecificDriver = androidDriver(platform.getPlatformAppAbsoutePath());
            logger.info("Android driver created");

        } else if (platformName.equalsIgnoreCase("ios")) {
            plateformSpecificDriver = iOSDriver(platform.getPlatformAppAbsoutePath());
            logger.info("iOS driver created");
        }
         else if (platformName.equalsIgnoreCase("web")) {
                 plateformSpecificDriver = createDriverForBrowser(browserName);
                 logger.info("Web driver created");
        }
        return plateformSpecificDriver;
    }


    /**
     * this method creates the android driver
     *
     * @param buildPath - path to pick the location of the app
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public synchronized AndroidDriver androidDriver(String buildPath) throws MalformedURLException {
        AndroidDriver driver =null;
        System.out.println(System.getenv("ANDROID_HOME"));
        MutableCapabilities sauceOptions = new MutableCapabilities();
        File app = new File(buildPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("deviceName", platform.getPlatformDeviceName());
        capabilities.setCapability("appium:platformName", platform.getPlatformName());
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
        //capabilities.setCapability("name", methodName.getName());

        if (freshInstall)
            capabilities.setCapability("app", app.getAbsolutePath());

        capabilities.setCapability("chromedriverExecutable", chromeDriverPathMac); //webview handling
        capabilities.setCapability("chromeOptions", new JSONObject().put("w3c", false));
        capabilities.setCapability("autoGrantPermissions", true);
        // added "MobileCapabilityType.FULL_RESET" capability to start app in fresh state (logout).
        // Remove it if not required
        capabilities.setCapability(MobileCapabilityType.NO_RESET, noResetFlag);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        if (executionMode.equalsIgnoreCase("cloud")) {
            sauceOptions.setCapability("username", "oauth-deepak.tiwari-69b6f");
            sauceOptions.setCapability("accessKey", "f1f00935-2049-45f6-b311-e65a533bd16d");
            sauceOptions.setCapability("build", "appium-build-U12N8");
            capabilities.setCapability("sauce:options", sauceOptions);
        }
        logger.info("Desired Capabilities " + new JSONObject(capabilities.asMap()));
        if (executionMode.equalsIgnoreCase("cloud")) {
            URL saucelabUrl = new URL("https://oauth-deepak.tiwari-69b6f:f1f00935-2049-45f6-b311-e65a533bd16d@ondemand.us-west-1.saucelabs.com:443/wd/hub");
            driver = new AndroidDriver(saucelabUrl, capabilities);
        }
        else
            driver = new AndroidDriver(appiumService.getUrl(), capabilities);
        //Implicit waits
       // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        // AndroidDriver driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        return driver;

    }

    /**
     * this method creates the iOS driver
     *
     * @param buildPath- path to pick the location of the app
     * @throws MalformedURLException Thrown to indicate that a malformed URL has occurred.
     */
    public IOSDriver iOSDriver(String buildPath) throws MalformedURLException {
        File app = new File(buildPath);
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, platform.getPlatformDeviceName());
        capabilities.setCapability("platformName", platform.getPlatformName());
        capabilities.setCapability("udid", platform.getPlatformDeviceId());
        capabilities.setCapability("automationName", "XCUITest");
        // capabilities.setCapability("app",platform.getPlatformAppAbsoutePath());
        capabilities.setCapability("bundleId", appPackage);
        capabilities.setCapability("orientation", "PORTRAIT");
        capabilities.setCapability("autoAcceptAlerts", "true");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, "false");
        // capabilities.setCapability(MobileCapabilityType.FULL_RESET, "true");
        capabilities.setCapability("xcodeOrgId", teamId);
        capabilities.setCapability("xcodeSigningId", "Iphone Tester");
        // capabilities.setCapability("appium:usePrebuiltWDA",true);
        //capabilities.setCapability("appium:autoWebView",true);
        capabilities.setCapability("includeSafariInWebviews", true);  //default is false
        capabilities.setCapability("webviewConnectTimeout", "90000"); //default is 0


        logger.info("Desired Capabilities " + new JSONObject(capabilities.asMap()));
        IOSDriver driver = new IOSDriver(appiumService.getUrl(), capabilities);
        return driver;
    }


    public WebDriver createDriverForBrowser(String browserName) throws MalformedURLException {

        WebDriver driver=null;
        logger.info("Creating Driver for "+browserName);
        switch (browserName.toLowerCase()){
            case "chrome":
                if (executionMode.equalsIgnoreCase("cloud"))
                    WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                options.addArguments("--headless");
                driver=executionMode.equalsIgnoreCase("cloud")?new ChromeDriver(options):new ChromeDriver();
                break;
            case "safari":
                driver=new SafariDriver();
                break;
            case "firefox":
                driver=new FirefoxDriver();
                break;
            case "edge":
                driver=new EdgeDriver();
                break;
            default:
                driver=new ChromeDriver();
        }
        driver.get(url);
        driver.manage().window().maximize();
        return driver;
    }
    /**
     * this method starts the appium  server depending on your OS.
     *
     * @param os your machine OS (windows/linux/mac)
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @throws ExecuteException An exception indicating that the executing a subprocesses failed
     * @throws InterruptedException Thrown when a thread is waiting, sleeping,
     * or otherwise occupied, and the thread is interrupted, either before
     * or during the activity.
     */
    private static AppiumDriverLocalService appiumService;
    private static AppiumServiceBuilder builder;

    public void startAppiumServer() throws IOException, InterruptedException {

        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            builder = new AppiumServiceBuilder()
                    .usingAnyFreePort()
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "info");
        } else if (os.contains("mac os x")) {
            builder = new AppiumServiceBuilder()
                    .usingAnyFreePort()
                    .withAppiumJS(new File(appiumJsPathMac))
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                    .withArgument(GeneralServerFlag.BASEPATH, "/wd/hub/");
            //.withArgument(GeneralServerFlag.ALLOW_INSECURE, "chromedriver_autodownload")
        } else if (os.contains("linux")) {
            //Start the appium server
            System.out.println("ANDROID_HOME : ");
            System.getenv("ANDROID_HOME");
            CommandLine command = new CommandLine("/bin/bash");
            command.addArgument("-c");
            command.addArgument("~/.linuxbrew/bin/node");
            command.addArgument("~/.linuxbrew/lib/node_modules/appium/lib/appium.js", true);
            DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
            DefaultExecutor executor = new DefaultExecutor();
            executor.setExitValue(1);
            executor.execute(command, resultHandler);
            Thread.sleep(5000); //Wait for appium server to start

        } else {
            System.out.println(os + "is not supported yet");
        }


        appiumService = builder.build();
        //appiumService=AppiumDriverLocalService.buildService(builder);
        appiumService.start();

        System.out.println("Appium started on " + appiumService.getUrl());
    }

    /**
     * this method stops the appium  server.
     *
     * @throws IOException      Signals that an I/O exception of some sort has occurred.
     * @throws ExecuteException An exception indicating that the executing a subprocesses failed.
     */
    public void stopAppiumServer() throws ExecuteException, IOException {
        if (appiumService != null) {
            appiumService.stop();
            logger.info("Appium server stopped");
        } else {
            logger.severe("Appium server fail to stopped");
        }
    }

}