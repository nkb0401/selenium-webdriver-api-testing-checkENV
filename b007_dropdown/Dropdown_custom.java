package b007_dropdown;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.GetAlertText;
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
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");
		driver = new ChromeDriver();
		/*
		 * System.setProperty("webdriver.gecko.driver",".\\lib\\Brower\\geckodriver.exe"
		 * ); driver = new FirefoxDriver();
		 */
		expliciWait = new WebDriverWait(driver, 30);
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
	}

	//@Test
	public void TC_01_Jquery() {
		driver.get("https://jqueryui.com/resources/demos/selectmenu/default.html");
		selecItemIncustomDropdown("//span[@id='number-button']", "//li[@class='ui-menu-item']/div", "13");
		sleepInSecond(1);
		Assert.assertEquals(driver
				.findElement(By.xpath("//span[@id='number-button']//span[@class='ui-selectmenu-text']")).getText(),
				"13");
		}
	//khi thao tác nó mới load data lên, do đó khi ko chọn thì trong DOM se ko có, du2mg debug de bat xpath
	//@Test
	public void TC_02_Angular() {
		driver.get("https://ej2.syncfusion.com/angular/demos/?_ga=2.262049992.437420821.1575083417-524628264.1575083417#/material/drop-down-list/data-binding");
		selecItemIncustomDropdown("//ejs-dropdownlist[@id='games']//span[@class='e-input-group-icon e-ddl-icon e-search-icon']", "//div[@id='games_popup']//li", "Hockey");
		Assert.assertEquals(getvalueofdropdown_Angular(),"Hockey");
	}
	
	//@Test
	public void TC_03_ReactJS() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-selection/");
		selecItemIncustomDropdown("//div[@class='ui fluid selection dropdown']","//div[@class='visible menu transition']//span", "Matt");
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Matt']")).isDisplayed());
	}
	
	//@Test
	public void TC_04_VueJS() {
		driver.get("https://mikerodham.github.io/vue-dropdowns/");
		selecItemIncustomDropdown("//li[@class='dropdown-toggle']","//ul[@class='dropdown-menu']//a", "Third Option");
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='dropdown-toggle']")).getText().trim(),"Third Option");
	}
	
	//@Test
	public void TC_05_editable() {
		driver.get("https://react.semantic-ui.com/maximize/dropdown-example-search-selection/");
		selecItemIncustomDropdown("//input[@class='search']","//div[@class='visible menu transition']/div", "Antigua");
		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Antigua']")).isDisplayed());
	}
	
	@Test
	public void TC_06_Advance() {
		driver.get("http://multiple-select.wenzhixin.net.cn/examples#basic.html");
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		String[] months = { "January", "August", "September" };
		selectMultiInDropDown("(//button[@class='ms-choice'])[1]", "(//div[@class='ms-drop bottom']/ul)[1]//span", months);
		Assert.assertTrue(checkMultiItemSelected(months));

		driver.navigate().refresh();
		driver.switchTo().frame(driver.findElement(By.tagName("iframe")));
		String[] months2 = { "March", "April", "September", "December" };
		selectMultiInDropDown("//option/parent::select/following-sibling::div", "//option/parent::select/following-sibling::div//label/span", months2);
		Assert.assertTrue(checkMultiItemSelected(months2));
	}
	
	public String getvalueofdropdown_Angular() {
		return (String) jsExecutor.executeScript("return document.querySelector(\"select[name='games'] option\").text");
	}
	public void selecItemIncustomDropdown(String parentxpath, String allitemxpath, String expectedvalueitem) {
		// 1. click vao element de show dropdown
		driver.findElement(By.xpath(parentxpath)).click();
		// 2. cho cac item load xong
		expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(allitemxpath)));
		// 3. luu all item vao 1 list
		List<WebElement> allitems = driver.findElements(By.xpath(allitemxpath));
		// 4. lấy text của từng item
		/*
		 * Cach 1: duyet tung item int allitemnuber = allitems.size(); for (int i =0; i
		 * < allitemnuber; i++) { String actualvalueitem = allitems.get(i).getText();
		 * //5. kiểm tra nó có bằng với text cần tìm hay không if
		 * (actualvalueitem.equals(expectedvalueitem) ) { //6. Nếu có thì click vào
		 * 
		 * allitems.get(i).click(); break;
		 * 
		 * } }
		 */

		// Cach 2: duyet tung item
		for (WebElement item : allitems) {
			if (item.getText().equals(expectedvalueitem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				item.click();
				break;
			}
		}
	}
	
	// Dùng có advance Multi Dropdown (TC_06)
	public void selectMultiInDropDown(String locatorMulti, String itemLocatorMulti, String[] ExpectedMulti) {
		// Click vào 1 thẻ bất kì
		driver.findElement(By.xpath(locatorMulti)).click();
		// Chờ cho tất cả item được xuất hiện
		expliciWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(itemLocatorMulti)));
		// Lấy hết tất cả các item đưa vào 1 list
		List<WebElement> allItems = driver.findElements(By.xpath(itemLocatorMulti));
		// Tổng số lượng item trong 1 dropdown
		System.out.println("Item size =" + allItems.size());
		// Duyệt qua từng item
		for (WebElement itemMulti : allItems) {
			// Duyệt qua từng item trong mảng String[]
			for (String itemExpectedString : ExpectedMulti) {
				// Verify với text mình cần click
				if (itemMulti.getText().equals(itemExpectedString)) {
					// Scroll trước khi click
					jsExecutor.executeScript("arguments[0].scrollIntoView(true);", itemMulti);
					sleepInSecond(2);
					// Click vào item cần chọn
					jsExecutor.executeScript("arguments[0].click();", itemMulti);
					sleepInSecond(2);
					List<WebElement> ItemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
					System.out.println("Item Selected = " + ItemSelected.size());
					if (ExpectedMulti.length == ItemSelected.size()) {
						break;
					}
				}
			}
		}
	}

	// Verify Multi Dropdown (TC_06)
	public boolean checkMultiItemSelected(String[] ItemMultiSelected) {
		List<WebElement> ItemSelected = driver.findElements(By.xpath("//li[@class='selected']//input"));
		// Số item được chọn = với số item
		int numberItemSelected = ItemSelected.size();

		// gettext trong dropdown
		String allItemSelectedText = driver.findElement(By.xpath("//button[@class='ms-choice']/span")).getText();
		System.out.println("Text is selected =" + allItemSelectedText);

		if (numberItemSelected <= 3 && numberItemSelected > 0) {
			for (String item : ItemMultiSelected) {
				if (allItemSelectedText.contains(item)) {
					break;
				}
			}
			// đúng và true khi nó chứa hết
			return true;
		}
		// Sai nếu >3 text trong dropdown là số
		else {
			return driver
					.findElement(By.xpath(
							"//button[@class='ms-choice']/span[text()='" + numberItemSelected + " of 12 selected']"))
					.isDisplayed();

		}
	}

	public void sleepInSecond(long timeInsecond) {
		try {
			Thread.sleep(timeInsecond * 1000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendkeydropdown(String locator, String value) {
		driver.findElement(By.xpath(locator)).clear();
		sleepInSecond(1);
		driver.findElement(By.xpath(locator)).sendKeys(value);
		sleepInSecond(1);
		driver.findElement(By.xpath(locator)).sendKeys(Keys.TAB);
		sleepInSecond(1);

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
