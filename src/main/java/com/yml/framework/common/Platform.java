package com.yml.framework.common;

public class Platform {

    private String platformName;
    private String platformVersion;
    private String platformAppAbsoutePath;
    private String platformDeviceName;
    private String platformDeviceId;
    private String platformAppPackageName;


    public Platform(){

    }

    public Platform(String platformName, String platformVersion, String platformAppAbsoutePath, String platformDeviceName,String platformDeviceId,String platformAppPackageName ) {
        this.platformName = platformName;
        this.platformVersion = platformVersion;
        this.platformAppAbsoutePath = platformAppAbsoutePath;
        this.platformDeviceName = platformDeviceName;
        this.platformDeviceId=platformDeviceId;
        this.platformAppPackageName=platformAppPackageName;
    }

    public String getPlatformAppPackageName() {
        return platformAppPackageName;
    }

    public void setPlatformAppPackageName(String platformAppPackageName) {
        this.platformAppPackageName = platformAppPackageName;
    }

    public String getPlatformDeviceId() {
        return platformDeviceId;
    }

    public void setPlatformDeviceId(String platformDeviceId) {
        this.platformDeviceId = platformDeviceId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getPlatformAppAbsoutePath() {
        return platformAppAbsoutePath;
    }

    public void setPlatformAppAbsoutePath(String platformAppAbsoutePath) {
        this.platformAppAbsoutePath = platformAppAbsoutePath;
    }

    public String getPlatformDeviceName() {
        return platformDeviceName;
    }

    public void setPlatformDeviceName(String platformDeviceName) {
        this.platformDeviceName = platformDeviceName;
    }

    public String toString(){
       String platformObjDesc="Platform name -"+this.platformName+ ", platform version-"+platformVersion;
       return platformObjDesc;
    }

    public boolean isAndroid(){
       return this.platformName.toLowerCase().startsWith("andr");
    }

    public boolean isIOS(){
        return this.platformName.toLowerCase().startsWith("ios");
    }

    public boolean isWeb(){
        return this.platformName.toLowerCase().startsWith("web");}

    public boolean isRestApis(){
        return this.platformName.toLowerCase().startsWith("rest")||this.platformName.toLowerCase().startsWith("api");}

}
