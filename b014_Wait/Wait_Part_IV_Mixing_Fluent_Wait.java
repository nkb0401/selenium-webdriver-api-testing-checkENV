package b014_Wait;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import com.google.common.base.Function;

public class Wait_Part_IV_Mixing_Fluent_Wait {

	WebDriver driver;
	FluentWait<WebElement> fluentelement;

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.chrome.driver", ".\\lib\\Brower\\chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	public void TC_01_Count_Down() {
		driver.get("https://automationfc.github.io/fluent-wait/");
		WebElement countdown = driver.findElement(By.xpath("//div[@id='javascript_countdown_time']"));
		fluentelement = new FluentWait<WebElement>(countdown);
		// Tổng thời gian chờ
		fluentelement.withTimeout(15, TimeUnit.SECONDS)
				// Tần số quết 1s 1 lần
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				// Nếu báo lỗi exception không tìm thấy element thì bỏ qua cho chạy tiếp
				.ignoring(NoSuchElementException.class)
				// kiểm tra điều kiện
				.until(new Function<WebElement, Boolean>() {
					public Boolean apply(WebElement countdown) {
						// kiểm tra điều kiện countdown = 00
						boolean flag = countdown.getText().endsWith("00");
						System.out.println("Time = " + countdown.getText());
						// trả lại giá trị cho function được apply
						return flag;
					}
				});
	}

	@Test
	public void TC_02_Loading() {
		driver.get("http://the-internet.herokuapp.com/dynamic_loading/2");
		driver.findElement(By.xpath("//button[contains(text(),'Start')]")).click();
		WebElement waitloading = driver.findElement(By.xpath("//div[h4]"));
		fluentelement = new FluentWait<WebElement>(waitloading);
		// Tổng thời gian chờ
		fluentelement.withTimeout(8, TimeUnit.SECONDS)
				// Tần số quết 1/10s 1 lần
				.pollingEvery(100, TimeUnit.MILLISECONDS)
				// Nếu báo lỗi exception không tìm thấy element thì bỏ qua cho chạy tiếp
				.ignoring(NoSuchElementException.class)
				// kiểm tra điều kiện
				.until(new Function<WebElement, Boolean>() {
					public Boolean apply(WebElement waitloading) {
						// kiểm tra điều kiện countdown = 00
						boolean flag = waitloading.getText().endsWith("Hello World!");
						// trả lại giá trị cho function được apply
						return flag;
					}
				});
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}