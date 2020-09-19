package Check_Environment;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Topic_01_Check_Environment {
static WebDriver driver;
	
	public static void main (String[] args) {
		System.setProperty("webdriver.gecko.driver",".\\lib\\Brower\\geckodriver.exe");
		
		driver = new FirefoxDriver();
		driver.get("https://www.facebook.com/");
	}
}
