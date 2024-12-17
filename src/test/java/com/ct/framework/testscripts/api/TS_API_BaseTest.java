package com.ct.framework.testscripts.api;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.google.inject.Inject;
import com.ct.framework.common.CommonUtil;
import com.ct.framework.common.Platform;
import com.ct.framework.aut.pojo.User;
import com.ct.framework.prerequisite.ConfigurationModule;
import com.ct.framework.common.RequestUtil;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.xml.XmlTest;

import javax.inject.Named;
import java.lang.reflect.Method;
import java.util.logging.Logger;

@Guice(modules = {ConfigurationModule.class})
public class TS_API_BaseTest {


    @Inject
    @Named("apiUrl")
    public String apiUrl;

    @Inject
    public Logger logger;

    @Inject
    public Platform platform;

    @Inject
    public User user;

    @Inject
    public CommonUtil commonUtil;

    @Inject
    public ExtentReports extent;

    public ExtentTest currentTestCase;

    @Inject
    public RequestUtil requestUtil;

    @BeforeSuite
    public void setUp() throws Exception {

        //add file handler to the logger.
        logger.info("Before Suite..");
        currentTestCase = extent.createTest("Before Suite");
        currentTestCase.info("Before Suite finished");
    }

    @BeforeTest
    public void initializeTest() {

        logger.info("Before Test");
    }


    @BeforeClass
    public void setUrl() {
        currentTestCase = extent.createTest("Before Class");
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeEachTestCase(Method method, XmlTest xmlTest, ITestResult result) {


        String testCaseName=result.getMethod().getDescription();
        if (testCaseName == null || testCaseName.equalsIgnoreCase(""))
            testCaseName=method.getName();
        currentTestCase = extent.createTest(testCaseName, method.getName());
        currentTestCase.assignCategory(method.getDeclaringClass().getSimpleName());
        commonUtil.setCurrentTestInstance(currentTestCase);


    }

    @AfterMethod(alwaysRun = true)
    public void afterEachTestCase(ITestResult result, XmlTest test) {
        int status = result.getStatus();
        try {
            switch (status) {
                case 1:
                    currentTestCase.pass("TEST PASSED");
                    break;
                case 2:
                    currentTestCase.log(Status.FAIL,"FAIlED");
                    currentTestCase.fail(CommonUtil.getStringForReport(result.getThrowable().getMessage()));
                    break;
                case 3:
                default:
                    currentTestCase.log(Status.SKIP,"SKIPPED");
                    currentTestCase.skip("TEST SKIPPED ");
                    currentTestCase.skip(result.getThrowable());
                    break;

            }
        } catch (Exception e) {
            currentTestCase.fail(e);
        }
        currentTestCase.info("<pre>ENDING TESTCASE...." + result.getName() + " </pre> ");

    }


    @AfterSuite(alwaysRun = true)
    public void cleanUp() throws Exception {

        currentTestCase = extent.createTest("After Suite-Clean Up");
        extent.flush();
    }

}
