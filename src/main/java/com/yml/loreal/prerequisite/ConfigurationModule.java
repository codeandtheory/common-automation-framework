/**
 *
 */
package com.yml.loreal.prerequisite;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Names;
import com.yml.loreal.common.CommonUtil;
import com.yml.loreal.contracts.adapters.MobileDriverActionAdapter;
import com.yml.loreal.pojo.Platform;
import com.yml.loreal.pojo.User;
import com.yml.loreal.prerequisite.providers.*;
import com.yml.loreal.reporting.ExtentManager;
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
    private Logger logger;

    @Override
    public void configure() {

        CommonUtil.addFileHandler(logger);
        logger.info("Execution Started");
       try {
           Names.bindProperties(binder(), getConfigProperties());
           extent = ExtentManager.getInstance();
           bind(ExtentReports.class).toInstance(extent);
           Names.bindProperties(binder(), getPlatformSpecificProperties());
           bind(AppiumServerManager.class);
           bind(CommonUtil.class).toInstance(new CommonUtil());
           bind(Platform.class).toProvider(PlatformProvider.class);
           bind(User.class).toProvider(UserProvider.class);
           bind(WebDriver.class).toProvider(PlatformDriverProvider.class).asEagerSingleton();
           bind(MobileDriverActionAdapter.class).toProvider(PlatformActionProvider.class).asEagerSingleton();
       }
       catch (Exception e){
           e.printStackTrace();
           logger.severe(e.getStackTrace().toString());
       }

        logger.info("Object bindings are done");
    }

    public Map<String, String> getConfigProperties() throws Exception {

        String platformPropMvn=System.getProperty("platform");
        String runModeMvn=System.getProperty("runMode");
        logger.info("Platform from command-line "+platformPropMvn);
        Properties prop = new Properties();
        InputStream input = null;
        InputStream platformProps =null;
        Map<String, String> propertiesMap = new HashMap<String, String>();
        try {
            input = new FileInputStream(CommonUtil.getProjectDir() + "//src//main//resources//config.properties");
            // load a properties file

            prop.load(input);
            this.platformName = platformPropMvn==null?prop.getProperty("AutomationPlatform"):platformPropMvn;
            runModeMvn=runModeMvn == null?prop.getProperty("ExecutionMode"):runModeMvn;
            logger.info("Platform Name is - " + this.platformName);
            testPropertiesMap.put("platformName", this.platformName);
            testPropertiesMap.put("executionMode", runModeMvn==null?"local":runModeMvn);
            testPropertiesMap.put("appiumJsPathMac", prop.getProperty("AppiumJsPathMac")==null?"":prop.getProperty("AppiumJsPathMac"));
            testPropertiesMap.put("appiumJsPathWindows", prop.getProperty("AppiumJsPathWindows")==null?"":prop.getProperty("AppiumJsPathWindows"));
            testPropertiesMap.put("appiumJsPathLinux", prop.getProperty("AppiumJsPathLinux")==null?"":prop.getProperty("AppiumJsPathLinux"));
            testPropertiesMap.put("chromeDriverPathMac", prop.getProperty("ChromeDriverExecutablePathMac")==null?"":prop.getProperty("ChromeDriverExecutablePathMac"));
            testPropertiesMap.put("username", prop.getProperty("Username")==null?"":prop.getProperty("Username"));
            testPropertiesMap.put("password", prop.getProperty("Password")==null?"":prop.getProperty("Password"));
            testPropertiesMap.put("teamId", prop.getProperty("IosTeamId")==null?"":prop.getProperty("IosTeamId"));
            testPropertiesMap.put("reportTitle", prop.getProperty("ReportTitle")==null?"":prop.getProperty("ReportTitle"));
            ExtentManager.platformName=platformName;
            ExtentManager.reportTitle=prop.getProperty("ReportTitle");

        }
        catch (IOException ex) {
            logger.log(Level.SEVERE,"Reading config file failed..");
            logger.log(Level.SEVERE,ex.getLocalizedMessage());
            test.error(ex.getLocalizedMessage());
            test.error(ex.getStackTrace().toString());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    test.error(ex.getLocalizedMessage());
                    test.error(ex.getStackTrace().toString());
                    ex.printStackTrace();
                }
            }
        }
        return testPropertiesMap;
    }


    public Map<String, String> getPlatformSpecificProperties() throws Exception {

        this.platformName = testPropertiesMap.get("platformName");
        test=extent.createTest("Reading Plateform Specific Properties File");
        test.info("Reading Config Properties..");
        logger.info("Reading config file...");
        Properties prop = new Properties();
        InputStream input = null;
        InputStream platformProps =null;
        String browserName=null;
        try {
            switch (platformName.toLowerCase()) {
                case "android":
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + "//src//main//resources//android//android_config.properties");
                    // load a properties file
                    prop.load(platformProps);
                    break;
                case "ios":
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + "//src//main//resources//ios//iOS_config.properties");
                    // load a properties file
                    prop.load(platformProps);
                    break;
                case "web":
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + "//src//main//resources//web//web_config.properties");
                    // load a properties file
                    prop.load(platformProps);
                    browserName=System.getProperty("browser")==null? prop.getProperty("BrowserName") :System.getProperty("browser") ;
                    break;
                default:
                    break;
            }

            testPropertiesMap.put("platformVersion", prop.getProperty("PlatformVersion")==null?"":prop.getProperty("PlatformVersion"));
            testPropertiesMap.put("platformAppAbsoutePath", prop.getProperty("AppPath")==null?"":prop.getProperty("AppPath"));
            testPropertiesMap.put("platformDeviceName", prop.getProperty("DeviceName")==null?"":prop.getProperty("DeviceName"));
            testPropertiesMap.put("appPackage", prop.getProperty("AppPackage")==null?"":prop.getProperty("AppPackage"));
            testPropertiesMap.put("appActivity", prop.getProperty("AppActivity")==null?"":prop.getProperty("AppActivity"));
            testPropertiesMap.put("platformDeviceId", prop.getProperty("DeviceId")==null?"":prop.getProperty("DeviceId"));
            testPropertiesMap.put("noReset", prop.getProperty("NoReset")==null?"true":prop.getProperty("NoReset"));
            testPropertiesMap.put("freshInstallApp", prop.getProperty("FreshInstallApp")==null?"false":prop.getProperty("FreshInstallApp"));
            testPropertiesMap.put("browserName", browserName==null?"chrome":browserName);
            testPropertiesMap.put("url", prop.getProperty("Url")==null?"":prop.getProperty("Url"));
           // while (testPropertiesMap.values().remove(null));
            logger.info("Reading Platform Specific Properties files successful");
            System.out.println("*******************App Platform is " + platformName + "  *******************");
            extent.setSystemInfo("Test Environment",platformName);
           // extent.setSystemInfo("Test Environment Url",testPropertiesMap.get("envUrl"));
            test.pass(CommonUtil.getStringForReport("Test  Environment is <b>" + platformName+"</b>"));
            test.pass(CommonUtil.getStringForReport("Test  Environment Properties <b>" + testPropertiesMap+"</b>"));
            logger.info("Test Environment Properties.."+testPropertiesMap);

        } catch (IOException ex) {
            logger.log(Level.SEVERE,"Reading config file failed..");
            logger.log(Level.SEVERE,ex.getLocalizedMessage());
            test.error(ex.getLocalizedMessage());
            test.error(ex.getStackTrace().toString());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    test.error(ex.getLocalizedMessage());
                    test.error(ex.getStackTrace().toString());
                    ex.printStackTrace();
                }
            }
        }
        return testPropertiesMap;
    }



    public Map<String, String> getTestEnvironmentProperties() throws Exception {


        test=extent.createTest("Reading Config File.");
        test.info("Reading Config Properties..");
        logger.info("Reading config file...");
        Properties prop = new Properties();
        InputStream input = null;
        InputStream platformProps =null;
        Map<String, String> propertiesMap = new HashMap<String, String>();
        try {
            input = new FileInputStream(CommonUtil.getProjectDir() + "//src//main//resources//config.properties");
            // load a properties file

            prop.load(input);
            this.platformName = prop.getProperty("AppAutomationPlatform");
            test.info("Platform Name -"+this.platformName);
            propertiesMap.put("platformName", prop.getProperty("AppAutomationPlatform"));
            propertiesMap.put("appiumJsPathMac", prop.getProperty("AppiumJsPathMac"));
            propertiesMap.put("appiumJsPathWindows", prop.getProperty("AppiumJsPathWindows"));
            switch (platformName.toLowerCase()) {
                case "android":
                     platformProps = new FileInputStream(CommonUtil.getProjectDir() + "//src//main//resources//android//android_config.properties");
                    // load a properties file
                    prop.load(platformProps);
                    break;
                case "ios":
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + "//src//main//resources//ios//iOS_config.properties");
                    // load a properties file
                    prop.load(platformProps);
                    break;
                    default:
                    break;
            }
            // get the property value and print it out
            propertiesMap.put("platformVersion", prop.getProperty("PlatformVersion"));
            propertiesMap.put("platformAppAbsoutePath", prop.getProperty("AppPath"));
            propertiesMap.put("platformDeviceName", prop.getProperty("DeviceName"));
            propertiesMap.put("appPackage", prop.getProperty("AppPackage"));
            propertiesMap.put("appActivity", prop.getProperty("AppActivity"));
            propertiesMap.put("platformDeviceId", prop.getProperty("DeviceId"));

            logger.info("Reading config files successful");
            System.out.println("*******************App Platform is " + platformName + "  *******************");
            extent.setSystemInfo("Test Environment",platformName);
            extent.setSystemInfo("Test Environment Url",propertiesMap.get("envUrl"));
            test.pass(CommonUtil.getStringForReport("Test  Environment is <b>" + platformName+"</b>"));
            test.pass(CommonUtil.getStringForReport("Test  Environment Properties <b>" + propertiesMap+"</b>"));
            logger.info("Test Environment Properties.."+propertiesMap);

        } catch (IOException ex) {
            logger.log(Level.SEVERE,"Reading config file failed..");
            logger.log(Level.SEVERE,ex.getLocalizedMessage());
            test.error(ex.getLocalizedMessage());
            test.error(ex.getStackTrace().toString());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException ex) {
                    test.error(ex.getLocalizedMessage());
                    test.error(ex.getStackTrace().toString());
                    ex.printStackTrace();
                }
            }
        }
        return propertiesMap;
    }


}
