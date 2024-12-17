package com.ct.framework.testscripts.ios;

import com.google.inject.Inject;
import com.microsoft.playwright.*;
import org.testng.Assert;

public class TS_Playwright_SampleTest{

    public static void main(String[] args) {

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();
            page.navigate("https://sh-replica.yml.co/");
            System.out.println(page.title());
            //Assert.assertEquals(page.title(),"Code and Theory");
            Locator signIn = page.locator("//a[text()='Sign in']");
            signIn.click();
            Locator email = page.locator("email");
            email.fill("deepak@gmail.com");
        }
    }


}
