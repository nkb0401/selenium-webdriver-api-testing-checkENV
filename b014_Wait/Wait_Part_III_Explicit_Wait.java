package b014_Wait;

import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Wait_Part_III_Explicit_Wait {

	WebDriver driver;
	WebDriverWait explicitWait;
	Alert alert;

	String sourceFolder = System.getProperty("user.dir");
	String imageName_01 = "nar1.jpg";
	String imageName_02 = "nar2.jpg";
	String imageName_03 = "nar3.jpg";
	String chromeMulti = "chromeUploadMultiple.exe";
	String fireFoxMulti = "firefoxUploadMultiple.exe";

	String imagePath_01 = sourceFolder + "\\lib\\image\\" + imageName_01;
	String imagePath_02 = sourceFolder + "\\lib\\image\\" + imageName_02;
	String imagePath_03 = sourceFolder + "\\lib\\image\\" + imageName_03;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
		explicitWait = new WebDriverWait(driver, 60);
		driver.manage().window().maximize();
	}

	public void TC_01_Alert_Accept() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Click for JS Alert')]")));
		driver.findElement(By.xpath("//button[contains(text(),'Click for JS Alert')]")).click();
		alert = explicitWait.until(ExpectedConditions.alertIsPresent());
		System.out.println("Text của alert là : " + alert.getText());
		alert.accept();
		WebElement VerifyText = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='result']")));
		VerifyText.getText();
		Assert.assertEquals(VerifyText.getText(), "You clicked an alert successfully");
	}

	public void TC_02_Visible_Invisible() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.findElement(By.xpath("//button[contains(text(),'Start')]")).click();
		// invisible sử dụng boolean
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@id='loading']//img")));
		// visible sử dụng WebElement
		WebElement waitHello = explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h4[contains(text(),'Hello World!')]")));
		Assert.assertEquals(waitHello.getText(), "Hello World!");

	}

	// AjaxLoading là load lại 1 phần của trang khi thao tác 1 element sẽ được load lại.
	public void TC_03_AjaxLoading() {
		driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='contentWrapper']")));
		WebElement textnonePick = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']"));
		Assert.assertEquals(textnonePick.getText(), "No Selected Dates to display.");

		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@title='Wednesday, September 23, 2020']"))).click();
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Wednesday, September 23, 2020']")));
		// viết lại hàm textnonePick do page bị reload.
		textnonePick = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']"));
		Assert.assertEquals(textnonePick.getText(), "Wednesday, September 23, 2020");

		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//td[@title='Thursday, September 24, 2020']"))).click();
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Thursday, September 24, 2020']")));
		// viết lại hàm textnonePick do page bị reload.
		textnonePick = driver.findElement(By.xpath("//span[@id='ctl00_ContentPlaceholder1_Label1']"));
		Assert.assertEquals(textnonePick.getText(), "Wednesday, September 23, 2020\nThursday, September 24, 2020");

	}

	@Test
	public void TC_04_Upload_ExplicitWait() throws InterruptedException {
		driver.get("https://gofile.io/?t=uploadFiles");
		Thread.sleep(10000);
		WebElement loadgofile = driver.findElement(By.xpath("//input[@type='file']"));
		loadgofile.sendKeys(imagePath_01 + "\n" + imagePath_02 + "\n" + imagePath_03);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'nar1.jpg')]")));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'nar2.jpg')]")));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'nar3.jpg')]")));

		driver.findElement(By.xpath("//button[@id='uploadFiles-btnUpload']")).click();
		explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='uploadFiles-uploadRowResult-downloadLink']"))).click();
		// Lấy id của current page
		String parentID = driver.getWindowHandle();
		// Switch sang tab mới
		switchToWindowByID(parentID);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'nar1.jpg')]")));
		driver.findElement(By.xpath("//button[@class='btn btn-primary btn-icon btn-md']")).click();;
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'nar2.jpg')]")));
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href,'nar3.jpg')]")));
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

}