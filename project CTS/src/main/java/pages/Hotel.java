package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Hotel {
	WebDriver driver;
	Actions actions;
	WebDriverWait wait;
	JavascriptExecutor jse;
	public String parentHandle;
	public Hotel(WebDriver driver) {
		this.driver = driver;
		this.actions = new Actions(driver);
		jse = (JavascriptExecutor)driver;
		parentHandle = driver.getWindowHandle();
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);

	}

	// ------------------- Locators -------------------
	@FindBy(className="chHotels")
	private WebElement hotelIcon;

	@FindBy(id="city")
	private WebElement destinationInput;

	@FindBy(css=".react-autosuggest__input")
	private WebElement searchBox;

	@FindBy(xpath="//label[@for='checkin']")
	private WebElement checkInField;

	@FindBy(xpath="//label[@for='checkout']")
	private WebElement checkOutField;

	@FindBy(css = ".btnApplyNew")
	WebElement applyBtn;

	@FindBy(xpath="//button[@id='hsw_search_button']")
	private WebElement searchButton;

	@FindBy(xpath="//div[@id='hotelListingContainer']")
	private WebElement searchResults;

	@FindBy(xpath = "//span[@aria-label='Next Month']")
	WebElement nextMonthBtn;

	@FindBy(xpath="//input[@aria-label='4 Star']")
	WebElement star4;

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


	@FindBy(xpath = "//div[@id='USER_RATING_MMT_BRAND']//li[1]//span[1]//label[1]")
	WebElement userRatingCheckbox;
	@FindBy(id="NOT_SELECTED")
	WebElement notSelect;

	@FindBy(xpath="//a[contains(@class, 'btnContinuePayment')]")
	WebElement payBtn;
	@FindBy(xpath="//div[@class='payment__options__tab']")
	WebElement paymentsTab;

	@FindBy(xpath="//p[@class='bkngOption__title']")
	WebElement hotelDetailRoomTypes;
	// sorting
	@FindBy(xpath="//p[@id='hlistpg_hotel_shown_price']")
	WebElement lowestPrice;
	@FindBy(xpath="//span[normalize-space()='(Lowest First)']")
	WebElement lowestPriceSort;
	
	@FindBy(xpath="//span[normalize-space()='(Highest First)'][1]") WebElement highestPriceSort;


	public void closeLogin() {
		try {
			WebElement close = wait.until(ExpectedConditions.elementToBeClickable(By.className("commonModal__close")));
			close.click();
		} catch (Exception e) {
			System.out.println("Modal not found or already closed.");
		}
	}

	public void selectHotelOption() {

		wait.until(ExpectedConditions.elementToBeClickable(hotelIcon)).click();

	}

	public void enterDestination(String destination) {
		wait.until(ExpectedConditions.elementToBeClickable(destinationInput));
		destinationInput.click();
		wait.until(ExpectedConditions.elementToBeClickable(searchBox));
		searchBox.sendKeys(destination);
	}

	public int selectCheckInDate(String date) {
		try {
			wait.until(ExpectedConditions.visibilityOf(checkInField));
			actions.moveToElement(checkInField).click().perform();

			boolean dateFound = false;
			while (!dateFound) {
				try {
					String xpath = String.format("//div[@aria-label='%s']", date);
					WebElement checkInDate = driver.findElement(By.xpath(xpath));


					if (checkInDate.getAttribute("class").contains("disabled")) {
						System.out.println("Check-in date is disabled: " + date);
						return -1;
					}

					actions.moveToElement(checkInDate).click().perform();
					System.out.println("Clicked on Check-in date: " + date);
					dateFound = true;
					return 0;
				} catch (Exception e) {
					actions.moveToElement(nextMonthBtn).click().perform();
				}
			}
		} catch (Exception e) {
			System.out.println("Error while selecting check-in date: " + e.getMessage());
		}
		return -1;
	}

	public int selectCheckOutDate(String date) {
		try {
			wait.until(ExpectedConditions.visibilityOf(checkOutField));
			actions.moveToElement(checkOutField).click().perform();

			boolean dateFound = false;
			while (!dateFound) {
				try {
					String xpath = String.format("//div[@aria-label='%s']", date);
					WebElement checkOutDate = driver.findElement(By.xpath(xpath));

					if (checkOutDate.getAttribute("class").contains("disabled")) {
						System.out.println("Check-out date is disabled: " + date);
						return -1;
					}

					actions.moveToElement(checkOutDate).click().perform();
					System.out.println("Clicked on Check-out date: " + date);
					dateFound = true;
					return 0;
				} catch (Exception e) {
					actions.moveToElement(nextMonthBtn).click().perform();
				}
			}
		} catch (Exception e) {
			System.out.println("Error while selecting check-out date: " + e.getMessage());
		}
		return -1;
	}

	public void selectRoomAndGuest() {
		wait.until(ExpectedConditions.visibilityOf(applyBtn)).click();
	}

	public void clickSearch() {
		wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		searchButton.click();
	}


	public boolean applyStarRatingFilter(int stars) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(userRatingCheckbox)).click();
		System.out.println("User Rating checkbox clicked");
		Thread.sleep(5000);
		List<WebElement> ratingElements = driver.findElements(By.xpath("//span[@id='hlistpg_hotel_star_rating']"));

		boolean allAreFourStars = true;

		for (WebElement rating : ratingElements) {
			String contentValue = rating.getAttribute("data-content");  
			if (!"4".equals(contentValue)) {
				allAreFourStars = false;
			}
		}
		return allAreFourStars;
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
	public void sortResultsBy() throws InterruptedException {
	   wait.until(ExpectedConditions.visibilityOf(lowestPriceSort)).click();
	   Thread.sleep(2000);
	}
	public void sortResultByHighest() throws InterruptedException {
		wait.until(ExpectedConditions.visibilityOf(highestPriceSort)).click();
		Thread.sleep(2000);
	}


	//2.15
	public void openHotelDetailPage() {
		WebElement hotel = driver.findElement(By.id("hlistpg_hotel_name"));
		parentHandle = driver.getWindowHandle();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("hlistpg_hotel_name")));

		actions.moveToElement(hotel).click(hotel).perform();

		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(parentHandle)) {
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
	public void bookRoom(String firstName, String lastName, String email, String phNo) throws IOException {
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


		By paymentsTabLocator = By.xpath("//div[@class='payment__options__tab']");
		wait.until(ExpectedConditions.refreshed(
				ExpectedConditions.visibilityOfElementLocated(paymentsTabLocator)
				));

		// Take screenshot
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File dest = new File("target/screenshots/screenshot.png");
		dest.getParentFile().mkdirs();
		FileUtils.copyFile(src, dest);

		driver.switchTo().window(parentHandle);
	}

	public void moveBack() throws InterruptedException {
		driver.navigate().back();
		Thread.sleep(4000);

	}


	// ⭐ Added Methods

	// Print the destination city name from the search box
	public void getDestination() throws InterruptedException {
		try {
			String destination = searchBox.getAttribute("value");
			System.out.println("Destination selected: " + destination);
		} catch (Exception e) {
			System.out.println("No destination found in search box.");
		}
	}

	// Apply 4-star filter (with JS click fallback)
	public void applyStarRating() throws InterruptedException {
		try {
			By star4Locator = By.xpath("//input[@aria-label='4 Star']");
			WebElement starFilter = wait.until(
					ExpectedConditions.presenceOfElementLocated(star4Locator)
					);

			try {
				// Try normal Selenium click
				wait.until(ExpectedConditions.elementToBeClickable(starFilter)).click();
				System.out.println("Applied 4-star rating filter (normal click)");
			} catch (Exception e) {
				// If normal click fails, use JS click
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", starFilter);
				System.out.println("Applied 4-star rating filter (JS click)");
			}

			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println("❌ Failed to apply 4-star rating filter: " + e.getMessage());
		}
	}


	// Apply swimming pool amenity filter
	public void applyAmenitiesFilter() throws InterruptedException {
		WebElement poolAmenity = driver.findElement(By.xpath("//span[contains(text(), 'Pool')]"));
		wait.until(ExpectedConditions.elementToBeClickable(poolAmenity)).click();
		System.out.println("Applied swimming pool amenity filter");
		Thread.sleep(2000);
	}

	// Apply Price (Lowest First) filter safely
	public void applyPriceFilter() throws InterruptedException {
		try {

			By dropdownLocator = By.xpath("//button[contains(@aria-label,'Sort by')]");
			WebElement sortDropdown = wait.until(
					ExpectedConditions.elementToBeClickable(dropdownLocator)
					);
			sortDropdown.click();
			System.out.println("Opened sort dropdown");


			By priceOptionLocator = By.xpath("//span[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'price (lowest first)')]");
			WebElement priceOption = wait.until(
					ExpectedConditions.visibilityOfElementLocated(priceOptionLocator)
					);


			try {
				priceOption.click();
				System.out.println("Applied Price (Lowest First) filter (normal click)");
			} catch (Exception e) {
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", priceOption);
				System.out.println("Applied Price (Lowest First) filter (JS click)");
			}

			Thread.sleep(2000);
		} catch (Exception e) {
			System.out.println(" Failed to apply Price filter: " + e.getMessage());
		}
	}




	// Get room type and price from hotel details
	public void getRoomTypeAndPrice() throws InterruptedException {
		try {
			wait.until(ExpectedConditions.visibilityOf(hotelDetailRoomTypes));
			String roomType = hotelDetailRoomTypes.getText();
			String roomPrice = driver.findElement(By.xpath("//span[contains(@class, 'font28')]")).getText();
			System.out.println("Room Type: " + roomType);
			System.out.println("Room Price: " + roomPrice);
		} catch (Exception e) {
			System.out.println("Unable to fetch room type and price.");
		}
	}






}


