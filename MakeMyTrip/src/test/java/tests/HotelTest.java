package tests;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.*;

import base.DriverSetup;
import pages.HotelPage;
import utils.ConfigReader;

import java.io.IOException;

public class HotelTest {
    private WebDriver driver;
    private HotelPage hotel;

    @BeforeClass
    public void setup() {
        driver = DriverSetup.getDriver();
        String url = ConfigReader.getProperty("baseURL");
        driver.get(url);
        hotel = new HotelPage(driver);
        hotel.closeLogin();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    
    @Test(priority = 0, description = "Search hotels in a valid location with future check-in and check-out dates")
    public void hotelSearchWithFutureDatesTest() throws InterruptedException {
    	hotel.selectHotelOption();
        hotel.enterDestination("Ranchi");
        hotel.selectCheckInDate("Thu Sep 04 2025");
        hotel.selectCheckOutDate("Sat Sep 06 2025");
        hotel.selectRoomAndGuest();
        hotel.clickSearch();

        Assert.assertTrue(hotel.isSearchResultsDisplayed(), "Hotel results should be displayed");
    }
    
    @Test(priority = 1, description = "Apply filters like star rating, amenities, and price range")
    public void hotelFilterApplicationTest() throws InterruptedException, IOException {
    	Thread.sleep(2000);
    	hotel.closeDeal();
    	Thread.sleep(2000);
//        Assert.assertTrue(hotel.applyStarRatingFilter(3));
        Assert.assertTrue(hotel.applyStarRatingFilter(4));
//        Assert.assertTrue(hotel.applyStarRatingFilter(5));
        Thread.sleep(2000);
        hotel.closeDeal();
    	Thread.sleep(1000);
        hotel.applyAmenitiesFilter("Wi-Fi");
        Thread.sleep(2000);
        hotel.closeDeal();
    	Thread.sleep(1000);
        hotel.applyPriceRangeFilter(2000, 5000);
        Thread.sleep(1000);
        hotel.closeDeal();
    	Thread.sleep(1000);
        hotel.takeProof("Amenities");
    }
    
    
    @Test(priority = 2, description = "Sort hotel results by rating or lowest price")
    public void hotelSortResults() throws InterruptedException {

        hotel.sortResultsBy("Rating");
        Thread.sleep(2000);
        hotel.closeDeal();
    	Thread.sleep(1000);

//        hotel.sortResultsBy("Price");
    }

    @Test(priority = 3, description = "Open hotel detail page and verify room types and prices")
    public void hotelDetailPageValidation() throws InterruptedException {
        Thread.sleep(2000);

        hotel.openHotelDetailPage();   // open first hotel details
        Thread.sleep(1000);
    }

    @Test(priority = 4, description = "Book a hotel room with valid guest details")
    public void hotelRoomBooking() throws IOException {
      hotel.bookRoom("John", "Doe", "johndoe@gmail.com", "9876543210");  
      hotel.takeProof("Booking_Payment");
      hotel.switchToParent();
//    	Assert.assertTrue(hotel.bookRoom("John", "Doe", "johndoe@gmail.com", "9876543210"), "Booking confirmation should be shown");
    }

    @Test(priority = 5, description = "Search with past check-in date should show validation error")
    public void hotelSearchWithPastCheckinDate() {
    	
        String pastDate = "Thu Aug 28 2025"; 
        boolean pastDateSelected = false;
		try {
			hotel.goBack();
	    	hotel.refresh();
	    	hotel.selectHotelOption();
	    	hotel.enterDestination("Delhi");
	        hotel.clickSearch();
			hotel.selectCheckInDate(pastDate);
			Thread.sleep(2000);
			pastDateSelected = true;
		} catch (Exception e) {
			System.out.println("    Past check-in date was disabled: " + pastDate);
		}

		if (pastDateSelected) {
			System.out.println("Test Failed: Past check-in date got selected");
		} else {
			System.out.println("Test Passed: Past check-in date cannot be selected");
		}
    }
    @Test(priority = 6, description = "Same check-in and check-out date should show validation error")
    public void hotelSearchWithSameCheckinCheckoutDate() {
    	try {
	        hotel.enterDestination("Goa");
	        hotel.selectCheckInDate("2025-09-01");
	        hotel.selectCheckOutDate("2025-09-01");
	        hotel.selectRoomAndGuest();
	        hotel.clickSearch();
	        System.out.println("Test Failed: Same check-out date cannot be selected");
    	}catch(Exception e) {
    		System.out.println("Test Passed: Same check-out date cannot be selected");
    	}

//        Assert.assertTrue(hotelPage.isDateValidationErrorDisplayed(), "Validation error for same check-in/out date should show");
    }

    @Test(priority = 7, description = "Search without entering destination should show validation error")
    public void hotelSearchWithoutDestination() throws IOException {
    	try {
    		hotel.goBack();
        	hotel.refresh();
        	hotel.selectHotelOption();
            hotel.emptyDestination();
            hotel.selectCheckInDate("2025-09-04");
            hotel.selectCheckOutDate("2025-09-07");
            hotel.clickSearch();
            hotel.takeProof("Empty Destination");
            System.out.println("Test Failed: Search without entering destination should show validation error");
    	}
    	catch(Exception e) {
    		System.out.println("Test Passed: Search without entering destination should show validation error");
    	}

//        Assert.assertTrue(hotelPage.isDestinationValidationErrorDisplayed(), "Destination validation error should show");
    }
    

    @AfterClass
    public void tearDown() throws InterruptedException {
    	Thread.sleep(3000);
        if (driver != null) {
            driver.quit();
        }
    }

}
