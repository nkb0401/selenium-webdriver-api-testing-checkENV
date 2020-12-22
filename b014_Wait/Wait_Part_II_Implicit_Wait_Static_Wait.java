package b014_Wait;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Wait_Part_II_Implicit_Wait_Static_Wait {

	WebDriver driver;
	WebDriverWait explicitWait;
	Select select;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	public void TC_01_ImplicitWait() throws InterruptedException {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.findElement(By.xpath("//button[contains(text(),'Start')]")).click();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// implicit Wait chờ cho đến khi tìm được element có text = Hello World!
		Assert.assertTrue(driver.findElement(By.xpath("//h4[contains(text(),'Hello World!')]")).isDisplayed());
	}

	@Test
	public void TC_02_StaticWait() throws InterruptedException {
		driver.get("https://juliemr.github.io/protractor-demo/");
		sleepInSecond(2);
		driver.findElement(By.xpath("//input[@ng-model='first']")).sendKeys("2");
		sleepInSecond(2);
		select = new Select(driver.findElement(By.xpath("//select[@ng-model='operator']")));
		sleepInSecond(2);
		select.selectByVisibleText("*");
		sleepInSecond(2);
		driver.findElement(By.xpath("//input[@ng-model='second']")).sendKeys("2");
		sleepInSecond(2);
		driver.findElement(By.xpath("//button[@id='gobutton']")).click();
		sleepInSecond(5);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), "*");
		sleepInSecond(2);
		// implicit Wait chờ cho đến khi tìm được element có text = Hello World!
		Assert.assertTrue(driver.findElement(By.xpath("//h2[text()='4']")).isDisplayed());
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}