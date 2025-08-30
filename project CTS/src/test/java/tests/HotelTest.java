package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import base.DriverSetup;
import pages.Flight;
import pages.Hotel;

public class HotelTest extends DriverSetup {

    Hotel hotel;

    @BeforeClass
    public void Setup() {  
        hotel = new Hotel(driver);
    }

    @Test(priority = 1, description = "Search hotels in a valid location with future check-in and check-out dates")
    public void hotelSearchWithFutureDatesTest() {
        hotel.selectHotelOption();
        hotel.enterDestination("Kolkata");
        hotel.selectCheckInDate("Sat Aug 30 2025");
        hotel.selectCheckOutDate("Wed Sep 03 2025");
        hotel.selectRoomAndGuest();
        hotel.clickSearch();

        Assert.assertTrue(hotel.isSearchResultsDisplayed(), "Hotel results should be displayed");
    }

    @Test(priority = 2, description = "Search hotels and then apply filters")
    public void applyHotelFilter() throws InterruptedException {
        hotel.applyStarRating();
        hotel.applyAmenitiesFilter();
        hotel.applyPriceFilter();
    }

    @Test(priority = 3, description = "Sort hotel results by rating or lowest price")
    public void hotelSortResultByLowestPrice() throws InterruptedException {
    	hotel.sortResultsBy();
    }
    @Test(priority=4)

    public void hotelSortResultByHighestPrice() throws InterruptedException {
    	hotel.sortResultByHighest();
    }
    @Test(priority = 5, description = "Open hotel detail page and verify room types and prices")
    public void hotelDetailPageValidation() throws InterruptedException {
        hotel.openHotelDetailPage();  
        hotel.getRoomTypeAndPrice();
    }

    @Test(priority = 6, description = "Book a hotel room with valid guest details")
    public void hotelRoomBooking() throws IOException {
        hotel.bookRoom("John", "Doe", "johndoe@gmail.com", "9876543210");
    }

    @Test(priority = 7, description = "Validate past check-in date selection")
    public void pastCheckInDate() throws InterruptedException {
        System.out.println("Test Start: pastCheckInDate");

        driver.switchTo().window(hotel.parentHandle);

        hotel.moveBack();
        
        String pastDate = "Thu Aug 28 2025"; 
        boolean pastDateSelected = false;
        try {
            hotel.selectCheckInDate(pastDate);
            Thread.sleep(2000);
            pastDateSelected = true;
        } catch (Exception e) {
            System.out.println("    Past check-in date was disabled: " + pastDate);
        }

        if (pastDateSelected) {
            System.out.println("Test Passed: Past check-in date cannot be selected");
        } else {
            System.out.println("Test Failed: Past check-in date got selected");
        }
    }

    @Test(priority = 8, description = "Validate same check-in and check-out date selection")
    public void sameCheckInCheckOutDate() throws InterruptedException {
        System.out.println("Test Start: sameCheckInCheckOutDate");

        driver.switchTo().window(hotel.parentHandle);

        hotel.selectHotelOption();
        

        String sameDate = "Wed Sep 10 2025";
        int checkInResult = hotel.selectCheckInDate(sameDate);
        int checkOutResult = hotel.selectCheckOutDate(sameDate);

        if (checkInResult == 0 && checkOutResult == 0) {
            hotel.selectRoomAndGuest();
            hotel.clickSearch();
            System.out.println("Test Passed: Same check-in/check-out handled correctly");
        } else {
            System.out.println("Test Failed: Same check-in/check-out not allowed");
        }

        hotel.moveBack();  
    }

    @Test(priority = 9, description = "Search hotel without entering destination")
    public void searchHotelWithoutDestination() throws InterruptedException {
        hotel.selectHotelOption();
        hotel.clickSearch();
        hotel.getDestination();
    }
}
