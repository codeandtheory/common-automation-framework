Feature: Login
  As a registered user
  I want to be able to log in to the system
  So that I can access my account and perform actions

  Scenario: Successful login in Loreal App
    Given I am on the login page
    When I enter valid credentials and click on login button
    Then I should be redirected to the UserDashboard