package b014_Wait;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Wait_Part_I_Element_Status {

	WebDriver driver;
	WebDriverWait explicitWait;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
		explicitWait = new WebDriverWait(driver, 10);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	public void TC_01_FindElement_Visible() {
		driver.get("http://demo.guru99.com/");

		// ***điều kiện bắt buộc của visible là phải có trên UI và có trong Dom
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='emailid']"))).sendKeys("test123@gmail.com");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
	}

	public void TC_02_FindElement_Invisible() {
		driver.get("http://demo.guru99.com/");

		// ***điều kiện bắt buộc của Invisible là không có trên UI

		// không có trên UI + có trong Dom
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[contains(text(),'Flash Movie Demo')]")));

		// không có trên UI + ko có trong Dom
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//li[contains(text(),'UserID : 1303')]")));

	}

	public void TC_03_FindElement_Presence() {
		driver.get("http://demo.guru99.com/");
		// ***điều kiện bắt buộc của Presence là phải có trong dom

		// có trong Dom và có trên UI
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='emailid']"))).sendKeys("test123@gmail.com");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();

		// Khống có trên UI nhưng có trong Dom
		explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(),'Flash Movie Demo')]")));
	}

	public void TC_04_FindElement_Staleness() {
		driver.get("http://demo.guru99.com/");
		// ***Điều kiện bắt buộc của Staleness là không có trong Dom

		driver.findElement(By.xpath("//a[contains(text(),'Agile Project')]")).click();
		// xác nhận nó có hiển thị
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(text(),'UserID : 1303')]")));
		// Lưu thành 1 cái biến để cho staleness ghi nhận
		WebElement stalenessID = driver.findElement(By.xpath("//li[contains(text(),'UserID : 1303')]"));
		// chuyển sang page khác để staleness xác nhận là vẫn tìm được element đã được gán trước đó
		driver.findElement(By.xpath("//a[contains(text(),'Telecom Project')]")).click();
		// Xác nhận element bằng staleness
		explicitWait.until(ExpectedConditions.stalenessOf(stalenessID));

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}