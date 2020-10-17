package b003_Textbox_textarea;

import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class login_verifynewcustomer {
	WebDriver driver;
	
	@BeforeClass
	public void beforeClass() {		
		System.setProperty("webdriver.gecko.driver", ".\\lib\\Brower\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4/");
	}

	@Test
	// user:mngr287795; pass:vynUtAq
	public void TC_01_login() {
		driver.findElement(By.name("uid")).sendKeys("mngr287795");
		driver.findElement(By.name("password")).sendKeys("vynUtAq");
		driver.findElement(By.name("btnLogin")).click();
		//Verify login thanh cong
		Assert.assertEquals(driver.findElement(By.xpath("//tbody/tr[3]/td")).getText(), "Manger Id : mngr287795");
	}
	
	@Test
	public void TC_02_create_customer() {
		driver.findElement(By.xpath("//div[3]/div/ul/li[2]")).click();
		driver.findElement(By.xpath("//tr[4]/td[2]/input")).sendKeys("abc123");
		driver.findElement(By.xpath("//tr[5]/td[2]/input[2]")).click();
		
		//Verify login thanh cong
		Assert.assertEquals(driver.findElement(By.xpath("//tbody/tr[3]/td")).getText(), "Manger Id : mngr287795");
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
