package b001_Check_Environment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Topic_01_Check_Environment {
	static WebDriver driver;

	public static void main (String[] args) {
		System.setProperty("webdriver.gecko.driver",".\\lib\\Brower\\geckodriver.exe");
		
		System.setProperty("webdriver.chrome.driver",".\\lib\\Brower\\chromedriver.exe");
		
		driver = new FirefoxDriver();
		driver = new ChromeDriver();
		driver.get("https://www.facebook.com/");
	}
}
