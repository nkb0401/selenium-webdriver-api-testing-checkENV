package b003_xpath;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

@Test
public class Login {
	WebDriver driver; 
	
	@BeforeTest
	public void beforeTest() {
		System.setProperty("webdriver.gecko.driver",".\\lib\\Brower\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.demoguru99.com/index.php");
		

	}

	public void TC01_Login_Empty_Email_Pass() {
		//steps
		driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.name("login[password]")).sendKeys("");
		driver.findElement(By.name("send")).click();
		//verify
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "This is a required field.");
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(), "This is a required field.");
	}


	public void TC02_Login_InvailEmail() {
		//steps
		driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
		driver.findElement(By.id("email")).sendKeys("123@123.123");
		driver.findElement(By.name("login[password]")).sendKeys("123");
		driver.findElement(By.name("send")).click();
		//verify		
		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(), "Please enter a valid email address. For example johndoe@domain.com.");

	}
	
	public void TC03_Login_InvailPass() {
		//steps		
		driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
		driver.findElement(By.id("email")).sendKeys("bangkhoa.nguyen@dxc.com");
		driver.findElement(By.name("login[password]")).sendKeys("123123");
		driver.findElement(By.name("send")).click();
		//verify		
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(), "Please enter 6 or more characters without leading or trailing spaces.");
					
	}
	
	public void TC04_Login_IncorrectEmail_and_vaild_pass() {
		//steps
		driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
		driver.findElement(By.id("email")).sendKeys("bang@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("12345678");
		driver.findElement(By.name("send")).click();
		//verify
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class=\"error-msg\"]//ul//li//span")).getText(), "Invalid login or password.");
		
	}
	
	public void TC05_Login_VaildEmail_and_incorrect_pass() {
		//steps	
		driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
		driver.findElement(By.id("email")).sendKeys("bang@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("12345678");
		driver.findElement(By.name("send")).click();
		//verify
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class=\"error-msg\"]//ul//li//span")).getText(), "Invalid login or password.");
		
	}
	
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
