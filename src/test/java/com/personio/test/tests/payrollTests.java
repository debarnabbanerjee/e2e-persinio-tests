package com.personio.test.tests;

import com.personio.test.pages.homePage;
import com.personio.test.pages.loginPage;
import com.personio.test.pages.payrollLandingPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.personio.test.util.e2eUtils;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;

public class payrollTests {
    loginPage lPage;
    homePage hmePage;
    payrollLandingPage payrollPage;
    WebDriver driver;
    WebElement element;

    @BeforeTest
    public void setUp() {
        driver = e2eUtils.openBrowser("chrome");
        e2eUtils.navigateToUrl("https://candidate-at-personio-debarnab-banerjee.personio.de");
    }

    @AfterTest
    public void tearDown() {
        e2eUtils.closeBrowser();
    }

    @Test(priority = 1)
    public void loginTest() {
        lPage = new loginPage(driver);
        lPage.doLogin("svetlanatoday@gmail.com", "Munich0!");
        e2eUtils.makeBrowserSleep(2);
        if (e2eUtils.returnElement("//*[contains(text(),'active employees')]").isDisplayed())
            hmePage = new homePage(driver);
        else {
            Assert.fail("Home Page didn't get displayed ");
        }
    }

    @Test(priority = 2, dependsOnMethods = "loginTest")
    public void validateUIElementsOfHomePage() {
        Assert.assertTrue(hmePage.validateUIELementsAreDisplayed());
    }

    @Test(priority = 3, dependsOnMethods = "loginTest")
    public void verifyUIELementsofPayrollLandingPage() {
        e2eUtils.makeBrowserSleep(2);
        if (e2eUtils.returnElement("//*[contains(text(),'active employees')]").isDisplayed() &&
                e2eUtils.returnElement("//*[contains(text(),'currently on leave')]").isDisplayed()) {
            hmePage.openPayRollPage();
            payrollPage = new payrollLandingPage(driver);
        }
        Assert.assertTrue(payrollPage.validatePayrollLandingPageUI());
    }

    @Test(priority = 4)
    public void verifyThatSalaryAmountIsNot0() {
        payrollPage.clickSalaryData();
        ArrayList<String> allSalaries = payrollPage.getAllSalaries();
        Collections.sort(allSalaries);
        Assert.assertTrue(Double.valueOf(allSalaries.get(0)) > 0.00);
    }

    @Test(priority = 5)
    public void verifyThatCurrencyIsNotNull() {
        ArrayList<String> allCurrencies = payrollPage.getAllCurrencies();
        for (int i = 0; i < allCurrencies.size(); i++) {
            Assert.assertTrue(allCurrencies.get(i).equals("EUR"));
        }
    }

    @Test(priority = 6)
    public void verifyThatPayrollCantBeClosedWithoutGeneratingExports() {
        // verify that payroll button is disabled
        Assert.assertTrue(e2eUtils.returnElement("//*[contains(text(),'Close payroll')]/../..").getAttribute("class").contains("disabled"));
    }

    @Test(priority = 7)
    public void verifyThatExportsCanBeGeneratedAndDeleted() {
        String intialCount = payrollPage.noOfExportsGenerated();
        payrollPage.generateExports();
        e2eUtils.waitForElementToHaveText("//button[@id='exported-files-button']//span", String.valueOf(Integer.valueOf(intialCount) + 1));
        Assert.assertTrue(Integer.valueOf(payrollPage.noOfExportsGenerated()) == (Integer.valueOf(intialCount)) + 1);
        payrollPage.deleteGeneratedExports();
        e2eUtils.waitForElementToHaveText("//button[@id='exported-files-button']//span", intialCount);
        Assert.assertTrue(payrollPage.noOfExportsGenerated().equalsIgnoreCase(intialCount));
    }

    @Test(priority = 8, dataProvider = "monthYearDataProvider")
    public void validateThatDifferentMonthAndYearCanBeSelected(String month, String year) {
        payrollPage.selectMonthAndYear(month, year);
        e2eUtils.waitForElementToHaveText(payrollPage.monthInPageLocator, month);
        e2eUtils.waitForElementToHaveText(payrollPage.yearInPageLocator, year);
    }

    @Test(priority = 9)
    public void validatePayrollClosureWarningMessage() {
        String warningMessage = "Warning, you are trying to close a payroll before the end of the billing period. This is possible, but be aware that attendance and absence data will be incomplete.";
        payrollPage.selectMonthAndYear("December", "2022");
        e2eUtils.waitForElementToHaveText(payrollPage.monthInPageLocator, "December");
        e2eUtils.waitForElementToHaveText(payrollPage.yearInPageLocator, "2022");
        payrollPage.closePayroll();
        Assert.assertTrue(driver.findElement((By.xpath("//*[contains(text(),'" + warningMessage + "')]"))).isDisplayed());
    }

    @DataProvider(name = "monthYearDataProvider")
    public Object[][] getDataFromDataprovider() {
        return new Object[][]
                {
                        {"June", "2019"},
                        {"May", "2020"},
                        {"December", "2021"}
                };
    }

}
