package pages;

import java.util.Scanner;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Login {

    WebDriver driver;
    WebDriverWait wait;

    public Login(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @FindBy(xpath="//input[@placeholder='Enter Mobile Number' and @data-cy='userName']")
    WebElement phoneNumber;

    @FindBy(xpath="//button[@class='capText font16']")
    WebElement continueBtn;

    @FindBy(xpath="//input[@id='otp']")
    WebElement otp;

    @FindBy(xpath="//button[@type='submit']//span[text()='Login']/..")
    WebElement loginBtn;
    
    @FindBy(xpath="//span[@class='commonModal__close']") 
    WebElement closeBtn;


    public void enterMobileNo(String mobile) {
        WebElement number = wait.until(ExpectedConditions.visibilityOf(phoneNumber));
        Assert.assertTrue(number.isDisplayed(), "Phone number input box is not visible!");
        number.sendKeys(mobile);    
        
    }
    
    public void clickContinueButton() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(continueBtn));
        Assert.assertTrue(btn.isDisplayed() && btn.isEnabled(), "Continue button is not clickable!");
        btn.click();
        System.out.println(btn.getText());
    }
    
    public void handleInitialPopup() {
        WebElement closePopup = wait.until(ExpectedConditions.elementToBeClickable(closeBtn));
        Assert.assertTrue(closePopup.isDisplayed(), "Close popup button not visible!");
        closePopup.click();
        
    }
    


}
