package base;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup {
	private static WebDriver driver;
	public static WebDriver getDriver() {
        WebDriverManager.firefoxdriver();
		
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
		return driver;
	}
}
