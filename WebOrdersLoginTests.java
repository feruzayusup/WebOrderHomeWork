package pom;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.WebOrdersLoginPage;

public class WebOrdersLoginTests {

WebDriver driver;
	
	@BeforeClass
	public void setup() {
		
		WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
	}
		@Ignore
		@Test
		public void positiveLoginTest() {
			driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
			driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
			driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
			driver.findElement(By.id("ctl00_MainContent_login_button")).click();

		}
		
		@Test
		public void positiveLoginUsingPOM() {
			driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
			
			//creating object from WebOrdersLoginPage class
			WebOrdersLoginPage loginPage = new WebOrdersLoginPage(driver);
			loginPage.userName.sendKeys("Tester");
			loginPage.password.sendKeys("test");
			loginPage.loginButton.click();
		}
		
		@Test(priority=1)
		public void invalidUsernameTest() {
			driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
			WebOrdersLoginPage loginPage = new WebOrdersLoginPage(driver);
			loginPage.userName.sendKeys("invalid");
			loginPage.password.sendKeys("test");
			loginPage.loginButton.click();
			String errMsg = loginPage.invalidUserNameErrMsg.getText();
			
			assertEquals(errMsg, "Invalid Login or Password.");
			
		}
	
	
	
}
