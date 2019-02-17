package com.personio.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.personio.test.util.e2eUtils;

public class homePage {

    WebDriver driver;

    @FindBy(xpath = "//*[contains(text(),'Your workforce at a glance')]")
    WebElement yourWorkForceLabel;

    @FindBy(xpath = "//a[@href='/staff']")
    WebElement employeesSideMenu;

    @FindBy(xpath = "//a[@href='/recruiting']")
    WebElement recreutingSideMenu;

    @FindBy(xpath = "//a[@href='/payroll-full-width']")
    WebElement payrollSideMenu;

    public homePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean validateUIELementsAreDisplayed() {
        try {
            e2eUtils.waitForElementToBePresentOnScreen(yourWorkForceLabel);
            e2eUtils.waitForElementToBePresentOnScreen(employeesSideMenu);
            e2eUtils.waitForElementToBePresentOnScreen(recreutingSideMenu);
            e2eUtils.waitForElementToBePresentOnScreen(payrollSideMenu);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void openPayRollPage(){
        e2eUtils.click(payrollSideMenu);
    }
}
