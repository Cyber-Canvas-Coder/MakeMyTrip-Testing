package tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.FlightPage;
import utils.ConfigReader;

public class FlightTest {
	
	FlightPage flight;
	WebDriver driver;
	
	@BeforeClass
    public void openBrowser() {
        driver = DriverSetup.getDriver();
        String url = ConfigReader.getProperty("baseURL");
        driver.get(url);
        flight = new FlightPage(driver);  
        flight.closeLogin();
    }
	@Test(priority = 1)
	public void ads() {
		flight.closeAds();
	}
	
	@Test(priority=2)
	public void OneWayTrip() throws InterruptedException {
		flight.selectFromLocation("Kolkata");
		flight.selectToLocation("Delhi");
		flight.selectDepartureDate("Wed Sep 17 2025");
		flight.clickSearch();
		flight.navigateToHome();
		flight.closeAds();
	}
	@Test(priority = 3)
	public void RoundTrip() throws InterruptedException {
		flight.clickCheckBox();
		flight.selectFromLocation("delhi");
		flight.selectToLocation("Kolkata");
		flight.selectDepartureDate("Wed Sep 17 2025");
		flight.selectReturnDate("Sat Sep 27 2025");
		flight.clickSearch();
		flight.navigateToHome();
		flight.closeAds();
	}
	
	@AfterClass
    public void tearDown() throws InterruptedException {
    	Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
        }
    }
}