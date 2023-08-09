/**
 *
 */
package com.yml.framework.prerequisite;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.name.Names;
import com.yml.framework.common.CommonUtil;
import com.yml.framework.contracts.adapters.MobileDriverActionAdapter;
import com.yml.framework.common.Platform;
import com.yml.framework.aut.pojo.User;
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
    private Logger logger=Logger.getAnonymousLogger();

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
       }
       catch (Exception e){
           e.printStackTrace();
           logger.severe(e.getStackTrace().toString());
       }

        logger.info("Object bindings are done");
    }

    public Map<String, String> getConfigProperties() throws Exception {

        test=extent.createTest("Reading Test Environment and Platform specific Properties");
        String platformPropMvn=System.getProperty("platform");
        String runModeMvn=System.getProperty("runMode");
        String envMvn=System.getProperty("env");
        String apiEnvMvn=System.getProperty("apiEnv");
        String configFileLocation="//src//main//resources//config.properties";
        logger.info("Platform from command-line "+platformPropMvn);
        Properties prop = new Properties();
        InputStream input = null;
        InputStream platformProps =null;
        Map<String, String> propertiesMap = new HashMap<String, String>();
        try {
            input = new FileInputStream(CommonUtil.getProjectDir() + configFileLocation);
            // load a properties file

            prop.load(input);
            this.platformName = platformPropMvn==null?prop.getProperty("AutomationPlatform"):platformPropMvn;
            runModeMvn=runModeMvn == null?prop.getProperty("ExecutionMode"):runModeMvn;
            apiEnvMvn=apiEnvMvn == null?prop.getProperty("ApiEnv"):apiEnvMvn;
            logger.info("Platform Name is - " + this.platformName);
            test.info("Platform Name is - " + this.platformName);
            testPropertiesMap.put("platformName", this.platformName);
            testPropertiesMap.put("executionMode", runModeMvn==null?"local":runModeMvn);
            testPropertiesMap.put("apEnv", apiEnvMvn==null?"prod":apiEnvMvn);
            testPropertiesMap.put("appiumJsPathMac", prop.getProperty("AppiumJsPathMac")==null?"":prop.getProperty("AppiumJsPathMac"));
            testPropertiesMap.put("appiumJsPathWindows", prop.getProperty("AppiumJsPathWindows")==null?"":prop.getProperty("AppiumJsPathWindows"));
            testPropertiesMap.put("appiumJsPathLinux", prop.getProperty("AppiumJsPathLinux")==null?"":prop.getProperty("AppiumJsPathLinux"));
            testPropertiesMap.put("chromeDriverPathMac", prop.getProperty("ChromeDriverExecutablePathMac")==null?"":prop.getProperty("ChromeDriverExecutablePathMac"));
            testPropertiesMap.put("username", prop.getProperty("Username")==null?"":prop.getProperty("Username"));
            testPropertiesMap.put("password", prop.getProperty("Password")==null?"":prop.getProperty("Password"));
            testPropertiesMap.put("teamId", prop.getProperty("IosTeamId")==null?"":prop.getProperty("IosTeamId"));
            testPropertiesMap.put("reportTitle", prop.getProperty("ReportTitle")==null?"":prop.getProperty("ReportTitle"));
            test.pass(CommonUtil.getStringForReport("<b>Reading Config file from "+configFileLocation+"</b>\n"+new JSONObject(testPropertiesMap).toString(1)));
            testPropertiesMap.putAll(getApiEnvProperties(apiEnvMvn,test));
           // test.info("Reading Configs and Platform specific file");
            testPropertiesMap.putAll(getPlatformSpecificProperties(this.platformName,test));
            extent.setSystemInfo("Automation Platform",platformName);
            extent.setSystemInfo("Browser Name",testPropertiesMap.get("browserName"));

        }
        catch (IOException ex) {
            logger.log(Level.SEVERE,"Reading config file failed..");
            logger.log(Level.SEVERE,ex.getLocalizedMessage());
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


    public Map<String, String> getPlatformSpecificProperties(String platformName,ExtentTest test) throws Exception {

         Map<String, String> platformPropertiesMap = new HashMap<String, String>();
       // test.info("Reading Platform Specific Config file .."+platformName);
        logger.info("Reading Platform Specific Config file .."+platformName);
        String fileLocation=null;
        Properties prop = new Properties();
        InputStream input = null;
        InputStream platformProps =null;
        String browserName=null;
        try {
            switch (platformName.toLowerCase()) {
                case "android":
                     fileLocation="//src//main//resources//android//android_config.properties";
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + fileLocation);
                    // load a properties file

                    prop.load(platformProps);
                    break;
                case "ios":
                    fileLocation="//src//main//resources//ios//iOS_config.properties";
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + fileLocation);
                    // load a properties file
                    prop.load(platformProps);
                    break;
                case "web":
                    fileLocation="//src//main//resources//web//web_config.properties";
                    platformProps = new FileInputStream(CommonUtil.getProjectDir() + fileLocation);
                    // load a properties file
                    prop.load(platformProps);
                    browserName=System.getProperty("browser")==null? prop.getProperty("BrowserName") :System.getProperty("browser") ;
                    break;

                default:
                    break;
            }

            platformPropertiesMap.put("platformVersion", prop.getProperty("PlatformVersion")==null?"":prop.getProperty("PlatformVersion"));
            platformPropertiesMap.put("platformAppAbsoutePath", prop.getProperty("AppPath")==null?"":prop.getProperty("AppPath"));
            platformPropertiesMap.put("platformDeviceName", prop.getProperty("DeviceName")==null?"":prop.getProperty("DeviceName"));
            platformPropertiesMap.put("appPackage", prop.getProperty("AppPackage")==null?"":prop.getProperty("AppPackage"));
            platformPropertiesMap.put("appActivity", prop.getProperty("AppActivity")==null?"":prop.getProperty("AppActivity"));
            platformPropertiesMap.put("platformDeviceId", prop.getProperty("DeviceId")==null?"":prop.getProperty("DeviceId"));
            platformPropertiesMap.put("noReset", prop.getProperty("NoReset")==null?"true":prop.getProperty("NoReset"));
            platformPropertiesMap.put("freshInstallApp", prop.getProperty("FreshInstallApp")==null?"false":prop.getProperty("FreshInstallApp"));
            platformPropertiesMap.put("browserName", browserName==null?"chrome":browserName);
            platformPropertiesMap.put("url", prop.getProperty("Url")==null?"":prop.getProperty("Url"));

            //test.info("Platform Specific File Location <b>"+fileLocation+"</b>");
            // while (testPropertiesMap.values().remove(null));
            logger.info("Reading Platform Specific Properties files successful");
            extent.setSystemInfo("Test Environment",platformName);
           // extent.setSystemInfo("Test Environment Url",testPropertiesMap.get("envUrl"));
            test.pass(CommonUtil.getStringForReport("Test  Environment is <b>" + platformName+"</b>"));
            logger.info("Test Environment Properties.."+testPropertiesMap);

        } catch (IOException ex) {
            logger.log(Level.SEVERE,"Reading config file failed..");
            logger.log(Level.SEVERE,ex.getLocalizedMessage());
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
        test.pass(CommonUtil.getStringForReport("<b>Successfully Read Platform Specific Properties file from "+fileLocation+"</b>\n"+new JSONObject(platformPropertiesMap).toString(1)));
        return platformPropertiesMap;
    }


    public Map<String, String> getApiEnvProperties(String apiEnv,ExtentTest testcase) throws Exception {

        Map<String,String> apiPropertiesMap=new HashMap<>();
        String apiFileLocation="//src//main//resources//api//api.properties";
        testcase.info("Setting Up Backend Environment");
        //testcase.info("Api File Location <b>"+apiFileLocation+"</b>");
        logger.info("Setting Up Backend Environment");
        Properties prop = new Properties();
        InputStream input = null;
        InputStream apiProps =null;
        String beApiUrl=null;
        try {


                     apiProps = new FileInputStream(CommonUtil.getProjectDir() + apiFileLocation);
                    // load a properties file
                    prop.load(apiProps);

                    beApiUrl=getApiBaseUrl(apiEnv,prop);

            apiPropertiesMap.put("apiUrl", beApiUrl);
            testcase.info("Api Url is <b>"+beApiUrl+"</b>");
            // while (testPropertiesMap.values().remove(null));
            logger.info("successful Set Up API Env");
        } catch (IOException ex) {
            logger.log(Level.SEVERE,"Reading API file failed..");
            logger.log(Level.SEVERE,ex.getLocalizedMessage());
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
        test.pass(CommonUtil.getStringForReport("<b> Successfully Read Api Properties file from "+apiFileLocation+"</b>\n"+new JSONObject(apiPropertiesMap).toString(1)));
        return apiPropertiesMap;
    }

    public String getApiBaseUrl(String apiEnv,Properties apiProps){
        String apiBaseurl=null;
        switch (apiEnv.substring(0,2).toLowerCase()){
            case "pr":
                apiBaseurl=apiProps.getProperty("API.PROD.HOST.URL");
                break;
            case "st":
                apiBaseurl=apiProps.getProperty("API.STAGE.HOST.URL");
                break;
            case "qa":
                apiBaseurl=apiProps.getProperty("API.QA.HOST.URL");
                break;
            default:
                apiBaseurl=apiProps.getProperty("API.PROD.HOST.URL");
        }

        return apiBaseurl;
    }



}
