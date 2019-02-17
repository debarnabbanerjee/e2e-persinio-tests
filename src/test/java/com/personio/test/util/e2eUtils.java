package com.personio.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.concurrent.TimeUnit;

public class e2eUtils {

    static private WebDriver driver;
    private static RemoteWebDriver remoteWebDriver;
    private static DesiredCapabilities desiredCapabilities;

    public static WebDriver openBrowser(String browserType) {
        if (System.getProperty("os.name").contains("Windows")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//binary//windows//chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            options.addArguments("window-size=1200x600");
            driver = new ChromeDriver(options);
            return driver;
        } else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//binary//mac//chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("headless");
            options.addArguments("window-size=1200x600");
            driver = new ChromeDriver(options);
            return driver;
        }

        // The below code should work if docker is running and selenium grid port is up and running in
        // local host 4444

//        DesiredCapabilities dr=null;
//        if(browserType.equals("firefox")){
//            dr=DesiredCapabilities.firefox();
//            dr.setBrowserName("firefox");
//            dr.setPlatform(Platform.ANY);
//        }else{
//            dr=DesiredCapabilities.chrome();
//            dr.setBrowserName("chrome");
//            dr.setPlatform(Platform.WINDOWS);
//        }
//        try{
//            remoteWebDriver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dr);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        driver =  remoteWebDriver;
//        return driver;
    }

    public static void refreshPage() {
        driver.navigate().refresh();
    }

    public static void navigateToUrl(String url) {
        driver.navigate().to(url);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    public static void closeBrowser() {
        if (driver != null) {
            driver.close();
        }
    }

    public static void waitForElementToBePresentOnScreen(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementToBeNotPresentOnScreen(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForElementToHaveText(String locator, String text) {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.textToBe(By.xpath(locator), text));
    }

    public static void click(WebElement element) {
        waitForElementToBePresentOnScreen(element);
        element.click();
    }

    public static void writeText(WebElement element, String text) {
        waitForElementToBePresentOnScreen(element);
        element.sendKeys(text);
    }

    public static WebElement returnElement(String locator) {
        return driver.findElement(By.xpath(locator));
    }

    public static void makeBrowserSleep(long l) {
        try {
            Thread.sleep(l * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void moveMouseAndClick(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }
}
