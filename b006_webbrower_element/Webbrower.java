package b006_webbrower_element;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriver.WindowType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Webbrower {
	WebDriver driver;
	String project_location=System.getProperty("user.dir");
	
  @BeforeClass
  public void beforeClass() {
	  System.setProperty("webdriver.chrome.driver", project_location + "\\lib\\Brower\\chromedriver.exe");
	  driver = new ChromeDriver();
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  driver.manage().window().maximize();
  }
  
  @Test
  public void TC01_verifyURL() {
	  driver.get("http://live.demoguru99.com/");
	  driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
	  Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/login/");
	  driver.findElement(By.xpath("//a[@title=\"Create an Account\"]")).click();
	  Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/create/");
  }
  
  @Test
  public void TC02_verifyTitle() {
	  driver.get("http://live.demoguru99.com/");
	  driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
	  Assert.assertEquals(driver.getTitle(), "Customer Login");
	  driver.findElement(By.xpath("//a[@title=\"Create an Account\"]")).click();
	  Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/create/");
  }
  
  @Test
  public void TC03_verifyNagativeFunction(){
	  driver.get("http://live.demoguru99.com/");
	  driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
	  driver.findElement(By.xpath("//a[@title=\"Create an Account\"]")).click();
	  Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/create/");
	  driver.navigate().back();;
	  Assert.assertEquals(driver.getCurrentUrl(), "http://live.demoguru99.com/index.php/customer/account/login/");
	  driver.navigate().forward();
	  Assert.assertEquals(driver.findElement(By.xpath("//div[@class=\"page-title\"]//h1")).getText(), "CREATE AN ACCOUNT");
	  
  }
  
  @Test
  public void TC04_verifyGetsource() {
	  driver.get("http://live.demoguru99.com/");
	  driver.findElement(By.xpath("//div[@class=\"footer\"]//a[text()=\"My Account\"]")).click();
	  String loginpagesource = driver.getPageSource();
	  Assert.assertTrue(loginpagesource.contains("LOGIN OR CREATE AN ACCOUNT"));
	  driver.findElement(By.xpath("//a[@title=\"Create an Account\"]")).click();
	  Assert.assertEquals(driver.findElement(By.xpath("//div[@class=\"page-title\"]//h1")).getText(), "CREATE AN ACCOUNT");
  }

  @AfterClass
  public void afterClass() {
  }

}
