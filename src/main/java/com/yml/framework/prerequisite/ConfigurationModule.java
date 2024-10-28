/**
 * @author Deepak Tiwari
 * /*
 * * Implementation notes.
 * This class is the main Entry point of the application. It has all the dependency and its
 * bindings .It extends Guice AbstractModule class.
 */
package com.yml.framework.prerequisite;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Names;
import com.yml.framework.aut.pojo.User;
import com.yml.framework.common.CommonUtil;
import com.yml.framework.common.Platform;
import com.yml.framework.contracts.adapters.MobileDriverActionAdapter;
import com.yml.framework.prerequisite.providers.PlatformActionProvider;
import com.yml.framework.prerequisite.providers.PlatformDriverProvider;
import com.yml.framework.prerequisite.providers.PlatformProvider;
import com.yml.framework.prerequisite.providers.UserProvider;
import com.yml.framework.reporting.ExtentManager;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConfigurationModule extends AbstractModule {


    private ExtentReports extent;
    private ExtentTest test;
    private String platformName;
    private Map<String, String> testPropertiesMap = new HashMap<String, String>();

    @Inject
    private Logger logger = Logger.getAnonymousLogger();

    @Override
    public void configure() {

        CommonUtil.addFileHandler(logger);
        logger.info("Execution Started");
        try {

            extent = ExtentManager.getInstance();
            //requestStaticInjection(ExtentReports.class);
            Names.bindProperties(binder(), getConfigProperties());
            bind(ExtentReports.class).toInstance(extent);
            bind(PlatformDriverManager.class);
            bind(CommonUtil.class).toInstance(new CommonUtil());
            bind(Platform.class).toProvider(PlatformProvider.class);
            bind(User.class).toProvider(UserProvider.class);
            bind(WebDriver.class).toProvider(PlatformDriverProvider.class).asEagerSingleton();
            if (!testPropertiesMap.get("platformName").equalsIgnoreCase("api"))
                bind(MobileDriverActionAdapter.class).toProvider(PlatformActionProvider.class).asEagerSingleton();
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getStackTrace().toString());
        }

        logger.info("Object bindings are done");
    }

    public Map<String, String> getConfigProperties() throws Exception {

        test = extent.createTest("Reading Test Environment and Platform specific Properties");
        String platformPropMvn = System.getProperty("platform");
        String runModeMvn = System.getProperty("runMode");
        String uiEnvMvn = System.getProperty("env");
        String apiEnvMvn = System.getProperty("apiEnv");
        String testReportTitleMvn = System.getProperty("reportTitle");
        String configFileLocation = "//src//main//resources//config.properties";
        logger.info("Platform from command-line " + platformPropMvn);
        Properties prop = new Properties();
        InputStream input = null;
        InputStream platformProps = null;
        Map<String, String> propertiesMap = new HashMap<String, String>();
        try {
            input = new FileInputStream(CommonUtil.getProjectDir() + configFileLocation);
            // load a properties file

            prop.load(input);
            this.platformName = platformPropMvn == null ? prop.getProperty("AutomationPlatform") : platformPropMvn;
            runModeMvn = runModeMvn == null ? prop.getProperty("ExecutionMode") : runModeMvn;
            apiEnvMvn = apiEnvMvn == null ? prop.getProperty("ApiEnv") : apiEnvMvn;
            uiEnvMvn = uiEnvMvn == null ? prop.getProperty("UiEnv") : uiEnvMvn;
            testReportTitleMvn = testReportTitleMvn == null ? prop.getProperty("ReportTitle") : testReportTitleMvn;
            logger.info("Platform Name is - " + this.platformName);
            test.info("Platform Name is - " + this.platformName);
            testPropertiesMap.put("platformName", this.platformName);
            testPropertiesMap.put("executionMode", runModeMvn == null ? "local" : runModeMvn);
            testPropertiesMap.put("apiEnv", apiEnvMvn == null ? "prod" : apiEnvMvn);
            testPropertiesMap.put("uiEnv", uiEnvMvn == null ? "prod" : uiEnvMvn);
            testPropertiesMap.put("appiumJsPathMac", prop.getProperty("AppiumJsPathMac") == null ? "" : prop.getProperty("AppiumJsPathMac"));
            testPropertiesMap.put("appiumJsPathWindows", prop.getProperty("AppiumJsPathWindows") == null ? "" : prop.getProperty("AppiumJsPathWindows"));
            testPropertiesMap.put("appiumJsPathLinux", prop.getProperty("AppiumJsPathLinux") == null ? "" : prop.getProperty("AppiumJsPathLinux"));
            testPropertiesMap.put("chromeDriverPathMac", prop.getProperty("ChromeDriverExecutablePathMac") == null ? "" : prop.getProperty("ChromeDriverExecutablePathMac"));
            testPropertiesMap.put("username", prop.getProperty("Username") == null ? "" : prop.getProperty("Username"));
            testPropertiesMap.put("password", prop.getProperty("Password") == null ? "" : prop.getProperty("Password"));
            testPropertiesMap.put("teamId", prop.getProperty("IosTeamId") == null ? "" : prop.getProperty("IosTeamId"));
            testPropertiesMap.put("reportTitle", testReportTitleMvn == null ? "SMC Automation Report" : testReportTitleMvn);
            test.pass(CommonUtil.getStringForReport("<b>Reading Config file from " + configFileLocation + "</b>\n" + new JSONObject(testPropertiesMap).toString(1)));
            testPropertiesMap.putAll(getApiEnvProperties(apiEnvMvn, test));
            // test.info("Reading Configs and Platform specific file");
            testPropertiesMap.putAll(getPlatformSpecificProperties(this.platformName, test, uiEnvMvn));
            ExtentManager.setReportTitle(testPropertiesMap.get("reportTitle"));
            extent.setSystemInfo("Execution Mode", runModeMvn);
            extent.setSystemInfo("Automation Platform", platformName);
            extent.setSystemInfo("Browser Name", testPropertiesMap.get("browserName"));
            extent.setSystemInfo("FE/UI Environment", uiEnvMvn);
            extent.setSystemInfo("BE/API Environment", apiEnvMvn);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Reading config file failed..");
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            test.fail(ex.getLocalizedMessage());
            test.fail(ex.getStackTrace().toString());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    test.fail(ex.getLocalizedMessage());
                    test.fail(ex.getStackTrace().toString());
                    ex.printStackTrace();
                }
            }
        }
        test.pass(CommonUtil.getStringForReport("<b>Successfully Read All Test  Environment Properties</b> \n" + new JSONObject(testPropertiesMap).toString(1)));
        return testPropertiesMap;
    }


    public Map<String, String> getPlatformSpecificProperties(String platformName, ExtentTest test, String uiEnv) throws Exception {

        Map<String, String> platformPropertiesMap = new HashMap<String, String>();
        // test.info("Reading Platform Specific Config file .."+platformName);
        logger.info("Reading Platform Specific Config file .." + platformName);
        String fileLocation = null;
        Properties prop = new Properties();
        InputStream input = null;
        InputStream platformProps = null;
        String browserName = null;
        String appUrl = "";
        try {
            switch (platformName.toLowerCase()) {
                case "android":
                    fileLocation = "//src//main//resources//android//android_config.properties";
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + fileLocation);
                    // load a properties file

                    prop.load(platformProps);
                    break;
                case "ios":
                    fileLocation = "//src//main//resources//ios//iOS_config.properties";
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + fileLocation);
                    // load a properties file
                    prop.load(platformProps);
                    break;
                case "web":
                    fileLocation = "//src//main//resources//web//web_config.properties";
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + fileLocation);
                    // load a properties file
                    prop.load(platformProps);
                    appUrl = getApplicationUrl(uiEnv, prop);
                    browserName = System.getProperty("browser") == null ? prop.getProperty("BrowserName") : System.getProperty("browser");
                    break;

                default:
                    break;
            }

            platformPropertiesMap.put("platformVersion", prop.getProperty("PlatformVersion") == null ? "" : prop.getProperty("PlatformVersion"));
            platformPropertiesMap.put("platformAppAbsoutePath", prop.getProperty("AppPath") == null ? "" : prop.getProperty("AppPath"));
            platformPropertiesMap.put("platformDeviceName", prop.getProperty("DeviceName") == null ? "" : prop.getProperty("DeviceName"));
            platformPropertiesMap.put("appPackage", prop.getProperty("AppPackage") == null ? "" : prop.getProperty("AppPackage"));
            platformPropertiesMap.put("appActivity", prop.getProperty("AppActivity") == null ? "" : prop.getProperty("AppActivity"));
            platformPropertiesMap.put("platformDeviceId", prop.getProperty("DeviceId") == null ? "" : prop.getProperty("DeviceId"));
            platformPropertiesMap.put("noReset", prop.getProperty("NoReset") == null ? "true" : prop.getProperty("NoReset"));
            platformPropertiesMap.put("freshInstallApp", prop.getProperty("FreshInstallApp") == null ? "false" : prop.getProperty("FreshInstallApp"));
            platformPropertiesMap.put("browserName", browserName == null ? "chrome" : browserName);
            platformPropertiesMap.put("url", appUrl == null ? "" : appUrl);

            //test.info("Platform Specific File Location <b>"+fileLocation+"</b>");
            // while (testPropertiesMap.values().remove(null));
            logger.info("Reading Platform Specific Properties files successful");
            // extent.setSystemInfo("Test Environment Url",testPropertiesMap.get("envUrl"));
            test.pass(CommonUtil.getStringForReport("Test  Environment is <b>" + platformName + "</b>"));
            logger.info("Test Environment Properties.." + testPropertiesMap);

        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Reading config file failed..");
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            test.fail(ex.getLocalizedMessage());
            test.fail(ex.getStackTrace().toString());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    test.fail(ex.getLocalizedMessage());
                    test.fail(ex.getStackTrace().toString());
                    ex.printStackTrace();
                }
            }
        }
        test.pass(CommonUtil.getStringForReport("<b>Successfully Read Platform Specific Properties file from " + fileLocation + "</b>\n" + new JSONObject(platformPropertiesMap).toString(1)));
        return platformPropertiesMap;
    }


    public Map<String, String> getApiEnvProperties(String apiEnv, ExtentTest testcase) throws Exception {

        Map<String, String> apiPropertiesMap = new HashMap<>();
        String apiFileLocation = "//src//main//resources//api//api.properties";
        testcase.info("Setting Up Backend Environment");
        //testcase.info("Api File Location <b>"+apiFileLocation+"</b>");
        logger.info("Setting Up Backend Environment");
        Properties apiProps = new Properties();
        InputStream input = null;
        InputStream inputStream = null;
        String beApiUrl = null;
        try {


            inputStream = new FileInputStream(CommonUtil.getProjectDir() + apiFileLocation);
            // load a properties file
            apiProps.load(inputStream);
            switch (apiEnv.substring(0, 2).toLowerCase()) {
                case "pr":
                    apiPropertiesMap.put("apiUrl", apiProps.getProperty("prod.host.url"));

                    break;
                case "st":
                    apiPropertiesMap.put("apiUrl", apiProps.getProperty("stage.host.url"));

                    break;
                case "de":
                    apiPropertiesMap.put("apiUrl", apiProps.getProperty("dev.host.url"));

                    break;
                default:
                    apiPropertiesMap.put("apiUrl", apiProps.getProperty("qa.host.url"));

                    break;

            }


            testcase.info("Api Url is <b>" + apiPropertiesMap.get("apiUrl") + "</b>");
            testcase.info("Cognito Service Url is <b>" + apiPropertiesMap.get("cognitoUrl") + "</b>");
            // while (testPropertiesMap.values().remove(null));
            logger.info("successful Set Up API Env");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Reading API file failed..");
            logger.log(Level.SEVERE, ex.getLocalizedMessage());
            testcase.fail(ex.getLocalizedMessage());
            testcase.fail(ex.getStackTrace().toString());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    testcase.fail(ex.getLocalizedMessage());
                    testcase.fail(ex.getStackTrace().toString());
                    ex.printStackTrace();
                }
            }
        }
        test.pass(CommonUtil.getStringForReport("<b> Successfully Read Api Properties file from " + apiFileLocation + "</b>\n" + new JSONObject(apiPropertiesMap).toString(1)));
        return apiPropertiesMap;
    }


    public String getApplicationUrl(String uiEnv, Properties uiProps) {
        String appurl = null;
        switch (uiEnv.substring(0, 2).toLowerCase()) {
            case "pr":
                appurl = uiProps.getProperty("env.prod.url");
                break;
            case "st":
                appurl = uiProps.getProperty("env.stage.url");
                break;
            case "qa":
                appurl = uiProps.getProperty("env.qa.url");
                break;
            case "dev":
                appurl = uiProps.getProperty("env.dev.url");
                break;
            default:
                appurl = uiProps.getProperty("env.prod.url");
        }

        return appurl;
    }
}