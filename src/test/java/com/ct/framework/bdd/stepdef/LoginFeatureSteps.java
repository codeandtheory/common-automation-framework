package com.ct.framework.bdd.stepdef;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.ct.framework.aut.rest.ApiEndPoints;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class LoginFeatureSteps extends CommonSteps {


    @Given("I am on the login page")
    public void onLoginPage() {

        super.setScreens();
       // loginScreen.loginButton.click();
        ExtentCucumberAdapter.addTestStepLog("I am on loginPage");
    }

    @When("I enter valid credentials and click on login button")
    public void loginWithValidCredentials() {

        boolean isLoginSuccess=false;
        try {

            //ExtentTest currentTestCase = ExtentCucumberAdapter.getCurrentStep();
            //setScreens(currentTestCase);
            //salonCentricCommons.setCurrentTestCase(currentTestCase);
            super.setScreens();
            //isLoginSuccess=salonCentricCommons.loginToSalonCentric(user);
            //boolean isLoginSuccess=salonCentricCommons.loginToSalonCentric(user);
            } catch (Exception e) {
              isLoginSuccess=false;
              e.printStackTrace();
              ExtentCucumberAdapter.getCurrentStep().fail(e.getMessage());
        }
        Assert.assertTrue(isLoginSuccess,"Login Failed");
    }

    @Then("I should fetch the APIs")
    public void fetchApis() {
        ExtentTest currentTestCase = ExtentCucumberAdapter.getCurrentStep();
        setScreens();
        String list_user_url = super.apiUrl + ApiEndPoints.LIST_USERS;
        Map<String, Object> params = new HashMap<>();
        params.put("page", 2);
        Response response = requestUtil.getRequestWithHeadersAndParam(list_user_url, new HashMap<>(), params, true);
        //currentTestCase.pass(MarkupHelper.createCodeBlock(response.asString(),CodeLanguage.JSON));

    }

}
