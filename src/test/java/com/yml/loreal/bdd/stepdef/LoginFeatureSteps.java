package com.yml.loreal.bdd.stepdef;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginFeatureSteps extends CommonSteps {

//    Scenario: Successful login in Loreal App
//    Given I am on the login page
//    When I enter valid credentials and click on login button
//    Then I should be redirected to the UserDashboard


    @Given("I am on the login page")
    public void onLoginPage(){
        currentTestCase.info("user is on the login page");
    }

    @When("I enter valid credentials and click on login button")
    public void loginWithValidCredentials(){

       try {
           boolean isLoginSuccess=salonCentricCommons.loginToSalonCentric(user);
           Assert.assertTrue(isLoginSuccess,"Login Failed");
       }
       catch (Exception e){
           currentTestCase.fail(e.getMessage());
           currentTestCase.error(e);
       }

    }

}
