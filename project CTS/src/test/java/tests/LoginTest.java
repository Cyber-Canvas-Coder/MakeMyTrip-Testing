package tests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.Login;

public class LoginTest extends DriverSetup {
	Login login;

	@BeforeClass

	public void setup() {  
		login = new Login(driver);  
	}
	@Test
	public void EnterLoginCredential() {
		login.enterMobileNo("9999999999");
		login.clickContinueButton();
	}


	@Test
	public void testClosePopup() {
		login.handleInitialPopup();  
	}


}
