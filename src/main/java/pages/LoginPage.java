package pages;


import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
	 WebDriver driver;
	 WebDriverWait wait;
	 Actions actions;
	
	@FindBy(xpath="//input[@placeholder='Enter Mobile Number' and @data-cy='userName']")
	 WebElement mobileInput;
	
	@FindBy(xpath="//button[@data-cy='continueBtn']")
	 WebElement continueButton;
	
	@FindBy(xpath="//input[@id='otp']")
	 WebElement otpInput;

	@FindBy(xpath="//button[@type='submit']//span[text()='Login']/..")
    WebElement loginBtn;
    
    @FindBy(xpath="//span[@class='commonModal__close']") 
    WebElement closeBtn;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(3));
		PageFactory.initElements(driver, this);
	}
	
	public void enterMobile(String mobile) {
		wait.until(ExpectedConditions.visibilityOf(mobileInput));
		mobileInput.clear();
		mobileInput.sendKeys(mobile);
	}
	
	public void clickContinue() {
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		actions.moveToElement(continueButton).click(continueButton).perform();
//        continueButton.click();
    }
	
	public void enterOTP(String otp) {
		wait.until(ExpectedConditions.visibilityOf(otpInput));
        otpInput.sendKeys(otp);
    }
	
	
	public boolean isMobileInputDisplayed() {
		try {
            wait.until(ExpectedConditions.visibilityOf(mobileInput));
            return mobileInput.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
	
	public void isContinueButtonDisplayed() {
		try {
            wait.until(ExpectedConditions.elementToBeClickable(continueButton));
            System.out.println(continueButton.isEnabled());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
	
	public void closeLogin() {
		try {
	          WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.className("commonModal__close")));
	          close.click();
	      } catch (Exception e) {
	          System.out.println("Modal not found or already closed.");
	      }
	}
	
	public void closeBrowser() {
		driver.close();
	}
}
