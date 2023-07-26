package com.yml.loreal.screens;

import com.google.inject.Inject;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.logging.Logger;

public class HomeScreen extends BaseScreen {

    @Inject
    public HomeScreen(WebDriver driver, Logger logger) {
        super(driver, logger);
    }

    @AndroidFindBy(id = "com.saloncentric.app:id/shop_banner_root")
    @iOSXCUITFindBy(id = "topBannerSection")
    public WebElement topBannerSection;


    @AndroidFindBy(id = "com.saloncentric.app:id/rvHomeData")
    @iOSXCUITFindBy(id = "topBannerSection")
    public WebElement homeScreenDataContainer;


    @AndroidFindBy(id = "com.saloncentric.app:id/rvData")
    @iOSXCUITFindBy(id = "topBannerSection")
    public List<WebElement> homeScreenTiles;


    @AndroidFindBy(id = "com.saloncentric.app:id/iv_banner_image")
    @iOSXCUITFindBy(id = "bannerImage")
    public WebElement bannerImage;

    @AndroidFindBy(id = "com.saloncentric.app:id/ll_banner")
    @iOSXCUITFindBy(id = "overlaySectionUnderTopBanner")
    public WebElement overlaySectionUnderTopBanner;

    @AndroidFindBy(id = "com.saloncentric.app:id/indicator")
    @iOSXCUITFindBy(id = "pageIndicatorUnderTopBanner")
    public WebElement pageIndicatorUnderTopBanner;

    @AndroidFindBy(id = "com.saloncentric.app:id/rvData")
    @iOSXCUITFindBy(id = "dataSectionBelowTopBanner")
    public WebElement homeScreenTile;

    @AndroidFindBy(id = "com.saloncentric.app:id/tvMore")
    @iOSXCUITFindBy(id = "dataSectionBelowTopBanner")
    public WebElement seeAllLink;

  @AndroidFindBy(id = "com.saloncentric.app:id/get_direction_text")
    @iOSXCUITFindBy(iOSNsPredicate = "type== 'XCUIElementTypeButton' and name == 'Directions'")
    public WebElement directionBtn;

    @AndroidFindBy(id = "com.saloncentric.app:id/phone_btn")
    @iOSXCUITFindBy(iOSNsPredicate = "type== 'XCUIElementTypeButton' and name == 'Contact'")
    public WebElement contactBtn;

