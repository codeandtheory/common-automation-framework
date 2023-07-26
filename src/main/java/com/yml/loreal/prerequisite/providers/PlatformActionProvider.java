package com.yml.loreal.prerequisite.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.yml.loreal.contracts.adapters.MobileDriverActionAdapter;
import com.yml.loreal.contracts.impl.android.AndroidDriverActions;
import com.yml.loreal.contracts.impl.ios.IOSDriverActions;
import com.yml.loreal.contracts.impl.web.WebDriverActions;
import com.yml.loreal.pojo.Platform;
import org.openqa.selenium.WebDriver;

import java.util.logging.Logger;

public class PlatformActionProvider implements Provider<MobileDriverActionAdapter>{

        @Inject
        Platform platform;

        @Inject
        private Logger logger;

        @Inject
        WebDriver driver;

        @Override
        public MobileDriverActionAdapter get() {

            logger.info("Instantiating Platform Specifc Action Class for "+platform.getPlatformName());
            MobileDriverActionAdapter platformSpecificActionClass = null;
        try {
            switch (platform.getPlatformName().toLowerCase()){
                case "android":
                    platformSpecificActionClass=new AndroidDriverActions(driver,logger);
                    break;
                case "ios":
                    platformSpecificActionClass=new IOSDriverActions(driver,logger);
                    break;
                case "web":
                    platformSpecificActionClass=new WebDriverActions(driver,logger);
                    break;
            }
            platformSpecificActionClass.setPlatform(platform);
        } catch (Exception e) {
            e.printStackTrace();
            logger.severe(e.getMessage());
        }
        logger.info("Successfully Instantiated Platform Specifc Action Class for "+platform.getPlatformName());
        return platformSpecificActionClass;
    }

    }
