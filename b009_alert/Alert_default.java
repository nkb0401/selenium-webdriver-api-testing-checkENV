package b009_alert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.Assert;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Alert_default {
	WebDriver driver;
	WebDriverWait expliciWait;
	Alert alert;
	By resultText = By.xpath("//p[@id='result']");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
		expliciWait = new WebDriverWait(driver, 30);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC01_alert_accept() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//div[@class='example']/button[1]")).click();
		//chờ cho alert xuất hiện
		expliciWait.until(ExpectedConditions.alertIsPresent());
		// switch qua alert
		alert = driver.switchTo().alert();
		sleepInSecond(4);
		Assert.assertEquals(alert.getText(), "I am a JS Alert");
		alert.accept();
		Assert.assertEquals(driver.findElement(resultText).getText(), "You clicked an alert successfully");
		// accept:	alert.accept();		
		// cancel: 	alert.dismiss();		
		//sendkey:	alert.sendKeys("");		
		// gettext:	alert.getText();		
	}

	@Test
	public void TC02_alert_cancel() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//div[@class='example']/button[2]")).click();
		//chờ cho alert xuất hiện
		expliciWait.until(ExpectedConditions.alertIsPresent());
		// switch qua alert
		alert = driver.switchTo().alert();
		sleepInSecond(4);
		Assert.assertEquals(alert.getText(), "I am a JS Confirm");
		alert.dismiss();
		Assert.assertEquals(driver.findElement(resultText).getText(), "You clicked: Cancel");
	}
	
	@Test
	public void TC03_alert_prompt() {
		String loginvalue = "Bang aka";
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.findElement(By.xpath("//div[@class='example']/button[3]")).click();
		//chờ cho alert xuất hiện
		expliciWait.until(ExpectedConditions.alertIsPresent());
		// switch qua alert
		alert = driver.switchTo().alert();
		sleepInSecond(4);
		Assert.assertEquals(alert.getText(), "I am a JS prompt");
		alert.sendKeys(loginvalue);
		alert.accept();	
		Assert.assertEquals(driver.findElement(resultText).getText(), "You entered: " + loginvalue);
	}
	
	@Test
	public void TC04_alert_authentication() {
		String username = "admin";
		String password = "admin";

		driver.get("http://" + username + ":" + password + "@" + "the-internet.herokuapp.com/basic_auth");
		sleepInSecond(2);

		Assert.assertTrue(driver.findElement(By.xpath("//h3[contains(text(),'Basic Auth')]")).isDisplayed());
	}
	
	@Test
	public void TC_05_Authentication_Notlink() {
		driver.get("https://the-internet.herokuapp.com/");

		String getlinkinvi = driver.findElement(By.xpath("//a[contains(text(),'Basic Auth')]")).getAttribute("href");
		System.out.println(getlinkinvi);
		sleepInSecond(2);
		authenticationNotLink(getlinkinvi);
		sleepInSecond(2);
		Assert.assertTrue(driver.findElement(By.xpath("//h3[contains(text(),'Basic Auth')]")).isDisplayed());

	}
	
	@AfterClass
	public void afterClass() {
		//driver.quit();
	}
	
	public void sleepInSecond(long time) {
		try {
				Thread.sleep(time*1000);
			} catch (InterruptedException e){
					e.printStackTrace();;	
			}
	}
	
	public void authenticationNotLink (String linkweb) {
		String splitling[] = linkweb.split("//");
		String username = "admin";
		String password = "admin";
		// Hàm split chia 1 link web thành 2 mảng
		// [0] : Http:
		// [1] : link của web vd: tiki.vn
		linkweb = splitling[0] + "//" + username + ":" + password + "@" + splitling[1];
		driver.get(linkweb);
		Assert.assertTrue(driver.findElement(By.xpath("//h3[contains(text(),'Basic Auth')]")).isDisplayed());
	}
}
