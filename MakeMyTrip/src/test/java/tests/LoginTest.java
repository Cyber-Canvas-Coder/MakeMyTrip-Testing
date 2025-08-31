package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest {
    LoginPage login;
    WebDriver driver;

    @BeforeClass
    public void openBrowser() {
        driver = DriverSetup.getDriver();
        String url = ConfigReader.getProperty("baseURL");
        driver.get(url);
        login = new LoginPage(driver);  
    }
    @Test(priority = 1)
    public void EnterLoginCredential() throws InterruptedException {
    	login.enterMobile("9838297040");
//    	Thread.sleep(2000);
    	login.clickContinue();
//    	Thread.sleep(2000);
//    	login.enterOTP("1234");
    }
    
    
    @Test(priority = 2)
    public void testClosePopup() {
        login.closeLogin();  
    }

    @AfterClass
    public void destroyBrowser() {
        login.closeBrowser();
    }
}
