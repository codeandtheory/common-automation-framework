package com.yml.loreal.screens.profile;

import com.google.inject.Inject;
import com.yml.loreal.screens.BaseScreen;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

public class UserProfileScreen extends BaseScreen {


    @Inject
    public UserProfileScreen(WebDriver driver, Logger logger) {
        super(driver, logger);
    }


    @AndroidFindBy(id = "com.saloncentric.app:id/cart")
    @iOSXCUITFindBy(id = "basket")
    public WebElement cartIcon;

    @AndroidFindBy(id = "com.saloncentric.app:id/main_toolbar_title")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='talks']//following-sibling::XCUIElementTypeStaticText")
    public WebElement screenTitleText;


    @AndroidFindBy(id = "com.saloncentric.app:id/cv_user_card")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement userMembershipCard;

    @AndroidFindBy(id = "com.saloncentric.app:id/barcode_iv")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement membershipCardBarcodeIcon;

    @AndroidFindBy(id = "com.saloncentric.app:id/barcode_tv")
    @iOSXCUITFindBy(id = "Forgot Password?")
    public WebElement membershipCardBarcodeText;

    @AndroidFindBy(id = "com.saloncentric.app:id/save_to_wallet")
    @iOSXCUITFindBy(id = "(//XCUIElementTypeButton[@name='Add to Apple Wallet'])")
    public WebElement saveToWalletSection;

    @AndroidFindBy(id = "com.saloncentric.app:id/llFav")
    @iOSXCUITFindBy(id = "Favorites")
    public WebElement favouritesSection;

    @AndroidFindBy(id = "com.saloncentric.app:id/llInbox")
    @iOSXCUITFindBy(id = "Inbox")
    public WebElement inboxSection;


    @AndroidFindBy(id = "com.saloncentric.app:id/llRewards")
    @iOSXCUITFindBy(id = "Rewards")
    public WebElement rewardsSection;

    @AndroidFindBy(id = "com.saloncentric.app:id/llCoupons")
    @iOSXCUITFindBy(id = "Coupons")
    public WebElement couponsSection;

    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tv_profile_normal_item' and @text='Business Tools']")
    @iOSXCUITFindBy(id = "Business Tools")
    public WebElement businessToolsMenuItem;

    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tv_profile_normal_item' and @text='Orders']")
    @iOSXCUITFindBy(id = "Orders")
    public WebElement ordersMenuItem;

    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tv_profile_normal_item' and @text='Settings']")
    @iOSXCUITFindBy(id = "Settings")
    public WebElement settingsMenuItem;

    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tv_profile_normal_item' and @text='Help']")
    @iOSXCUITFindBy(id = "Help")
    public WebElement helpMenuItem;


    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tv_profile_normal_item' and @text='About Us']")
    @iOSXCUITFindBy(id = "About Us")
    public WebElement aboutUsMenuItem;


    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tv_profile_normal_item' and @text='Log Out']")
    @iOSXCUITFindBy(id = "Log Out")
    public WebElement logoutMenuItem;


    @AndroidFindBy(id = "com.saloncentric.app:id/llCoupons")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'SalonCentric' AND type == 'XCUIElementTypeAlert'")
    public WebElement logoutAlert;

    @AndroidFindBy(id = "com.saloncentric.app:id/llCoupons")
    @iOSXCUITFindBy(id = "YES")
    public WebElement logoutAlertYes;

    @AndroidFindBy(id = "com.saloncentric.app:id/llCoupons")
    @iOSXCUITFindBy(id = "NO")
    public WebElement logoutAlertNo;



    //com.saloncentric.app:id/main_toolbar
    // com.saloncentric.app:id/layout_barcode
    //com.saloncentric.app:id/tv_profile_normal_item

}
