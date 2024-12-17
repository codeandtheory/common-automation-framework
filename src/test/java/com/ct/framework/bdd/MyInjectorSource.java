package com.ct.framework.bdd;
import com.aventstack.extentreports.service.ExtentService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Stage;
import com.google.inject.name.Names;
import com.ct.framework.common.Platform;
import com.ct.framework.prerequisite.ConfigurationModule;
import io.cucumber.guice.*;

@ScenarioScoped
public class MyInjectorSource implements InjectorSource {
    @Override
    public Injector getInjector() {
        Injector injector=Guice.createInjector(Stage.PRODUCTION, CucumberModules.createScenarioModule(), new ConfigurationModule());
        Platform platform=injector.getBinding(Platform.class).getProvider().get();
        String browserName=injector.getInstance(Key.get(String.class, Names.named("browserName")));
        String uiEnv=injector.getInstance(Key.get(String.class, Names.named("uiEnv")));
        String apiEnv=injector.getInstance(Key.get(String.class, Names.named("apiEnv")));
        String executionMode=injector.getInstance(Key.get(String.class, Names.named("executionMode")));
        ExtentService.getInstance().setSystemInfo("Execution Mode", executionMode);
        ExtentService.getInstance().setSystemInfo("Platform Name", platform.getPlatformName());
        ExtentService.getInstance().setSystemInfo("Operating System", System.getProperty("os.name"));
        ExtentService.getInstance().setSystemInfo("Browser Name", browserName);
        ExtentService.getInstance().setSystemInfo("FE/UI Env", uiEnv);
        ExtentService.getInstance().setSystemInfo("BE/API Env", apiEnv);
        return injector;


    }
}
