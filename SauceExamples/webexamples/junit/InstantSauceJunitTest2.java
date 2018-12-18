package webexamples.junit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

public class InstantSauceJunitTest2 {
    private WebDriver driver;

    @Test
    @DisplayName("shouldOpenSafari()")
    public void shouldOpenSafari(TestInfo testInfo) throws MalformedURLException {
        /** Here we set environment variables from your local machine, or IntelliJ run configuration,
         *  and store these values in the variables below. Doing this is a best practice in terms of test execution
         *  and security. If you're not sure how to use env variables, refer to this guide -
         * https://wiki.saucelabs.com/display/DOCS/Best+Practice%3A+Use+Environment+Variables+for+Authentication+Credentials
         * or check junit5-README.md */
        String sauceUserName = System.getenv("SAUCE_USERNAME");
        String sauceAccessKey = System.getenv("SAUCE_ACCESS_KEY");

        /**
         * In this section, we will configure our test to run on some specific
         * browser/os combination in Sauce Labs.*/
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("username", sauceUserName);
        capabilities.setCapability("accessKey", sauceAccessKey);
        capabilities.setCapability("browserName", "Safari");
        capabilities.setCapability("platform", "macOS 10.13");
        capabilities.setCapability("version", "11.1");
        capabilities.setCapability("name", testInfo.getDisplayName());

        /**
         * In this section, we set the Remote WebDriver to run on Sauce Labs, and pass the capabilities.
         * Then we perform some actions on an application.
         * For this script, enter in your application's URL in place of 'https://www.saucedemo.com'. */

        driver = new RemoteWebDriver(new URL("http://ondemand.saucelabs.com:80/wd/hub"), capabilities);
        driver.navigate().to("https://www.saucedemo.com");
        assertTrue(true);
    }

    /**
     * Below we are performing 2 critical actions. Quitting the driver and passing
     * the test result to Sauce Labs user interface.
     */
    @AfterEach
    public void cleanUpAfterTestMethod () {
        ((JavascriptExecutor) driver).executeScript("sauce:job-result=" + (true ? "passed" : "failed"));
        driver.quit();
    }
}