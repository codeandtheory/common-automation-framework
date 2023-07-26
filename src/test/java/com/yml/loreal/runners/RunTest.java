package com.yml.loreal.runners;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(tags = "", features = {"src/test/resources/features/login_loreal.feature"}, glue = {"com.yml.loreal.bdd.stepdef"},
        plugin = {})
public class RunTest {

}