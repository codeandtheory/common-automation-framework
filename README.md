# common-automation-framework
its a common automation framework for web, android , ios and rest apis. The framework can be used both in bdd and hybrid style. The framework supports:
1. Android App Automation using **appium**
2. iOS App Automation using **appium**
3. Web Automation using **selenium**
4. Api Automation using **rest assured**
5. BDD Style using **cucumber** and **testng**
6. Hybrid style using **testng**

 
## Framework Capabilities and Dependencies

1. Framework has Adavanced and configurable Reporting using **extent report**.
2. Framework has Logging using Java inbuilt logging- **java.util.Logger**.
3. Framework uses dependency Management using **google Guice library**.
4. Framework uses **testng** as a test flow management tool.
5. Framework utilizes the common code accross all Ui Platforms.
6. Framework can be used to automate any type of web application, mobile/native applications, hybrid Applications , rest apis.
7. Framework is built on java **11** , appium server version **1.22.3**. For other libraries refer **pom.xml**.


## Framework Structure and Notes

The base package in **src/main** is **com.yml.framework** under that we have following sub-packages.

#### com.yml.framework.aut

#### com.yml.framework.common

#### com.yml.framework.contracts

#### com.yml.framework.prerequisite

#### com.yml.framework.reporting


All the resources including apps,property files,test data should be kept inside **src/main/resources** .Currently under that we have following sub-directories and files.

#### src/main/resources/android

#### src/main/resources/api

#### src/main/resources/ios

#### src/main/resources/test-data

#### src/main/resources/web

#### src/main/resources/config.properties



Then comes src/test. So all the test-scripts, bdd configurations including features files , step definition classes , cucumber config and reporting properties can be found under this. The base package is **com.yml** . under that we have following sub-packages and directories.

#### com.yml.api

#### com.yml.framework



Then We have 3 testng XML suite files for bdd,hybrid and api 


#### testng.xml


#### testng-api.xml


#### testng-cucumber.xml




## Execution

testscripts Can be Executed using following ways.

#### Using Maven

#### Using Testng

#### Using Cucumber









