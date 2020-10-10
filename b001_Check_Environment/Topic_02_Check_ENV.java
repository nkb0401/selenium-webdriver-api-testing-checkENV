package b001_Check_Environment;

import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Topic_02_Check_ENV {
	WebDriver driver;
	String project_location = System.getProperty("user.dir");
	

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver",".\\lib\\Brower\\geckodriver.exe");
		
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4/");
	}

	@Test
	public void TC_02_RunChorme() {
		System.setProperty("webdriver.chrome.driver",project_location + "\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Test
	public void TC_03_ValidateCurrentUrl() {
		// Login Page Url matching
		String loginPageUrl = driver.getCurrentUrl();
		Assert.assertEquals(loginPageUrl, "http://demo.guru99.com/v4/");
	}


	@Test
	public void TC_04_ValidatePageTitle() {
		// Login Page title
		String loginPageTitle = driver.getTitle();
		Assert.assertEquals(loginPageTitle, "Guru99 Bank Home Page");
	}


	@Test
	public void TC_05_LoginFormDisplayed() {
		// Login form displayed
		Assert.assertTrue(driver.findElement(By.xpath("//form[@name='frmLogin']")).isDisplayed());
	}


	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
