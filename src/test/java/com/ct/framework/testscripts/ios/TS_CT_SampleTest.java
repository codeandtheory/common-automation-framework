package com.ct.framework.testscripts.ios;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS_CT_SampleTest extends TS_CT_UI_BaseTest {

    @Test
    public void firstFailedTest() throws Exception {

        //input[@class='text-field-wrapper']
        loginScreen.signInLink.click();
        mobileDriverAction.waitForElementVisible(loginScreen.emailShadowRoot,10,null);
        //driver.findElement(By.xpath("(//ymlwebcl-textfield[@label='EMAIL'])[1]")).sendKeys("sdsdsds");
        //WebElement element=loginScreen.findElementInsideShadowRoot(loginScreen.emailShadowRoot,"#email");
        WebElement email = mobileDriverAction.getElementInsideShadowRootById(loginScreen.emailShadowRoot, "email");
        email.sendKeys("dede@Gail.com");
        WebElement password = mobileDriverAction.getElementInsideShadowRootByCssSelector(loginScreen.passwordShadowRoot, "#password");
        password.sendKeys("dede@Gail.com");
        //span[text()='Sign in']
        //span[text()='Sign in']
        WebElement signINButton = driver.findElement(By.xpath("//span[text()='Sign in']"));
        //mobileDriverAction.getElementInsideShadowRootByXpath(loginScreen.signinButtonShadowRoot,"//span[text()='Sign in']");
        signINButton.click();
        this.currentTestCase.info("Executing first Test");
        Assert.assertTrue(false);

    }

    @Test
    public void secondTest() throws Exception {

        logger.info("Test 2 successfully launched");
        Assert.assertTrue(true);
    }
}