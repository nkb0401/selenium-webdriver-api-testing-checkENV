package b003_xpath;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewTest {
	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", ".\\lib\\Brower\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://live.demoguru99.com/index.php/");
	}

	@Test
	public void TC_01_Login_Empty_Email_And_Password() {
		// Click vào My Account link
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();

		// Nhập vào Email và Password
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.name("login[password]")).sendKeys("");

		// Click vào button login
		driver.findElement(By.name("send")).click();

		// Kiểm tra error message xuất hiện tại Email textbox

		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-email")).getText(),
				"This is a required field.");

		// Kiểm tra error message xuất hiện tại Password textbox

		Assert.assertEquals(driver.findElement(By.id("advice-required-entry-pass")).getText(),
				"This is a required field.");
	}

	@Test
	public void TC_02_Login_Invalid_Email() {

		// Click vào My Account link
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		// Nhập vào Email và Password
		driver.findElement(By.id("email")).sendKeys("123@45.243");
		driver.findElement(By.name("login[password]")).sendKeys("123456");
		// Click vào button login
		driver.findElement(By.name("send")).click();
		//// Kiểm tra error message xuất hiện tại Email textbox
		Assert.assertEquals(driver.findElement(By.id("advice-validate-email-email")).getText(),
				"Please enter a valid email address. For example johndoe@domain.com.");

	}

	@Test
	public void TC_03_Login_Invalid_Password() {
		// Click vào My Account link
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		// Nhập vào Email và Password
		driver.findElement(By.id("email")).sendKeys("dam@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123");
		// Click vào button login
		driver.findElement(By.name("send")).click();
		//// Kiểm tra error message xuất hiện tại Password textbox
		Assert.assertEquals(driver.findElement(By.id("advice-validate-password-pass")).getText(),
				"Please enter 6 or more characters without leading or trailing spaces.");

	}

	@Test
	public void TC_04_Login_Incorect_Email_And_Valid_Password() {
		// Click vào My Account link
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		// Nhập vào incorrect Email và Password
		driver.findElement(By.id("email")).sendKeys("autotest345345@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123456");
		// Click vào button login
		driver.findElement(By.name("send")).click();

		// Kiểm tra error message xuất hiện
		Assert.assertEquals(driver.findElement(By.xpath("//liv[@class='error-msg']//span")).getText(),
				"Invalid login or password.");

	}

	@Test
	public void TC_05_Login_Valid_Email_And_Incorrect_Password() {
		// Click vào My Account link
		driver.findElement(By.xpath("//div[@class='footer']//a[text()='My Account']")).click();
		// Nhập vào incorrect Email và Password
		driver.findElement(By.id("email")).sendKeys("dam@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("654321");
		// Click vào button login
		driver.findElement(By.name("send")).click();

		// Kiểm tra error message xuất hiện
		Assert.assertEquals(driver.findElement(By.xpath("//liv[@class='error-msg']//span")).getText(),
				"Invalid login or password.");

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
