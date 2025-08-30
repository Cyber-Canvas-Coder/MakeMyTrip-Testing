package tests;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.Flight;
import pages.Login;

public class FlightTest extends DriverSetup {
	
	Flight flight;
	@BeforeClass
    
    public void setup() {   // ✅ method takes parameters
        
        flight = new Flight(driver);
    }
	

	@Test(priority = 1)
	public void ads() {
		flight.closeAds();
	}
	
	@Test(priority=2)
	public void OneWayTrip() throws InterruptedException {
		flight.selectFromLocation("Kolkata");
		flight.selectToLocation("Delhi");
		flight.selectDepartureDate("Wed Sep 10 2025");
		flight.clickSearch();
		flight.navigateToHome();
		flight.closeAds();
	}
	@Test(priority = 3)
	public void RoundTrip() throws InterruptedException {
		flight.clickCheckBox();
		flight.selectFromLocation("delhi");
		flight.selectToLocation("Kolkata");
		flight.selectDepartureDate("Wed Sep 10 2025");
		flight.selectReturnDate("Sat Sep 13 2025");
		flight.clickSearch();
		flight.navigateToHome();
		flight.closeAds();
	}
	
	
}
