package pages;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HotelPage {
	 WebDriver driver;
	 WebDriverWait wait;
	Actions actions;
	String parentWinHandle;
	
	@FindBy(className="chHotels")
	 WebElement hotelIcon;
	
	@FindBy(id="city")
     WebElement destinationInput;
	
	@FindBy(css=".react-autosuggest__input")
	 WebElement searchBox;
	
	@FindBy(xpath="(//p[@class='sr_city']/span[@class='blackText']/b)[2]")
	 WebElement searchItem;
	
	@FindBy(xpath="//label[@for='checkin']")
	 WebElement checkInField;
	
	@FindBy(xpath="//label[@for='checkout']")
	 WebElement checkOutField;
	
	@FindBy(css = ".btnApplyNew")
    WebElement applyBtn;
	
	@FindBy(xpath="//button[@id='hsw_search_button']")
     WebElement searchButton;
	
	@FindBy(xpath="//div[@id='hotelListingContainer']")
     WebElement searchResults;
	
	@FindBy(xpath = "//span[@aria-label='Next Month']")
    WebElement nextMonthBtn;
	
	// Ratings, Amenities
	@FindBy(xpath="//input[@aria-label='4 Star']")
	WebElement star4;
	@FindBy(xpath="//input[@id='Wi-Fi']/following-sibling::label")
	WebElement amenitiesWifi;
//	@FindBy(xpath="//label[contains(text(),'₹ 8000 - ₹ 12000')]")
//	WebElement priceFilterCheckbox;
	
	// sorting
	@FindBy(xpath="//p[@id='hlistpg_hotel_shown_price']")
	WebElement lowestPrice;
	@FindBy(xpath="//span[normalize-space()='(Lowest First)']")
	WebElement lowestPriceSort;
	
	@FindBy(xpath="//button[contains(@class, 'bkngOption__cta') and normalize-space(text())='BOOK THIS NOW']")
	WebElement bookNowButton;
	
    @FindBy(name="fName")
    WebElement guestFname;
    @FindBy(name="lName")
    WebElement guestLname;
    @FindBy(name="email")
    WebElement guestEmail;
    @FindBy(name="mNo")
    WebElement guestpH;
    @FindBy(id="NOT_SELECTED")
    WebElement notSelect;
    @FindBy(xpath="//a[contains(@class, 'btnContinuePayment')]")
    WebElement payBtn;
    @FindBy(xpath="//div[@class='payment__options__tab']")
    WebElement paymentsTab;
    
    @FindBy(xpath="//p[@class='bkngOption__title']")
    WebElement hotelDetailRoomTypes;
	
	public HotelPage(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}
	
	public void closeLogin() {
		try {
	          WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.className("commonModal__close")));
	          close.click();
	      } catch (Exception e) {
	          System.out.println("Modal not found or already closed.");
	      }
	}
	
	public void closeDeal() {
		try {
	          WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@data-testid='cmCloseBtn']")));
	          close.click();
	      } catch (Exception e) {
	          System.out.println("Modal not found or already closed.");
	      }
	}
	
	public void refresh() {
		driver.navigate().refresh();
	}
	
	public void selectHotelOption() {
		wait.until(ExpectedConditions.elementToBeClickable(hotelIcon)).click();
	}
	
	public void enterDestination(String destination) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(destinationInput));
        destinationInput.click();
		wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		searchBox.sendKeys(destination);
		Thread.sleep(2000);
