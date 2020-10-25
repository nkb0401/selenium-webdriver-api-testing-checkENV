package b007_dropdown;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import java.util.List;

public class Dropdown_custom {
	WebDriver driver;
	WebDriverWait expliciWait;
	JavascriptExecutor jsExecutor;
	Select select;
	String project_location = System.getProperty("user.dir");

	// data

	@BeforeClass
	public void beforeClass() {
		/*System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();*/
		System.setProperty("webdriver.gecko.driver",".\\lib\\Brower\\geckodriver.exe");
		driver = new FirefoxDriver();
		expliciWait = new WebDriverWait(driver, 30);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");

	}

	@Test

	public void TC_01_Jquery() {
		selecItemIncustomDropdown("//span[@id='number-button']","//li[@class='ui-menu-item']/div","13");
		sleepInSecond(1);
		Assert.assertEquals(driver.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(), "13");
		
		selecItemIncustomDropdown("//span[@id='number-button']","//ul[@id='number-menu']//div","15");
		
	}

	public void selecItemIncustomDropdown(String parentxpath,String allitemxpath, String expectedvalueitem) {
		//1. click vao element de show dropdown
		driver.findElement(By.xpath(parentxpath)).click();
		//2. cho cac item load xong
		expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allitemxpath)));
		//3. luu all item vao 1 list
		List<WebElement> allitems = driver.findElements(By.xpath(allitemxpath));
		//4. lấy text của từng item
		/*Cach 1: duyet tung item
		int allitemnuber = allitems.size();			
		for (int i =0; i < allitemnuber; i++)
		{
			String actualvalueitem = allitems.get(i).getText();
			//5. kiểm tra nó có bằng với text cần tìm hay không
			if (actualvalueitem.equals(expectedvalueitem) ) {
				//6. Nếu có thì click vào
				
				allitems.get(i).click();
					break;
				
			}
		}*/
		
		//Cach 2: duyet tung item
		for (WebElement item : allitems) {
			if (item.getText().equals(expectedvalueitem)) {
			jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				item.click();
				break;
			}
		}
	}
	
		
		
		
	public void sleepInSecond(long timeInsecond) {
		try {
			Thread.sleep(timeInsecond*1000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	

	@AfterClass
	public void afterClass() {
		//driver.quit();
	}

}
