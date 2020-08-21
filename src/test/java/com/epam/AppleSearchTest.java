package com.epam;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class AppleSearchTest {
    private WebDriver webDriver;
    private static final String DRIVER_NAME = "webdriver.chrome.driver";
    private static final String DRIVER_PATH = "src/main/resources/chromedriver.exe";
    private static final String BASE_URL = "https://www.google.com";
    private static final int TIME_WAIT = 10;
    private static final String SEARCH_QUERY = "Apple";

    @BeforeClass
    private void setUp() {
        System.setProperty(DRIVER_NAME, DRIVER_PATH);
        webDriver = new ChromeDriver();
        webDriver.manage()
                .timeouts()
                .implicitlyWait(TIME_WAIT, TimeUnit.SECONDS);
        webDriver.get(BASE_URL);
    }

    @Test
    private void verifyPageTitle() {
        WebElement searchField = webDriver.findElement(By.name("q"));
        searchField.sendKeys(SEARCH_QUERY);
        searchField.submit();
        (new WebDriverWait(webDriver, TIME_WAIT)).until(driver -> webDriver.getTitle().toLowerCase()
                .contains(SEARCH_QUERY.toLowerCase()));
        Assert.assertTrue(webDriver.getTitle().toLowerCase()
                .contains(SEARCH_QUERY.toLowerCase()), "Page title doesn't contain search query.");
    }

    @Test(dependsOnMethods = "verifyPageTitle")
    private void verifyOpenedTab() {
        WebElement imageTabButton = webDriver.findElement(By.xpath("//*[@id=\"hdtb-msb-vis\"]/*[2]/*"));
        imageTabButton.click();
        WebElement firstImageElement = webDriver.findElement(By.xpath("//*[@class=\"rg_i Q4LuWd\"]"));
        Assert.assertTrue(firstImageElement.isDisplayed(),
                "Image element is not displayed, so Image tab is not open.");
    }

    @AfterClass
    private void quitDriver() {
        webDriver.quit();
    }
}
