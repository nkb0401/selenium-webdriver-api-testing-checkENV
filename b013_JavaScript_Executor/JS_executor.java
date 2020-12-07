package b013_JavaScript_Executor;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class JS_executor {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	WebElement element;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\Driver\\chromedriver.exe");
		driver = new ChromeDriver();
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	public void TC_01_LiveGuru() {
		// mở web
		navigateToUrlByJS("http://live.demoguru99.com/");
		// get domain
		// ép kiểu cha qua con (object) --> String = downString, và trả lại dữ liệu = return
		String domainOfBrowser = (String) executeForBrowser("return document.domain");
		// verify
		Assert.assertEquals(domainOfBrowser, "live.demoguru99.com");
		String urlOfBrowser = (String) executeForBrowser("return document.URL");
		// verify URL
		Assert.assertEquals(urlOfBrowser, "http://live.demoguru99.com/");
		// click vô mobile
		clickToElementByJS("//a[contains(text(),'Mobile')]");

		clickToElementByJS("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']/button");
		// verify message sau khi click thành công
		String getTextVerifyAddCast = getTextInerText();
		Assert.assertTrue(getTextVerifyAddCast.contains("Samsung Galaxy was added to your shopping cart."));

		clickToElementByJS("//a[contains(text(),'Customer Service')]");

		// get title
		String titleofBrowser = (String) executeForBrowser("return document.title");
		Assert.assertEquals(titleofBrowser, "Customer Service");

		scrollToElement("//input[@id='newsletter']");
		String getTextVerifyText = getTextInerText();
		Assert.assertTrue(getTextVerifyText.contains("Praesent ipsum libero, auctor ac, tempus nec, tempor nec, justo."));

	}

	@Test
	public void TC_02_Remove_Attribute() {
		navigateToUrlByJS("http://demo.guru99.com/v4");
		sleepInSecond(2);
		driver.findElement(By.xpath("//input[@name='uid']")).sendKeys("mngr285493");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("unAnyvU");
		driver.findElement(By.xpath("//input[@name='btnLogin']")).click();
		driver.findElement(By.xpath("//a[contains(text(),'New Customer')]")).click();
		removeAttributeInDOM("//input[@id='dob']", "type");
		sleepInSecond(3);
		driver.findElement(By.xpath("//input[@id='dob']")).sendKeys("20" + "/" + "09" + "/" + "1992");
		sleepInSecond(3);

	}

	@Test
	public void TC_03_Element_ToolTip() throws InterruptedException {
		driver.findElement(By.xpath("//input[@value='SUBMIT']")).click();
		Thread.sleep(3000);
		String nameMessage = getHTML5ValidationMessage(driver.findElement(By.xpath("//input[@id='fname']")));
		Assert.assertEquals("Please fill out this field.", nameMessage);
		System.out.println("Text verify : " + nameMessage);
	}

	@AfterClass
	public void afterClass() {
		driver.close();
	}

	// Browser (vào console để lấy các đoạn mã lệnh)
	public Object executeForBrowser(String javaSript) {
		return jsExecutor.executeScript(javaSript);
	}

	// Verify tất cả text trên page hiện tại = Assert.assert...
	public String getTextInerText() {
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}

	// gettext (hạn chế dùng), verify chính xác 1 text nào đó có trên current page
	public boolean verifyTextInInnerText(WebDriver driver, String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	// Cuộn tới chỗ element
	public void scrollToBottomPage(WebDriver driver) {
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	// get url mở 1 trang web
	public void navigateToUrlByJS(String url) {
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	// Get style của elemnent
	public void highlightElement(String locator) {
		element = driver.findElement(By.xpath(locator));
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);

	}

	// Hàm click
	public void clickToElementByJS(String locator) {
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	// Hàm scroll
	public void scrollToElement(String locator) {
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	// hàm send kí tự vào
	public void sendkeyToElementByJS(String locator, String value) {
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public void removeAttributeInDOM(String locator, String attributeRemove) {
		element = driver.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
	}

	// hàm tìm tooltip của 1 element
	public String getHTML5ValidationMessage(WebElement element) {
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", element);
	}

	// hàm sleep
	public void sleepInSecond(long time) {
		try {
			Thread.sleep(time * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}