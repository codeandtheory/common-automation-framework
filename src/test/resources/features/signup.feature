Feature: SignUp Feature
  As a registered user
  I want to be able to Sign Up to the system
  So that I can Use the System

  Scenario: Successful login in Loreal App
    Given I am on the login page
    When I enter valid credentials and click on login button
    #Then I should be redirected to the UserDashboard

  Scenario: Successful Fetch Values from Api
    Then I should fetch the APIs