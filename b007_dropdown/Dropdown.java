// Default dropdown list: the html = select -> option -> co the su dung selenium xu ly
package b007_dropdown;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

public class Dropdown {
	WebDriver driver;
	Select select;
	String project_location = System.getProperty("user.dir");
	String fristname, lastname, email, company, date, moth, year, pass, confirmpassA;
	
	By genderMaleby = By.id("gender-male");
	By fristnameby = By.id("FirstName");
	By lastnameby = By.id("LastName");
	By emailby = By.id("Email");
	By companyby = By.id("Company");
	By dateby = By.name("DateOfBirthDay");
	By mothby = By.name("DateOfBirthMonth");
	By yearby = By.name("DateOfBirthYear");
	By passby = By.id("Password");
	By confirmpassby = By.id("ConfirmPassword");
	//data	

	
	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", ".\\lib\\Brower\\geckodriver.exe");

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://demo.nopcommerce.com/register");
		//data
		fristname ="BANG";
		lastname = "KHOA";
		email= "abs3@gmail.com";
		company = "1234fsd";
		date= "3";
		moth = "March";
		year= "1988";
		pass = "12345678";
		confirmpassA = "12345678";
	}

	@Test

	public void TC_01_register() {
		driver.findElement(genderMaleby).click();
		driver.findElement(fristnameby).sendKeys(fristname);
		driver.findElement(lastnameby).sendKeys(lastname);
		
		//--------------------------------------------------------------
		// select day
		select = new Select(driver.findElement(dateby));
		// verify multity dropdown
		Assert.assertFalse(select.isMultiple());
		// chọn
		select.selectByIndex(10);
		select.selectByValue("15");
		select.selectByVisibleText("3");
		// verify ite, selected
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);
		// verify all item in dropdown
		Assert.assertEquals(select.getOptions().size(), 32);
		//----------------------------------------------------------------------
		
		// chon moth
		select = new Select(driver.findElement(mothby));
		select.selectByVisibleText(moth);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), moth);
		Assert.assertEquals(select.getOptions().size(), 13);

		// chon year
		select = new Select(driver.findElement(yearby));
		select.selectByVisibleText(year);
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);
		Assert.assertEquals(select.getOptions().size(), 112);
		
		driver.findElement(emailby).sendKeys(email);
		driver.findElement(companyby).sendKeys(company);
		driver.findElement(passby).sendKeys(pass);
		driver.findElement(confirmpassby).sendKeys(confirmpassA);
		
		driver.findElement(By.id("register-button")).click();
		//Verify create acc thanh cong
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='result']")).getText(), "Your registration completed");
		driver.findElement(By.xpath("//a[@class='ico-account']")).click();
		// verify inputed is true
		select = new Select(driver.findElement(dateby));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), date);
		select = new Select(driver.findElement(mothby));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), moth);
		select = new Select(driver.findElement(yearby));
		Assert.assertEquals(select.getFirstSelectedOption().getText(), year);

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

}
