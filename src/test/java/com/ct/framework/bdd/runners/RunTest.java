package com.ct.framework.bdd.runners;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "@smoke", features = {"src/test/resources/features"},
        glue = {"com.yml.framework.bdd"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"})
public class RunTest extends AbstractTestNGCucumberTests {





}