    @AndroidFindBy(id = "com.saloncentric.app:id/profile_tabs")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement profileTabs;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@content-desc='Home']")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'Home'")
    public WebElement homeTab;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@content-desc='Shop']")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'Shop'")
    public WebElement shopTab;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@content-desc='Search']")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'Search'")
    public WebElement searchTab;


    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@content-desc='Learn']")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'Learn'")
    public WebElement learnTab;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout[@content-desc='Profile']")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'Profile'")
    public WebElement profileTab;

    @AndroidFindBy(id = "com.saloncentric.app:id/ivChat")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'chat'")
    public WebElement chatIcon;

    @AndroidFindBy(id = "com.saloncentric.app:id/ivLogo")
    @iOSXCUITFindBy(iOSNsPredicate = "name == 'sc_HomeLogo'")
    public WebElement logoIcon;

    @AndroidFindBy(id = "com.saloncentric.app:id/ivCart")
    @iOSXCUITFindBy(id = "basket White")
    public WebElement cartIcon;

    @AndroidFindBy(id = "com.saloncentric.app:id/ivToolTipChatbot")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement toolTipChat;


    @AndroidFindBy(id = "com.saloncentric.app:id/ivToolTipLearn")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement toolTipLearn;

    @AndroidFindBy(id = "com.saloncentric.app:id/tvTitle")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement homeScreenTilesTitleText;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'You May Like')]")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement productsTileTitleText;


    @AndroidFindBy(id = "com.saloncentric.app:id/iv_product")
    @iOSXCUITFindBy(id = "profileTabs")
    public List<WebElement> productImageListFromTiles;

    @AndroidFindBy(id = "com.saloncentric.app:id/iv_product")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement productImageFromTiles;

    @AndroidFindBy(id = "com.saloncentric.app:id/tv_product_brand")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement productBrandFromTiles;

    @AndroidFindBy(id = "com.saloncentric.app:id/tv_product_name")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement productNameFromTiles;


    @AndroidFindBy(xpath = "//*[@resource-id='android:id/text1' and @text='Call']")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement contactsCallBtn;

    @AndroidFindBy(xpath = "//*[@resource-id='android:id/text1' and @text='Email']")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement contactsEmailBtn;

    @AndroidFindBy(id = "android:id/button2")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement contactsCancelDialogBtn;

    @AndroidFindBy(id = "com.google.android.gm:id/peoplekit_chip")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement mailRecepientTo;


    @AndroidFindBy(id = "com.samsung.android.dialer:id/digits")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement samsungDialerPad;

    @AndroidFindBy(id = "com.samsung.android.dialer:id/dialButtonImage")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement samsungDialBtn;

    @AndroidFindBy(id = "com.saloncentric.app:id/ivEditMyStore")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement updateMyStoreLink;

    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tvTitle' and contains(@text,'Classes Near')]")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement classesNearSectionHeading;

    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tvTitle' and contains(@text,'Classes Near')]//parent::android.widget.LinearLayout//following-sibling::\n" +
            "android.widget.FrameLayout//child::androidx.recyclerview.widget.RecyclerView")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement classesSectionRecyclerView;


    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tvTitle' and contains(@text,'Classes Near')]//parent::android.widget.LinearLayout//following-sibling::\n" +
            "android.widget.FrameLayout//child::androidx.recyclerview.widget.RecyclerView//child::android.widget.TextView[@resource-id='com.saloncentric.app:id/tvTitle']")
    @iOSXCUITFindBy(id = "dataSectionBelowTopBanner")
    public List<WebElement> classNameTextList;

    @AndroidFindBy(id = "com.saloncentric.app:id/tvDate")
    @iOSXCUITFindBy(id = "dataSectionBelowTopBanner")
    public List<WebElement> classDateTextList;

    @AndroidFindBy(id = "com.saloncentric.app:id/tvLocation")
    @iOSXCUITFindBy(id = "dataSectionBelowTopBanner")
    public List<WebElement> classLocationTextList;

    @AndroidFindBy(id = "com.saloncentric.app:id/tvCopyRights")
    @iOSXCUITFindBy(id = "dataSectionBelowTopBanner")
    public WebElement copyRightText;


    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tvTitle' and contains(@text,'Featured')]")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement featuredPostSectionHeading;

    @AndroidFindBy(xpath = "//*[@resource-id='com.saloncentric.app:id/tvTitle' and contains(@text,'Featured')]//parent::android.widget.LinearLayout//following-sibling::\n" +
            "android.widget.FrameLayout//child::androidx.recyclerview.widget.RecyclerView")
    @iOSXCUITFindBy(id = "profileTabs")
    public WebElement featuredPostSectionRecyclerView;


    @AndroidFindBy(id = "com.saloncentric.app:id/tvLookTitle")
    @iOSXCUITFindBy(id = "dataSectionBelowTopBanner")
    public List<WebElement> featuredPostTitleTextList;


    @AndroidFindBy(id = "com.saloncentric.app:id/fb_share_iv")
    @iOSXCUITFindBy(id = "dataSectionBelowTopBanner")
    public WebElement facebookShareBtn;


    @AndroidFindBy(id = "com.saloncentric.app:id/pinterest_share_iv")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='pinterest']")
    public RemoteWebElement pinterestShareBtn;


    @AndroidFindBy(id = "com.saloncentric.app:id/tiktok_share_iv")
    @iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Tiktok']")
    public RemoteWebElement tiktokShareBtn;




    @FindBy(xpath = "//a[contains(@href,'created')]")
    public WebElement pinterestPageCreatedTabOnWebPage;

    @FindBy(xpath = "//a[contains(@href,'twitter')]")
    public WebElement twitterLinkOnWebPage;


    @FindBy(xpath = "//button[@aria-label='Follow']")
    public WebElement followBtnOnPinterestWebPage;


    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Twitter']")
    @AndroidFindBy(xpath = "//android.widget.Button[(@text='Follow')]")
    public RemoteWebElement followBtnPinterestNative;


    @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Twitter']")
    @AndroidFindBy(xpath = "//android.widget.TextView[(@text='Twitter')]")
    public RemoteWebElement tiktokPageTwitterLink;


  @iOSXCUITFindBy(xpath = "//XCUIElementTypeStaticText[@name='Twitter']")
  @AndroidFindBy(xpath = "//android.widget.TextView[(@text='Created')]")
  public RemoteWebElement createdTabOnPinterestNative;



    public static final String pinterestBtnId="com.saloncentric.app:id/pinterest_share_iv";
    public static final String  tikTokBtnId="com.saloncentric.app:id/tiktok_share_iv";


}
