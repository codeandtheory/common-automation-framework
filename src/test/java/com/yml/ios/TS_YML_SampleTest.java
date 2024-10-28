package com.yml.ios;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TS_YML_SampleTest extends TS_YML_UI_BaseTest {

    @Test
    public void firstFailedTest() throws Exception {

        
        this.currentTestCase.info("Executing first Test");
        Assert.assertTrue(false);

    }

    @Test
    public void secondTest() throws Exception {

        logger.info("Test 2 successfully launched");
        Assert.assertTrue(true);
    }
}