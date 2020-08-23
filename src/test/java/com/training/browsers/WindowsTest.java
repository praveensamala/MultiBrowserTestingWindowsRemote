package com.training.browsers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class WindowsTest {
	String HubUrl = "http://192.168.134.143:4444/wd/hub";
	
	DesiredCapabilities chromecapabilities = DesiredCapabilities.chrome();
	ChromeOptions chromeoptions = new ChromeOptions();
    
    DesiredCapabilities firefoxcapabilities = DesiredCapabilities.firefox();
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    
	@BeforeClass
	public void beforeSuite() {
		//Below code is disable notifications from chrome browser during the testing
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");  
		chromeoptions.setExperimentalOption("prefs", prefs);
		chromecapabilities.setBrowserName("chrome");
		chromecapabilities.setPlatform(Platform.WINDOWS);
		chromeoptions.merge(chromecapabilities);
		
		//Below code is disable notifications from firefox browser during the testing
		FirefoxProfile profile = new FirefoxProfile();
	    profile.setPreference("permissions.default.desktop-notification", 1);
	    
		System.setProperty("webdriver.gecko.driver",  "geckodriver.exe");
	    firefoxcapabilities.setCapability(FirefoxDriver.PROFILE, profile);
	    firefoxcapabilities.setBrowserName("firefox");
	    firefoxcapabilities.setPlatform(Platform.WINDOWS);
	}
	
	@Test (enabled = true)
	public void loginTest1() throws MalformedURLException {		
		WebDriver driver = new RemoteWebDriver(new URL(HubUrl), chromeoptions);
		facebookLoginTestChrome(driver);
		driver.close();
	}
	
	@Test (enabled = true)
	public void loginTest2() throws MalformedURLException {
	    WebDriver driver = new RemoteWebDriver(new URL(HubUrl), firefoxcapabilities);
	    facebookLoginTestFirefox(driver);
		driver.close();
	}
	
	public static void facebookLoginTestChrome(WebDriver driver) {
		driver.get("https://www.facebook.com");
		
		driver.findElement(By.id("email")).sendKeys("gamecheck280@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("system123");
		driver.findElement(By.name("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]")));
		
		List <WebElement> list = new ArrayList<WebElement>();
		list = driver.findElements(By.tagName("span"));
		boolean userfound = false;
		for(WebElement e: list) {
			if (e.getText().contains("Gamecheck") && !e.getText().contains("Not you") && !e.getText().contains("Log in as")) {
				userfound = true;
				break;
			}
		}
		
		driver.findElement(By.id("userNavigationLabel")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span//form[@method='post']")));
		WebElement ele = driver.findElement(By.xpath("//span//form[@method='post']"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", ele);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@role='button' and contains(text(), 'Create New Account')]")));
		
		AssertJUnit.assertTrue(userfound);
	}
	
	public static void facebookLoginTestFirefox(WebDriver driver) {
		driver.get("https://www.facebook.com");
		
		driver.findElement(By.id("email")).sendKeys("gamecheck280@gmail.com");
		driver.findElement(By.id("pass")).sendKeys("system123");
		driver.findElement(By.name("login")).click();
		
		WebDriverWait wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type=\"submit\"]")));
		
		List <WebElement> list = new ArrayList<WebElement>();
		list = driver.findElements(By.tagName("span"));
		boolean userfound = false;
		for(WebElement e: list) {
			if (e.getText().contains("Gamecheck") && !e.getText().contains("Not you") && !e.getText().contains("Log in as")) {
				userfound = true;
				break;
			}
		}
		
		driver.findElement(By.id("userNavigationLabel")).click();
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span//form[@method='post']")));
		WebElement ele = driver.findElement(By.xpath("//span//form[@method='post']"));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", ele);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@role='button' and contains(text(), 'Create New Account')]")));
		
		AssertJUnit.assertTrue(userfound);
	}
	
	@AfterClass
	public void afterTest() {
		
	}
	
	public static void sleep(int n) {
		try {
			Thread.sleep(n*1000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