//		searchBox.sendKeys(Keys.ENTER);
		wait.until(ExpectedConditions.elementToBeClickable(searchItem));
		searchItem.click();
    }
	
	public void emptyDestination() {
		wait.until(ExpectedConditions.elementToBeClickable(destinationInput));
        destinationInput.click();
		wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		searchBox.clear();
		searchBox.sendKeys(Keys.ENTER);
	}
	
	public void selectCheckInDate(String date) {
        try {
            wait.until(ExpectedConditions.visibilityOf(checkInField));
            actions.moveToElement(checkInField).click().perform();

            boolean dateFound = false;
            while (!dateFound) {
                try {
                    String xpath = String.format("//div[@aria-label='%s']", date);
                    WebElement checkInDate = driver.findElement(By.xpath(xpath));
                    actions.moveToElement(checkInDate).click().perform();
                    System.out.println("Clicked on Check-in date: " + date);
                    dateFound = true;
                } catch (Exception e) {
                    actions.moveToElement(nextMonthBtn).click().perform();
                }
            }
        } catch (Exception e) {
            System.out.println("Error while selecting check-in date: " + e.getMessage());
        }
    }

    public void selectCheckOutDate(String date) {
        try {
            wait.until(ExpectedConditions.visibilityOf(checkOutField));
            actions.moveToElement(checkOutField).click().perform();

            boolean dateFound = false;
            while (!dateFound) {
                try {
                    String xpath = String.format("//div[@aria-label='%s']", date);
                    WebElement checkOutDate = driver.findElement(By.xpath(xpath));
                    actions.moveToElement(checkOutDate).click().perform();
                    System.out.println("Clicked on Check-out date: " + date);
                    dateFound = true;
                } catch (Exception e) {
                    actions.moveToElement(nextMonthBtn).click().perform();
                }
            }
        } catch (Exception e) {
            System.out.println("Error while selecting check-out date: " + e.getMessage());
        }
    }
	
    public void selectRoomAndGuest() {
        wait.until(ExpectedConditions.visibilityOf(applyBtn)).click();
    }
    
	public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }
	
	public boolean applyStarRatingFilter(int stars) throws InterruptedException {
        WebElement starFilterCheckbox = driver.findElement(By.xpath("//input[@aria-label='" + stars + " Star']/following-sibling::label"));
        wait.until(ExpectedConditions.elementToBeClickable(starFilterCheckbox));
        System.out.println("4 stars clicked!");
        starFilterCheckbox.click();
        Thread.sleep(5000);
        List<WebElement> ratingElements = driver.findElements(By.xpath("//span[@id='hlistpg_hotel_star_rating']"));

        boolean allAreFourStars = true;

        for (WebElement rating : ratingElements) {
            String contentValue = rating.getAttribute("data-content");  // Should be "4"
            if (!"4".equals(contentValue)) {
                allAreFourStars = false;
            }
        }
        return allAreFourStars;
    }
	
	public void applyAmenitiesFilter(String amenity) {
        By amenityFilterCheckbox = By.xpath("//input[@id='" + amenity + "']/following-sibling::label");
        wait.until(ExpectedConditions.elementToBeClickable(amenityFilterCheckbox));
        driver.findElement(amenityFilterCheckbox).click();
    }

    public void applyPriceRangeFilter(int minPrice, int maxPrice) {
        By priceMinInput = By.xpath("//input[@aria-label='min range input']");
        By priceMaxInput = By.xpath("//input[@aria-label='max range input']");
        By applyPriceBtn = By.xpath("//button[@aria-label='Select Range button']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(priceMinInput));
        WebElement priceMin = driver.findElement(priceMinInput);
        priceMin.clear();
        priceMin.sendKeys(String.valueOf(minPrice));

        wait.until(ExpectedConditions.visibilityOfElementLocated(priceMaxInput));
        WebElement priceMax = driver.findElement(priceMaxInput);
        priceMax.clear();
        priceMax.sendKeys(String.valueOf(maxPrice));

        wait.until(ExpectedConditions.elementToBeClickable(applyPriceBtn));
        driver.findElement(applyPriceBtn).click();
    }

    public boolean isSearchResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchResults));
            return searchResults.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    // 2.14
    public void sortResultsBy(String criterion) {
    	if(criterion.equals("Rating")) {
    		WebElement sortUserRating = driver.findElement(By.xpath("//div[contains(@class, 'srtByFltr__list--item')]//span[contains(@class, 'srtByFltr__list--itemTitle') and contains(text(), 'User Rating')]"));
            wait.until(ExpectedConditions.elementToBeClickable(sortUserRating));
            actions.moveToElement(sortUserRating).click(sortUserRating).perform();
    	}
    	else {
    		wait.until(ExpectedConditions.elementToBeClickable(lowestPriceSort));
    		lowestPriceSort.click();
//    		Thread.sleep(2000);
    		wait.until(ExpectedConditions.visibilityOfAllElements(lowestPrice));
    		System.out.println("Lowest Price: " + lowestPrice.getText());
    	}
    }
    
    public void sortByLowestPrice() throws InterruptedException {
		
	}
    
    //2.15
    public void openHotelDetailPage() {
        WebElement hotel = driver.findElement(By.id("hlistpg_hotel_name"));
        parentWinHandle = driver.getWindowHandle();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlistpg_hotel_name")));
        actions.moveToElement(hotel).click(hotel).perform();
        for(String handle : driver.getWindowHandles()) {
        	if(!handle.equals(parentWinHandle)) {
        		driver.switchTo().window(handle);
        		break;
        	}
        }
        wait.until(ExpectedConditions.visibilityOf(hotelDetailRoomTypes));
        String roomType = hotelDetailRoomTypes.getText();
        String roomPrice = driver.findElement(By.xpath("//span[contains(@class, 'font28')]")).getText();
        System.out.println("Room Type: " + roomType);
        System.out.println("Room Price: " + roomPrice);
    }

    // 2.16 done
    public void bookRoom(String firstName, String lastName, String email, String phNo) {
        wait.until(ExpectedConditions.elementToBeClickable(bookNowButton));
        bookNowButton.click();
        wait.until(ExpectedConditions.visibilityOf(guestFname));
        guestFname.sendKeys(firstName);
        guestLname.sendKeys(lastName);
        guestEmail.sendKeys(email);
        guestpH.sendKeys(phNo);
        wait.until(ExpectedConditions.elementToBeClickable(notSelect));
        notSelect.click();
        wait.until(ExpectedConditions.elementToBeClickable(payBtn));
        payBtn.click();
        wait.until(ExpectedConditions.visibilityOf(paymentsTab));
    }
    
    public void switchToParent() {
    	driver.switchTo().window(parentWinHandle);
    }
    
    public void takeProof(String fileName) throws IOException {
    	File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File dest = new File("target/screenshots/" + fileName + ".png");
        // Make sure the directory exists
        dest.getParentFile().mkdirs();
        // Copy screenshot to destination
        FileUtils.copyFile(src, dest);
    }
    
    public void goBack() {
    	driver.navigate().back();
    }
}
