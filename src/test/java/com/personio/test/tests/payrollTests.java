package com.personio.test.tests;

import com.personio.test.pages.homePage;
import com.personio.test.pages.loginPage;
import com.personio.test.pages.payrollLandingPage;
import io.qameta.allure.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import com.personio.test.util.e2eUtils;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;

public class payrollTests {
    private loginPage lPage;
    private homePage hmePage;
    private payrollLandingPage payrollPage;
    private WebDriver driver;
    private WebElement element;
    private String initialCount;

    @BeforeTest
    public void setUp() {
        driver = e2eUtils.openBrowser("chrome");
        e2eUtils.navigateToUrl("https://candidate-at-personio-debarnab-banerjee.personio.de");
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        e2eUtils.closeBrowser();
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) throws Exception {
        if (!result.isSuccess())
            e2eUtils.takeSceenshot();
    }

    @Description("Positive Login Test")
    @Test(priority = 1)
    public void loginTest() {
        lPage = new loginPage(driver);
        lPage.doLogin(System.getProperty("email"), System.getProperty("password"));
        e2eUtils.makeBrowserSleep(2);
        if (e2eUtils.returnElement("//*[contains(text(),'active employees')]").isDisplayed())
            hmePage = new homePage(driver);
        else {
            Assert.fail("Home Page didn't get displayed ");
        }
    }

    @Description("Validate the UI Elements present in Home Page")
    @Test(priority = 2, dependsOnMethods = "loginTest")
    public void validateUIElementsOfHomePage() {
        Assert.assertTrue(hmePage.validateUIELementsAreDisplayed());
    }

    @Description("Validate the UI Elements present in Payroll Landing Page")
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

    @Description("Validate the Salary Amounts displayed are not zero")
    @Test(priority = 4)
    public void verifyThatSalaryAmountIsNot0() {
        payrollPage.clickSalaryData();
        ArrayList<String> allSalaries = payrollPage.getAllSalaries();
        Collections.sort(allSalaries);
        Assert.assertTrue(Double.valueOf(allSalaries.get(0)) > 0.00);
    }

    @Description("Validate that currency displayed for salarie is EUR")
    @Test(priority = 5)
    public void verifyThatCurrencyIsNotNull() {
        ArrayList<String> allCurrencies = payrollPage.getAllCurrencies();
        for (int i = 0; i < allCurrencies.size(); i++) {
            Assert.assertTrue(allCurrencies.get(i).equals("EUR"));
        }
    }

    @Description("Validate that payroll can't be closed with out generating exports")
    @Test(priority = 6)
    public void verifyThatPayrollCantBeClosedWithoutGeneratingExports() {
        // verify that payroll button is disabled
        Assert.assertTrue(e2eUtils.returnElement("//*[contains(text(),'Close payroll')]/../..").getAttribute("class").contains("disabled"));
    }

    @Description("Validate that exports can be generated")
    @Test(priority = 7)
    public void verifyThatExportsCanBeGenerated() {
        initialCount = payrollPage.noOfExportsGenerated();
        payrollPage.generateExports();
        e2eUtils.waitForElementToHaveText("//button[@id='exported-files-button']//span", String.valueOf(Integer.valueOf(initialCount) + 1));
        Assert.assertTrue(Integer.valueOf(payrollPage.noOfExportsGenerated()) == (Integer.valueOf(initialCount)) + 1);
    }

    @Description("Validate that exports can be deleted")
    @Test(priority = 8)
    public void verifyThatExportsCanBeDeleted() {
        payrollPage.deleteGeneratedExports();
        e2eUtils.waitForElementToHaveText("//button[@id='exported-files-button']//span", initialCount);
        Assert.assertTrue(payrollPage.noOfExportsGenerated().equalsIgnoreCase(initialCount));
    }

    @Description("Validate the different month and year in past and future can be selected from Payroll Page")
    @Test(priority = 9, dataProvider = "monthYearDataProvider")
    public void validateThatDifferentMonthAndYearCanBeSelected(String month, String year) {
        payrollPage.selectMonthAndYear(month, year);
        e2eUtils.waitForElementToHaveText(payrollPage.monthInPageLocator, month);
        e2eUtils.waitForElementToHaveText(payrollPage.yearInPageLocator, year);
    }

    @Description("Validate the a warning message is displayed when trying to close a payroll in future period")
    @Test(priority = 10)
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
