package b003_Textbox_textarea;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;

public class TC_01 {
	WebDriver driver;
	
	@BeforeTest
	public void beforeTest() {		
		System.setProperty("webdriver.gecko.driver", ".\\lib\\Brower\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4/");
	}

	@Test
	// user:mngr287795; pass:vynUtAq
	public void f() {
		driver.findElement(By.name("uid")).sendKeys("mngr287795");
		driver.findElement(By.name("password")).sendKeys("vynUtAq");
		driver.findElement(By.name("btnLogin")).click();
	}
	
	@AfterTest
	public void afterTest() {
		//driver.quit();
	}

}
