package com.ct.framework.prerequisite.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import com.ct.framework.common.Platform;


public class PlatformProvider implements Provider<Platform> {

    @Inject
    @Named("platformVersion")
    public String platformVersion;


    @Inject
    @Named("platformName")
    public String platformName;

    @Inject
    @Named("platformAppAbsoutePath")
    public String platformAppAbsoutePath;


    @Inject
    @Named("platformDeviceName")
    public String platformDeviceName;

    @Inject
    @Named("platformDeviceId")
    public String platformDeviceId;


    @Inject
    @Named("appPackage")
    public String appPackage;

    @Override
    public Platform get() {
        return new Platform(platformName,platformVersion,platformAppAbsoutePath,platformDeviceName,platformDeviceId,appPackage);
    }

}
