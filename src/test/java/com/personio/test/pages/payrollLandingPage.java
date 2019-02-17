package com.personio.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.personio.test.util.e2eUtils;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class payrollLandingPage {

    WebDriver driver;

    public payrollLandingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "search-term")
    WebElement searchField;

    @FindBy(xpath = "//a[contains(text(),'Personal data')]")
    WebElement personalDataLink;

    @FindBy(xpath = "//a[contains(text(),'Salary data')]")
    WebElement salaryDataLink;

    @FindBy(xpath = "//a[contains(text(),'Absence periods')]")
    WebElement absencePeriodLink;

    @FindBy(xpath = "//*[contains(text(),'Close payroll')]")
    WebElement closePayrollButton;

    @FindBy(xpath = "//*[contains(text(),'Generate export')]")
    WebElement generateExport;

    @FindBy(xpath = "//th[contains(text(),'Last name')]")
    WebElement lastNameColHeader;

    @FindBy(xpath = "//th[contains(text(),'First name')]")
    WebElement firstNameColHeader;

    @FindBy(xpath = "//th[contains(text(),'Email')]")
    WebElement emailColHeader;

    @FindBy(xpath = "//th[contains(text(),'Geburtstag')]")
    WebElement geburtstagColHeader;

    @FindBy(xpath = "//th[contains(text(),'Nationalität')]")
    WebElement nationalityColHeader;

    @FindBy(xpath = "//th[contains(text(),'Familienstand')]")
    WebElement maritalStatusColHeader;

    @FindBy(xpath = "//th[contains(text(),'Office')]")
    WebElement officeLocationColHeader;

    @FindBy(xpath = "//button[@id='exported-files-button']//span")
    WebElement noOfExportsGenerated;

    @FindBy(xpath = "(/html/body//table)[2]//tbody/tr[*]/td[3]")
    WebElement salaryContainer;

    @FindBy(xpath = "//*[contains(text(),'Generated exports')]")
    WebElement generatedExportsLabel;

    @FindBy(xpath = "//*[contains(text(),'Done')]")
    WebElement doneButton;

    @FindBy(xpath = "//i[@class='fal fa-trash']")
    WebElement deleteGeneratedExports ;

    @FindBy(xpath = "//*[contains(text(),'Confirm to overwrite export')]")
    WebElement overwriteExportLabel;

    @FindBy(xpath = "//*[contains(text(),'Please confirm that you want to overwrite the currently open export.')]")
    WebElement overwriteExportWarning;

    @FindBy(className = "//*[contains(text(),'Overwrite')]")
    WebElement overwriteButton;

    @FindBy(xpath = "//i[@class='far fa-calendar-alt']")
    WebElement calendarPicker;

    public String monthInPageLocator ="//div[@class='uhMEN']//strong[1]";

    public String yearInPageLocator ="//div[@class='uhMEN']//strong[2]";

    public boolean validatePayrollLandingPageUI(){
        try{
            e2eUtils.waitForElementToBePresentOnScreen(searchField);
            e2eUtils.waitForElementToBePresentOnScreen(personalDataLink);
            e2eUtils.waitForElementToBePresentOnScreen(salaryDataLink);
            e2eUtils.waitForElementToBePresentOnScreen(absencePeriodLink);
            e2eUtils.waitForElementToBePresentOnScreen(closePayrollButton);
            e2eUtils.waitForElementToBePresentOnScreen(generateExport);
            e2eUtils.waitForElementToBePresentOnScreen(lastNameColHeader);
            e2eUtils.waitForElementToBePresentOnScreen(firstNameColHeader);
            e2eUtils.waitForElementToBePresentOnScreen(emailColHeader);
            e2eUtils.waitForElementToBePresentOnScreen(geburtstagColHeader);
            e2eUtils.waitForElementToBePresentOnScreen(nationalityColHeader);
            e2eUtils.waitForElementToBePresentOnScreen(maritalStatusColHeader);
            e2eUtils.waitForElementToBePresentOnScreen(officeLocationColHeader);
            e2eUtils.waitForElementToBePresentOnScreen(noOfExportsGenerated);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void clickSalaryData(){
        e2eUtils.click(salaryDataLink);
        e2eUtils.waitForElementToBePresentOnScreen(salaryContainer);
    }

    public ArrayList<String> getAllSalaries(){
        ArrayList<String> list = new ArrayList<String>();
        List<WebElement> elements = driver.findElements(By.xpath("(/html/body//table)[2]//tbody/tr[*]/td[3]"));
        for(int i = 0;i<elements.size();i++){
            list.add(elements.get(i).getText().replace("€","").trim());
        }
        return list;
    }

    public ArrayList<String> getAllCurrencies(){
        ArrayList<String> list = new ArrayList<String>();
        List<WebElement> elements = driver.findElements(By.xpath("(/html/body//table)[2]//tbody/tr[*]/td[6]"));
        for(int i = 0;i<elements.size();i++){
            list.add(elements.get(i).getText().trim());
        }
        return list;
    }

    public void generateExports(){
        e2eUtils.makeBrowserSleep(2);
        e2eUtils.click(generateExport);
        e2eUtils.waitForElementToBePresentOnScreen(generatedExportsLabel);
        e2eUtils.click(doneButton);
        e2eUtils.waitForElementToBePresentOnScreen(noOfExportsGenerated);
    }

    public void overwriteExport(){
        e2eUtils.makeBrowserSleep(2);
        e2eUtils.click(generateExport);
        e2eUtils.waitForElementToBePresentOnScreen(overwriteExportLabel);
        e2eUtils.waitForElementToBePresentOnScreen(overwriteExportWarning);
        e2eUtils.click(overwriteButton);
        e2eUtils.waitForElementToBePresentOnScreen(generatedExportsLabel);
        e2eUtils.click(doneButton);
        e2eUtils.waitForElementToBePresentOnScreen(noOfExportsGenerated);
    }

    public void deleteGeneratedExports(){
        e2eUtils.makeBrowserSleep(2);
        e2eUtils.click(noOfExportsGenerated);
        e2eUtils.waitForElementToBePresentOnScreen(deleteGeneratedExports);
        List<WebElement> elements = driver.findElements(By.xpath("//i[@class='fal fa-trash']"));
        System.out.println("size is "  + elements.size());
        e2eUtils.moveMouseAndClick(elements.get(elements.size()-1));
        e2eUtils.click(doneButton);
    }

    public String noOfExportsGenerated(){
        e2eUtils.waitForElementToBePresentOnScreen(noOfExportsGenerated);
        return noOfExportsGenerated.getText().trim();
    }

    public void selectMonthAndYear(String month, String year){
        e2eUtils.click(calendarPicker);
        e2eUtils.click(driver.findElement(By.xpath("//div[@class='datepicker-months']//tbody//td/span[contains(text(),'"+month+"')]")));
    }
}
