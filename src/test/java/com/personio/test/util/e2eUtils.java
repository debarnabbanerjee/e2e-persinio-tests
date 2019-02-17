package com.personio.test.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class e2eUtils {

    static private WebDriver driver;
    public static WebDriver openBrowser(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }

    public static void navigateToUrl(String url){
        driver.navigate().to(url);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }

    public static void closeBrowser(){
        if(driver!=null){
            driver.close();
        }
    }

    public static void waitForElementToBePresentOnScreen(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForElementToBeNotPresentOnScreen(WebElement element){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForElementToHaveText(String locator, String text){
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.textToBe(By.xpath(locator),text));
    }

    public static void clickByIndex(String locator, int index){
        waitForElementToBePresentOnScreen(driver.findElement(By.xpath(locator)));
        System.out.println("Iam here now");
        click(driver.findElements(By.xpath(locator)).get(index));
        System.out.println("Iam here again ...");
    }

    public static void click(WebElement element){
        waitForElementToBePresentOnScreen(element);
        element.click();
    }

    public static void writeText(WebElement element, String text){
        waitForElementToBePresentOnScreen(element);
        element.sendKeys(text);
    }

    public static WebElement returnElement(String locator){
        return driver.findElement(By.xpath(locator));
    }

    public static void makeBrowserSleep(long l){
        try{
            Thread.sleep(l*1000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void moveMouseAndClick(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).click().build().perform();
    }
}
