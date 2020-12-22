package b015_Upload_Senkey_AutoIT;

import java.awt.RenderingHints.Key;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.server.handler.SendKeys;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Upload_Senkey_AutoIT {
	// Thẻ của upload toàn bộ đều có //input[@type='file']

	WebDriver driver;
	JavascriptExecutor jsExecutor;
	String sourceFolder = System.getProperty("user.dir");
	String imageName_01 = "nar1.jpg";
	String imageName_02 = "nar2.jpg";
	String imageName_03 = "nar3.jpg";
	String chromeOneTime = "chromeUploadOneTime.exe";
	String fireFoxOneTime = "firefoxUploadOneTime.exe";
	String chromeMulti = "chromeUploadMultiple.exe";
	String fireFoxMulti = "firefoxUploadMultiple.exe";

	String imagePath_01 = sourceFolder + "\\lib\\image\\" + imageName_01;
	String imagePath_02 = sourceFolder + "\\lib\\image\\" + imageName_02;
	String imagePath_03 = sourceFolder + "\\lib\\image\\" + imageName_03;
	String AutoItPathChrome = sourceFolder + "\\AutoIt\\" + chromeOneTime;
	String AutoItPathFirefox = sourceFolder + "\\AutoIt\\" + fireFoxOneTime;
	String AutoItPathChromeMulti = sourceFolder + "\\AutoIt\\" + chromeMulti;
	String AutoItPathFirefoxMulti = sourceFolder + "\\AutoIt\\" + fireFoxMulti;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\brower\\chromedriver.exe");
		driver = new ChromeDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	//@Test
	public void TC_01_Upload_nFile() {
		driver.get("http://blueimp.github.io/jQuery-File-Upload/");
		// Lấy thẻ input để senkey vào
		WebElement upLoadFile = driver.findElement(By.xpath("//input[@type='file']"));
		// add 1 lúc nhiều file
		upLoadFile.sendKeys(imagePath_01 + "\n" + imagePath_02 + "\n" + imagePath_03);
		sleepInSecond(2);

		// verify load lên thành công
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and contains(text(),'nar1.jpg')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and contains(text(),'nar2.jpg')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and contains(text(),'nar3.jpg')]")).isDisplayed());

		// click start cho nguyên list
		List<WebElement> listStartUpload = driver.findElements(By.xpath("//td//button[@class='btn btn-primary start']"));
		for (WebElement clickListStartUpload : listStartUpload) {
			clickListStartUpload.click();
			sleepInSecond(2);
		}

		// verify sau khi upload thành công
		// Verify bằng hình ảnh

		Assert.assertTrue(verifyImage("//img[contains(@src,'" + imageName_01 + "')]"));
		sleepInSecond(2);
		Assert.assertTrue(verifyImage("//img[contains(@src,'" + imageName_02 + "')]"));
		sleepInSecond(2);
		Assert.assertTrue(verifyImage("//img[contains(@src,'" + imageName_03 + "')]"));
		sleepInSecond(2);
	}
	@Test
	public void TC_02_Upload_eachfile() {
		driver.get("https://gofile.io/?t=uploadFiles");
		WebElement loadgofile = driver.findElement(By.xpath("//input[@type='file']"));
		sleepInSecond(2);
		// add từng file 1 phải load lại element 1 lần nữa
		loadgofile.sendKeys(imagePath_01);
		sleepInSecond(3);
		loadgofile = driver.findElement(By.xpath("//input[@type='file']"));
		loadgofile.sendKeys(imagePath_02);
		sleepInSecond(3);
		loadgofile = driver.findElement(By.xpath("//input[@type='file']"));
		loadgofile.sendKeys(imagePath_03);
		sleepInSecond(3);
		

		// verify load thành công
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'" + imageName_01 + "')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'" + imageName_02 + "')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//td[contains(text(),'" + imageName_03 + "')]")).isDisplayed());

		driver.findElement(By.xpath("//button[@id='uploadFiles-btnUpload']")).click();
		sleepInSecond(7);

		// verify upload thành công
		Assert.assertTrue(driver.findElement(By.xpath("//span[@class='font-weight-bold']")).isDisplayed());
		driver.findElement(By.xpath("//a[@id='uploadFiles-uploadRowResult-downloadLink']")).click();

		// Lấy id của current page
		String parentID = driver.getWindowHandle();
		// Switch sang tab mới
		switchToWindowByID(parentID);
		// tắt popup
		driver.findElement(By.xpath("//button[@class='swal2-cancel swal2-styled']")).click();
		sleepInSecond(3);
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@href,'" + imageName_01 + "')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@href,'" + imageName_02 + "')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//a[contains(@href,'" + imageName_03 + "')]")).isDisplayed());

	}

	//@Test
	public void TC_03_Upload_Gofile_AutoIt() throws IOException {
		driver.get("http://blueimp.github.io/jQuery-File-Upload/");
		driver.findElement(By.cssSelector(".fileinput-button")).click();
		Runtime.getRuntime().exec(new String[] { AutoItPathChromeMulti, imagePath_01, imagePath_02 });
		// verify load lên thành công
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and contains(text(),'nar1.jpg')]")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and contains(text(),'nar2.jpg')]")).isDisplayed());

		// click start cho nguyên list
		List<WebElement> listStartUpload = driver.findElements(By.xpath("//td//button[@class='btn btn-primary start']"));
		for (WebElement clickListStartUpload : listStartUpload) {
			clickListStartUpload.click();
			sleepInSecond(2);
		}

		// verify sau khi upload thành công
		// Verify bằng hình ảnh

		Assert.assertTrue(verifyImage("//img[contains(@src,'" + imageName_01 + "')]"));
		sleepInSecond(2);
		Assert.assertTrue(verifyImage("//img[contains(@src,'" + imageName_02 + "')]"));
		sleepInSecond(2);

	}

	@AfterClass
	public void afterClass() {
		//driver.quit();
	}

	// get page id
	public void switchToWindowByID(String pageID) {
		// Lấy ra tất cả id của window/tab đang có
		// dùng set do chỉ lấy ra giá trị duy nhất, còn List sẽ lấy ra giá trị trùng
		// nhau
		Set<String> allWindows = driver.getWindowHandles();
		for (String listWindown : allWindows) {
			if (!listWindown.equals(pageID)) {
				driver.switchTo().window(listWindown);
				break;
			}
		}
	}

	// Verify image bằng JavaScript Executor
	public boolean verifyImage(String imagelocator) {
		boolean isImageLoaded = (Boolean) ((JavascriptExecutor) driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", driver.findElement(By.xpath(imagelocator)));
		return isImageLoaded;
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
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(By.xpath(clicklocator)));
	}

	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}