package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v124.dom.DOM.PerformSearchResponse;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Flight {
	WebDriver driver;
	WebDriverWait wait;
	JavascriptExecutor jse;
	public Flight(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		 jse = (JavascriptExecutor) driver;
	}

	@FindBy(xpath ="//input[@id='fromCity']") WebElement fromCity;
	@FindBy(xpath ="//span[@class='styles__Close-sc-1bytt3z-0 kezeYI']") WebElement ads;
	@FindBy(xpath ="//input[@class='react-autosuggest__input react-autosuggest__input--open']") WebElement fromCitySearch;
	@FindBy(xpath="//li[@id='react-autowhatever-1-section-0-item-1']") WebElement fromCityOption;
	@FindBy(xpath ="//input[@id='toCity']") WebElement toCity;
	@FindBy(xpath ="//input[@class='react-autosuggest__input react-autosuggest__input--open']") WebElement toCitySearch;
	@FindBy(xpath="//li[@id='react-autowhatever-1-section-0-item-1']") WebElement toCityOption;
	@FindBy(xpath="//div[@class='DayPicker']") WebElement DepartureDayPicker;
	@FindBy(xpath="//span[text()='Departure']") WebElement departure;
	@FindBy(xpath = "//div[contains(@class,'DayPicker-Month')]") WebElement calender;
	@FindBy(xpath = "//a[contains(@class,'primaryBtn') and text()='Search']") WebElement searchBtn;
	@FindBy(xpath="//li[@data-cy='roundTrip']//span[@class='tabsCircle appendRight5']") WebElement roundTripCheckBox;
	@FindBy(xpath="//span[text()='Return']") WebElement returnDate;
	
	public void closeAds() {
		WebElement adsClose = wait.until(ExpectedConditions.visibilityOf(ads));
		Assert.assertTrue(adsClose.isDisplayed(), "Ad close button is not visible!");
		adsClose.click();
		
	}

    public void selectFromLocation(String location) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(fromCity)).click();
        

        // Now wait for fresh search input
        WebElement fromSearch = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@role='combobox']//input[@type='text']")));
        fromSearch.sendKeys(location);

        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(fromCityOption)).click();
       
    }

    
    public void selectToLocation(String location) throws InterruptedException {
    	wait.until(ExpectedConditions.elementToBeClickable(toCity)).click();
    	
    	
    	WebElement toSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='To']")));
    	toSearch.sendKeys(location);
    	
    	Thread.sleep(3000);
    	 wait.until(ExpectedConditions.elementToBeClickable(toCityOption)).click();
    	 
    	 
    }
    public void selectDepartureDate(String date) {
        try {
            System.out.println("Opening departure calendar...");

           
            WebElement departureField = wait.until(ExpectedConditions.elementToBeClickable(departure));
            departureField.click();
            System.out.println("Departure calendar opened.");

            
            wait.until(ExpectedConditions.visibilityOf(calender));
             
            

            
            String xpath = String.format("//div[@aria-label='%s']", date);
            WebElement departureDate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

           
            departureDate.click();
            System.out.println("Clicked on Departure date: " + date);

        } catch (Exception e) {
            System.out.println("Error while selecting departure date: " + e.getMessage());
            
        }
    }

    public void clickSearch() throws InterruptedException {
        
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(searchBtn));
            jse.executeScript("arguments[0].scrollIntoView(true);", button);
            jse.executeScript("arguments[0].click();", button);
            Thread.sleep(10000);
      
    }
    
    public void navigateToHome() {
        driver.navigate().back();
        driver.navigate().refresh(); 
    }

    public void clickCheckBox() {
    	wait.until(ExpectedConditions.visibilityOf(roundTripCheckBox)).click();
    }
    
    public void selectReturnDate(String date) {
        try {
            System.out.println("Opening return calendar...");

            // Click on the return date field
            WebElement returnField = wait.until(ExpectedConditions.elementToBeClickable(returnDate));
            returnField.click();
            System.out.println("Return calendar opened.");

            // Wait until calendar is visible
            wait.until(ExpectedConditions.visibilityOf(calender));

            // Build dynamic xpath with given date
            String xpath = String.format("//div[@aria-label='%s']", date);
            WebElement returnDateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));

            // Click on the return date
            returnDateElement.click();
            System.out.println("Clicked on Return date: " + date);

        } catch (Exception e) {
            System.out.println("Error while selecting return date: " + e.getMessage());
        }
    }

}
