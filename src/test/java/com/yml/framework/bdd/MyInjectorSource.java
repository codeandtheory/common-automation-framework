package com.yml.framework.bdd;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.yml.framework.prerequisite.ConfigurationModule;
import io.cucumber.guice.*;

public class MyInjectorSource implements InjectorSource {
    @Override
    public Injector getInjector() {
        return Guice.createInjector(Stage.PRODUCTION, CucumberModules.createScenarioModule(), new ConfigurationModule());
    }
}
