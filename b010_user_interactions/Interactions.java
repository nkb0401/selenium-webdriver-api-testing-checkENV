package b010_user_interactions;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Interactions {
	WebDriver driver;
	Actions action;
	WebElement shortcut, element;
	JavascriptExecutor jsExecutor;
	String rootFolder = System.getProperty("user.dir");
	
	String javascriptPath =rootFolder +"\\b010_user_interactions\\drag_and_drop_helper.js";
	String jQueryPath =rootFolder +"\\b010_user_interactions\\jquery_load_helper.js";
	
	
	
	@BeforeClass
	public void beforeClass() {
		
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
		action = new Actions(driver);
		jsExecutor = (JavascriptExecutor)driver;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	//@Test
	public void TC01_hover() {
		driver.get("https://tiki.vn/");
		sleepInSecond(6);
		driver.findElement(By.xpath("//button[@id='onesignal-slidedown-cancel-button']")).click();;
		shortcut = driver.findElement(By.xpath("//span[@class='Userstyle__NoWrap-sc-6e6am-11 cbYxcp']"));
		action.moveToElement(shortcut).perform();
	}
	//@Test
	public void TC_02_Hover_Mouse() {
		driver.get("http://www.myntra.com/");
		element = driver.findElement(By.xpath("//a[@class='desktop-main'][contains(text(),'Kids')]"));
		// di chuyển chuột tới 1 vị trí
		action.moveToElement(element).perform();
		driver.findElement(By.xpath("//a[contains(text(),'Home & Bath')]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='breadcrumbs-crumb']")).getText(), "Kids Home Bath");

	}
	
	//@Test
	public void TC03_click_hover() {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		List<WebElement> allItem = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		// click chuột vào giữ
		action.clickAndHold(allItem.get(0)).moveToElement(allItem.get(3)).release().perform();
		List<WebElement> allItemSelected = driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"));
		Assert.assertEquals(allItemSelected.size(), 4);

		for (WebElement displayitemSelected : allItemSelected) {
			System.out.println(displayitemSelected.getText());
		}
		sleepInSecond(6);
	}
	//@Test
	public void TC04_doubleclick() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		element = driver.findElement(By.xpath("//button[contains(text(),'Double click me')]"));
		// double click chuột
		action.doubleClick(element).perform();
		sleepInSecond(1);
		WebElement verifytext = driver.findElement(By.xpath("//p[@id='demo']"));
		Assert.assertEquals(verifytext.getText(), "Hello Automation Guys!");
		sleepInSecond(1);
		System.out.println("Verify text double text is : " + verifytext.getText());
	}
	//@Test
	public void TC_05_Ctrl_And_Click() {
		driver.get("http://jqueryui.com/resources/demos/selectable/display-grid.html");
		List<WebElement> allItem = driver.findElements(By.xpath("//ol[@id='selectable']/li"));
		// Nhấn button Ctrl
		action.keyDown(Keys.CONTROL).perform();
		// Click chuột trái
		action.click(allItem.get(0)).click(allItem.get(2)).click(allItem.get(5)).click(allItem.get(10)).perform();
		// Thả button Ctrl
		action.keyUp(Keys.CONTROL).perform();
		List<WebElement> allItemSelected = driver.findElements(By.xpath("//li[@class='ui-state-default ui-selectee ui-selected']"));
		Assert.assertEquals(allItemSelected.size(), 4);

		for (WebElement displayitemSelected : allItemSelected) {
			System.out.println(displayitemSelected.getText());
		}
	}
	//@Test
	public void TC_06_Drag_And_Drop_HTML_Nho_Hon_5() {
		driver.get("http://demos.telerik.com/kendo-ui/dragdrop/angular");
		WebElement sourceCircle = driver.findElement(By.xpath("//div[@id='draggable']"));
		WebElement targetCircle = driver.findElement(By.xpath("//div[@id='droptarget']"));
		sleepInSecond(2);
		action.dragAndDrop(sourceCircle, targetCircle).perform();
		sleepInSecond(2);
		Assert.assertEquals(targetCircle.getText(), "You did great!");
	}

	@Test
	public void TC_07_Drag_And_Drop_HTML5() throws InterruptedException, IOException {
		driver.get("http://the-internet.herokuapp.com/drag_and_drop");

		String sourceCss = "#column-a";
		String targetCss = "#column-b";
		System.out.println(rootFolder);
		String java_script = readFile(javascriptPath);
		System.out.println(javascriptPath);
		System.out.println(jQueryPath);
		//Inject Jquery khi web chưa có thì mở hàm này ra
		// Inject Jquery lib to site
		// String jqueryscript = readFile(jqueryPath);
		// javascriptExecutor.executeScript(jqueryscript);

		// chuyển từ A to B
		java_script = java_script + "$(\"" + sourceCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
		jsExecutor.executeScript(java_script);
		Thread.sleep(2000);
		Assert.assertTrue(checkisdisplay("//div[@id='column-a']/header[text()='B']"));

		// chuyển từ B to A
		java_script = "$(\"" + sourceCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
		jsExecutor.executeScript(java_script);
		Thread.sleep(3000);
		Assert.assertTrue(checkisdisplay("//div[@id='column-a']/header[text()='A']"));
	}
	
	//@Test
	public void TC_08_DragDrop_HTML5_Offset() throws InterruptedException, IOException, AWTException {
		driver.get("http://the-internet.herokuapp.com/drag_and_drop");
	
		String sourceXpath = "//div[@id='column-a']";
		String targetXpath = "//div[@id='column-b']";
		
		drag_the_and_drop_html5_by_xpath(sourceXpath, targetXpath);
		Thread.sleep(3000);
		
		drag_the_and_drop_html5_by_xpath(sourceXpath, targetXpath);
		Thread.sleep(3000);
		
		drag_the_and_drop_html5_by_xpath(sourceXpath, targetXpath);
		Thread.sleep(3000);
	}

	@AfterClass
	public void afterClass() {
	}
	public void sleepInSecond(long time) {
		try {
				Thread.sleep(time*1000);
			} catch (InterruptedException e){
					e.printStackTrace();;	
			}
	}
	
	public String readFile(String file) throws IOException {
		Charset cs = Charset.forName("UTF-8");
		FileInputStream stream = new FileInputStream(file);
		try {
			Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
			StringBuilder builder = new StringBuilder();
			char[] buffer = new char[8192];
			int read;
			while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
				builder.append(buffer, 0, read);
			}
			return builder.toString();
		} finally {
			stream.close();
		}
	}
	
	public boolean checkisdisplay(String locator) {
		WebElement displaylocator = driver.findElement(By.xpath(locator));
		if (displaylocator.isDisplayed()) {
			return true;
		}
		return false;
	}

	public void drag_the_and_drop_html5_by_xpath(String sourceLocator, String targetLocator) throws AWTException {

		WebElement source = driver.findElement(By.xpath(sourceLocator));
		WebElement target = driver.findElement(By.xpath(targetLocator));

		// Setup robot
		Robot robot = new Robot();
		robot.setAutoDelay(500);

		// Get size of elements
		Dimension sourceSize = source.getSize();
		Dimension targetSize = target.getSize();

		// Get center distance
		int xCentreSource = sourceSize.width / 2;
		int yCentreSource = sourceSize.height / 2;
		int xCentreTarget = targetSize.width / 2;
		int yCentreTarget = targetSize.height / 2;

		Point sourceLocation = source.getLocation();
		Point targetLocation = target.getLocation();
		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Make Mouse coordinate center of element
		sourceLocation.x += 20 + xCentreSource;
		sourceLocation.y += 110 + yCentreSource;
		targetLocation.x += 20 + xCentreTarget;
		targetLocation.y += 110 + yCentreTarget;

		System.out.println(sourceLocation.toString());
		System.out.println(targetLocation.toString());

		// Move mouse to drag from location
		robot.mouseMove(sourceLocation.x, sourceLocation.y);

		// Click and drag
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

		// Move to final position
		robot.mouseMove(targetLocation.x, targetLocation.y);

		// Drop
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
}
