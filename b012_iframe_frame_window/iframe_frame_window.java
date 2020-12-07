package b012_iframe_frame_window;


import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class iframe_frame_window {
	WebDriver driver;
	JavascriptExecutor jvExecutor;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
		jvExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	public void TC_01_Iframe_Frame() {
		driver.get("https://kyna.vn/");
		sleepInSecond(2);
		WebElement showregisterForm = driver.findElement(By.xpath("//img[@class='fancybox-image']"));
		if (showregisterForm.isDisplayed()) {
			Assert.assertTrue(driver.findElement(By.xpath("//img[@class='fancybox-image']")).isDisplayed());
			sleepInSecond(2);
			driver.findElement(By.xpath("//a[@class='fancybox-item fancybox-close']")).click();
			sleepInSecond(2);
		}

		driver.switchTo().frame(driver.findElement(By.xpath("//div[@class='fanpage ']//iframe")));
		sleepInSecond(2);
		WebElement iframedisplay = driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div"));
		Assert.assertEquals(iframedisplay.getText(), "169K likes");

		// switch to webchat
		// defaultContent là quay về lại iframe lúc chưa switch
		driver.switchTo().defaultContent();
		sleepInSecond(2);
		driver.switchTo().frame(driver.findElement(By.xpath("//div[@id='cs-live-chat']/iframe")));
		WebElement iframeChatBox = driver.findElement(By.xpath("//span[contains(text(),'Hỗ trợ trực tuyến Kyna')]"));
		Assert.assertEquals(iframeChatBox.getText(), "Hỗ trợ trực tuyến Kyna");
		sleepInSecond(2);
		driver.findElement(By.tagName("textarea")).sendKeys("Automation");
		sleepInSecond(2);
		driver.findElement(By.tagName("textarea")).sendKeys(Keys.ENTER);
		Assert.assertTrue(driver.findElement(By.xpath("//div[@name='profile_menu']")).isDisplayed());
		sleepInSecond(2);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("live-search-bar")).sendKeys("Java");
		driver.findElement(By.xpath("//button[@class='search-button']//img")).click();
		sleepInSecond(1);
		Assert.assertEquals(driver.findElement(By.xpath("//span//h1")).getText(), "'Java'");

	}

	@Test
	public void TC_02_Window_Tab() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		// Lấy ra id của page đang đứng
		String parentId = driver.getWindowHandle();

		clickJvscript("//a[contains(text(),'GOOGLE')]");
		// Switch qua google
		switchToWindowByTitle("Google");
		Assert.assertEquals(driver.getTitle(), "Google");

		// Switch lại thằng cha
		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		clickJvscript("//a[contains(text(),'FACEBOOK')]");
		// switch qua facebook
		switchToWindowByTitle("Facebook - Đăng nhập hoặc đăng ký");
		Assert.assertEquals(driver.getTitle(), "Facebook - Đăng nhập hoặc đăng ký");

		// Switch lại thằng cha
		switchToWindowByTitle("SELENIUM WEBDRIVER FORM DEMO");
		clickJvscript("//a[contains(text(),'TIKI')]");
		// switch qua tiki
		switchToWindowByTitle("Mua Hàng Trực Tuyến Uy Tín với Giá Rẻ Hơn tại Tiki.vn");
		Assert.assertEquals(driver.getTitle(), "Mua Hàng Trực Tuyến Uy Tín với Giá Rẻ Hơn tại Tiki.vn");

		closeAllWindow(parentId);
		Assert.assertEquals(driver.getTitle(), "SELENIUM WEBDRIVER FORM DEMO");

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	// get page title
	public void switchToWindowByTitle(String pagetitle) {
		// Lấy ra tất cả id của window/tab đang có
		// dùng set do chỉ lấy ra giá trị duy nhất, còn List sẽ lấy ra giá trị trùng nhau
		Set<String> allWindows = driver.getWindowHandles();
		// Dùng vòng lặp duyệt qua các ID
		for (String listWindown : allWindows) {
			// Swtich vào từng window/tab
			driver.switchTo().window(listWindown);
			// Lấy ra title của tab đó
			String currentTitlePage = driver.getTitle();
			// Kiểm tra title của page nào bằng với title của page expected thì truyền vào
			if (currentTitlePage.equals(pagetitle)) {
				// Thoát vòng lặp
				break;
			}
		}
	}

	// get page id
	public void switchToWindowByID(String pageID) {
		// Lấy ra tất cả id của window/tab đang có
		// dùng set do chỉ lấy ra giá trị duy nhất, còn List sẽ lấy ra giá trị trùng nhau
		Set<String> allWindows = driver.getWindowHandles();
		// Dùng vòng lặp duyệt qua các ID
		for (String listWindown : allWindows) {
			// Kiểm tra title của page nào bằng với title của page expected thì truyền vào
			if (!listWindown.equals(pageID)) {
				// Swtich vào từng window/tab
				driver.switchTo().window(listWindown);
				// Thoát vòng lặp
				break;
			}
		}
	}

	// Close all window/tab
	public boolean closeAllWindow(String parentID) {
		// Lấy ra tất cả ID
		Set<String> idWindow = driver.getWindowHandles();
		// Dùng vòng lặp duyệt qua từng ID
		for (String allidwindow : idWindow) {
			// Nếu như ID khác với ID của parent
			if (!allidwindow.equals(parentID)) {
				// Switch vào ID đó
				driver.switchTo().window(allidwindow);
				driver.close();
			}
		}

		driver.switchTo().window(parentID);
		if (driver.getWindowHandles().size() == 1)
			return true;
		else
			return false;
	}

	// Click javascript
	public void clickJvscript(String clicklocator) {
		jvExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath(clicklocator)));
	}

	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}