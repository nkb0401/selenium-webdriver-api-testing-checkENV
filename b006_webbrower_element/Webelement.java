package b006_webbrower_element;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Webelement {
	WebDriver driver;
	String project_location=System.getProperty("user.dir");
	
	By emailTextboxby = By.id("mail");
	By educationTextboxby = By.id("edu");
	By AgeUnder18Radioby = By.id("under_18");
	By jobRoledropdown = By.id("job2");
	By interestCheckbox = By.id("development");
	By slider = By.id("slider-1");
	
	By passwordtextbox = By.id("password");
	By ageradio = By.id("radio-disabled");
	By biotextarea = By.id("bio");
	By job3dropdown = By.id("job3");
	By disablecheckbox = By.id("check-disbaled");
	By slider2 = By.id("slider-2");
	By javaCheckbox = By.id("java");
	
	@BeforeClass
	public void beforeClass() {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver", project_location + "\\lib\\Brower\\chromedriver.exe");
		  driver = new ChromeDriver();
		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  driver.manage().window().maximize();
		
	}
	
	@Test
	public void TC_01_Element_display() throws InterruptedException {
		// TODO Auto-generated method stub
		driver.get("https://automationfc.github.io/basic-form/index.html");
		
		//email textbox
		if(driver.findElement(emailTextboxby).isDisplayed())
		{
			System.out.println("Element is displayed");
			driver.findElement(emailTextboxby).sendKeys("Automation Testing");
		} else {
			System.out.println("Element is not displayed");
		}
		
		Thread.sleep(2000);
		
		// education textbox
		if(driver.findElement(educationTextboxby).isDisplayed())
		{
			System.out.println("Element is displayed");
			driver.findElement(educationTextboxby).sendKeys("Automation Testing");
		} else {
			System.out.println("Element is not displayed");
		}
		
		Thread.sleep(2000);
		
		// Age (under 18) radio button
		// if element ko display co the vao else ko? refer case 1, case 2
				if(driver.findElement(AgeUnder18Radioby).isDisplayed())
				{
					System.out.println("Element is displayed");
					driver.findElement(AgeUnder18Radioby).click();
				} else {
					System.out.println("Element is not displayed");
				}
				
				Thread.sleep(2000);
				//case 1 - ko display (in DOM)
				  
				  if (driver.findElement(By.xpath("//h5[text()='Name: User4']")).isDisplayed()) {
					  System.out.println("Element is displayed");
					  System.out.println(driver.findElement(By.xpath("//h5[text()='Name: User4']")).getText());
				  }
				  else {System.out.println("Case 1 Element is NOT displayed");}
				  Thread.sleep(3000);
				  
				//case 2 - ko display (NOT in DOM)
				   if (driver.findElement(By.id("und")).isDisplayed()) {
					  System.out.println("Element is displayed");
					  driver.findElement(By.id("under_18")).click();
				  }
				  else {System.out.println("Element is NOT displayed");}
				  Thread.sleep(3000);
	}
	
	@Test
	public void TC_02_Element_Displayed_refactorcodeTC01() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.navigate().refresh();
		
		if(isElementDisplayed(emailTextboxby))
		{
			driver.findElement(emailTextboxby).sendKeys("Automation Testing");
		}
		
		if(isElementDisplayed(educationTextboxby))
		{
			driver.findElement(educationTextboxby).sendKeys("Automation Testing");
		}
		
		if(isElementDisplayed(AgeUnder18Radioby))
		{
			driver.findElement(AgeUnder18Radioby).click();
		}
	}
	
	@Test
	public void TC_03_Element_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.navigate().refresh();
		
		Assert.assertTrue(isElementEnabled(emailTextboxby));
		Assert.assertTrue(isElementEnabled(educationTextboxby));
		Assert.assertTrue(isElementEnabled(AgeUnder18Radioby));
		Assert.assertTrue(isElementEnabled(jobRoledropdown));
		Assert.assertTrue(isElementEnabled(interestCheckbox));
		Assert.assertTrue(isElementEnabled(slider));
		
		Assert.assertFalse(isElementEnabled(passwordtextbox));
		Assert.assertFalse(isElementEnabled(ageradio));
		Assert.assertFalse(isElementEnabled(biotextarea));
		Assert.assertFalse(isElementEnabled(job3dropdown));
		Assert.assertFalse(isElementEnabled(disablecheckbox));
		Assert.assertFalse(isElementEnabled(slider2));
			
	}
	
	@Test
	public void TC_04_Selected() throws InterruptedException {
		driver.get("https://automationfc.github.io/basic-form/index.html");
		driver.navigate().refresh();
		
		driver.findElement(AgeUnder18Radioby).click();
		driver.findElement(javaCheckbox).click();
		
		Thread.sleep(2000);
		
		Assert.assertTrue(isElementSelected(AgeUnder18Radioby));
		Assert.assertTrue(isElementSelected(javaCheckbox));
		
		// Click to unselected
		driver.findElement(javaCheckbox).click();
		
		Thread.sleep(2000);
		
		Assert.assertFalse(isElementSelected(javaCheckbox));
		
	}
	
	@Test
	public void TC_05_Register() throws InterruptedException {
		driver.get("https://login.mailchimp.com/signup/");
		driver.navigate().refresh();
		
		driver.findElement(By.id("email")).sendKeys("automationtest20@gmail.com");
		driver.findElement(By.id("new_username")).sendKeys("automationtest20");
		
		// Sign up button is disable
		Assert.assertFalse(isElementEnabled(By.id("create-account")));
		
		//Lower case
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("auto");
		Thread.sleep(1000);
		Assert.assertFalse(isElementEnabled(By.id("create-account")));		
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed']")));
		
		//Uper case
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("Auto");
		Thread.sleep(1000);
		Assert.assertFalse(isElementEnabled(By.id("create-account")));	
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='uppercase-char completed']")));
		
		//Number
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("Auto1");
		Thread.sleep(1000);
		Assert.assertFalse(isElementEnabled(By.id("create-account")));	
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='uppercase-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='number-char completed']")));
		
		//Special char
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("Auto1!");
		Thread.sleep(1000);
		Assert.assertFalse(isElementEnabled(By.id("create-account")));	
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='uppercase-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='number-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='special-char completed']")));
		
		//8 Char
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("Auto1235");
		Thread.sleep(1000);
		Assert.assertFalse(isElementEnabled(By.id("create-account")));	
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='uppercase-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='number-char completed']")));
		Assert.assertTrue(isElementDisplayed(By.xpath("//li[@class='8-char completed']")));
		
		//full condition
		driver.findElement(By.id("new_password")).clear();
		driver.findElement(By.id("new_password")).sendKeys("Auto123!");
		Thread.sleep(1000);
		Assert.assertTrue(isElementEnabled(By.id("create-account")));	
		Assert.assertTrue(isElementDisplayed(By.xpath("//h4[text()=\"Your password is secure and you're all set!\"]")));
		
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='lowercase-char completed']")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='uppercase-char completed']")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='number-char completed']")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='8-char completed']")));
		Assert.assertFalse(isElementDisplayed(By.xpath("//li[@class='special-char completed']")));
	}
	
	//common display
	public boolean isElementDisplayed(By by) {
		if(driver.findElement(by).isDisplayed())
		{
			System.out.println("Element is displayed");
			return true;
		} else {
			System.out.println("Element is not displayed");
			return false;
		}
	} 
	
	//common enable
	public boolean isElementEnabled(By by) {
			if(driver.findElement(by).isEnabled())
			{
				System.out.println("Element is enabled");
				return true;
			} else {
				System.out.println("Element is disable");
				return false;
			}
		} 
	
	public boolean isElementSelected(By by) {
		if(driver.findElement(by).isSelected())
		{
			System.out.println("Element is selected");
			return true;
		} else {
			System.out.println("Element is unselected");
			return false;
		}
	}
	
		
	//common selected
	public boolean isSelected(By by) {
					if(driver.findElement(by).isSelected())
					{
						System.out.println("Element is selected");
						return true;
					} else {
						System.out.println("Element is unselected");
						return false;
					}
				} 
	
	@AfterClass
	public void afterClasss() {
		// TODO Auto-generated method stub
		driver.quit();
		
	}
}