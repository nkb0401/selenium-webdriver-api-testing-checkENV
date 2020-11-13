package b010_user_interactions;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
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
	
	
	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
		action = new Actions(driver);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void TC01_hover() {
		driver.get("https://tiki.vn/");
		sleepInSecond(6);
		driver.findElement(By.xpath("//button[@id='onesignal-slidedown-cancel-button']")).click();;
		shortcut = driver.findElement(By.xpath("//span[@class='Userstyle__NoWrap-sc-6e6am-11 cbYxcp']"));
		action.moveToElement(shortcut).perform();
	}
	@Test
	public void TC_02_Hover_Mouse() {
		driver.get("http://www.myntra.com/");
		element = driver.findElement(By.xpath("//a[@class='desktop-main'][contains(text(),'Kids')]"));
		// di chuyển chuột tới 1 vị trí
		action.moveToElement(element).perform();
		driver.findElement(By.xpath("//a[contains(text(),'Home & Bath')]")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//span[@class='breadcrumbs-crumb']")).getText(), "Kids Home Bath");

	}
	
	@Test
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
	@Test
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
	
	@Test
	public void TC04_hover() {
	}
	@Test
	public void TC05_hover() {
	}
	@Test
	public void TC06_hover() {
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

}
