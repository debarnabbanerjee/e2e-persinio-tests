package com.personio.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.personio.test.util.e2eUtils;

public class loginPage {

    WebDriver driver;

    @FindBy(id="email")
    WebElement emailField;

    @FindBy(id="password")
    WebElement password;

    @FindBy(xpath="//*[contains(text(),'Einloggen')]")
    WebElement loginBtn;

    public loginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void doLogin(String username, String psswrd){
        e2eUtils.writeText(emailField,username);
        e2eUtils.writeText(password,psswrd);
        e2eUtils.click(loginBtn);
    }
}
