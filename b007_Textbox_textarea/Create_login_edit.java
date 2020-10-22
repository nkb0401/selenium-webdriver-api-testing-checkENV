package b007_Textbox_textarea;

import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class Create_login_edit {
	WebDriver driver;
	String email, userid, pass, loginURL, customerID;
	String name, dob, addr, city, state, pinno, tele, pass1;
	String addEdit,	cityEdit,	stateEdit,	pinEdit,	phoneEdit;
	By nameTextBox = By.xpath("//input[@name='name']");
	By genderTextBox = By.xpath("//input[@name='gender']");
	By dateOfBirthTextBox = By.xpath("//input[@name='dob']");
	By addressTextBox = By.xpath("//textarea[@name='addr']");
	By cityTextBox = By.xpath("//input[@name='city']");
	By stateTextBox = By.xpath("//input[@name='state']");
	By pinTextBox = By.xpath("//input[@name='pinno']");
	By phoneTextBox = By.xpath("//input[@name='telephoneno']");
	By emailTextBox = By.xpath("//input[@name='emailid']");
	By passwordTextBox = By.xpath("//input[@name='password']");

	@BeforeClass
	public void beforeClass() {
		System.setProperty("webdriver.gecko.driver", ".\\lib\\Brower\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://demo.guru99.com/v4");
		

		email = createEmail();
		name = "Bangkhoa";
		dob = "2020-04-01";
		addr = "123 dfsd";
		city = "SAi gon";
		state = "VN";
		pinno = "123331";
		tele = "123456789";
		pass1 = "123";
				
		addEdit = "Quang Trung";
		cityEdit = "HANOI";
		stateEdit = "USA";
		pinEdit = "090909";
		phoneEdit = "022222224";		
		
	}

	@Test
	public void TC_01_register() {
		loginURL = driver.getCurrentUrl();
		driver.findElement(By.xpath("//a[text()='here']")).click();
		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("btnLogin")).click();
		userid = driver.findElement(By.xpath("//td[text()='User ID :']/following-sibling::td")).getText();
		pass = driver.findElement(By.xpath("//td[text()='Password :']/following-sibling::td")).getText();
	}

	@Test
	// user:mngr287795; pass:vynUtAq
	public void TC_02_login() {
		driver.get(loginURL);
		driver.findElement(By.name("uid")).sendKeys(userid);
		driver.findElement(By.name("password")).sendKeys(pass);
		driver.findElement(By.name("btnLogin")).click();
		// Verify login thanh cong
		Assert.assertTrue(driver.findElement(By.xpath("//tr[@class='heading3']/td[text()='Manger Id : " + userid + "']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//marquee[text()=\"Welcome To Manager's Page of Guru99 Bank\"]")).isDisplayed());
	}

	@Test
	public void TC_03_create_customer() {
		driver.findElement(By.xpath("//a[text()='New Customer']")).click();
		driver.findElement(By.name("name")).sendKeys(name);
		driver.findElement(By.xpath("//input[@value='f']")).click();		
		driver.findElement(By.name("dob")).sendKeys(dob);
		driver.findElement(By.name("addr")).sendKeys(addr);
		driver.findElement(By.name("city")).sendKeys(city);
		driver.findElement(By.name("state")).sendKeys(state);
		driver.findElement(By.name("pinno")).sendKeys(pinno);
		driver.findElement(By.name("telephoneno")).sendKeys(tele);
		driver.findElement(By.name("emailid")).sendKeys(email);
		driver.findElement(By.name("password")).sendKeys(pass1);
		driver.findElement(By.name("sub")).click();
		// Verify 
		Assert.assertTrue(driver.findElement(By.xpath("//p[text()='Customer Registered Successfully!!!']")).isDisplayed());
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Customer Name']/following-sibling::td")).getText(), name);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Gender']/following-sibling::td")).getText(), "female");
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Birthdate']/following-sibling::td")).getText(), dob);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Address']/following-sibling::td")).getText(), addr);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='City']/following-sibling::td")).getText(), city);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='State']/following-sibling::td")).getText(), state);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Pin']/following-sibling::td")).getText(), pinno);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Mobile No.']/following-sibling::td")).getText(), tele);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='Email']/following-sibling::td")).getText(), email);

		customerID = driver.findElement(By.xpath("//td[text()='Customer ID']/following-sibling::td")).getText();
		System.out.println(customerID);
	}

	@Test
	public void TC_04_edit_customer() {
		driver.findElement(By.xpath("//a[text()='Edit Customer']")).click();
		driver.findElement(By.xpath("//input[@name='cusid']")).sendKeys(customerID);
		driver.findElement(By.xpath("//input[@name='AccSubmit']")).click();		
		
		
		Assert.assertFalse(driver.findElement(nameTextBox).isEnabled());
		Assert.assertFalse(driver.findElement(genderTextBox).isEnabled());
		Assert.assertFalse(driver.findElement(dateOfBirthTextBox).isEnabled());
		
		driver.findElement(stateTextBox).clear();
		driver.findElement(stateTextBox).sendKeys(stateEdit);
		driver.findElement(pinTextBox).clear();
		driver.findElement(pinTextBox).sendKeys(pinEdit);
		driver.findElement(phoneTextBox).clear();
		driver.findElement(phoneTextBox).sendKeys(phoneEdit);
		driver.findElement(emailTextBox).clear();
		driver.findElement(emailTextBox).sendKeys(email);
		driver.findElement(By.xpath("//input[@name='sub']")).click();
		Assert.assertTrue((driver.findElement(By.xpath("//p[text()='Customer details updated Successfully!!!']")).isDisplayed()));
	}


	@AfterClass
	public void afterClass() {
		driver.quit();
	}

	public String createEmail() {
		Random rand = new Random();
		return "email" + rand.nextInt(999) + "@abc.com";
	}

}
