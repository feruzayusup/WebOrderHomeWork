package pom;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.AllOrderPage;
import pages.InformationProductPage;
import pages.ProductsPage;
import pages.WebOrdersLoginPage;

public class WebOrderTest {

	WebDriver driver;
	WebOrdersLoginPage loginPage;
	AllOrderPage allOrdersPage;
	InformationProductPage informationProductPage;
	ProductsPage productsPage;
	String userId = "Tester";
	String password = "test";
	Faker data = new Faker();
	
	@BeforeClass //runs once for all tests
	public void setUp() {
		System.out.println("Setting up WebDriver in BeforeClass...");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().fullscreen();
	}
	
	@BeforeMethod
	public void setUpApplication() {
		driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
		loginPage = new WebOrdersLoginPage(driver);
	}
	
	@Test(description="Verify labels and tab links are displayed",priority=1)
	public void labelsVerication() {
		assertEquals(driver.getTitle(),"Web Orders Login","LoginPage is not displayed. Application is down.");
		/*  loginPage.userName.sendKeys(userId);
			loginPage.password.sendKeys(password);
		    loginPage.loginButton.click();
		*/
		loginPage.login(userId, password);
		
		allOrdersPage = new AllOrderPage(driver);
		assertTrue(allOrdersPage.webOrders.isDisplayed(),"Web Orders is not displayed");
		assertTrue(allOrdersPage.listOfAllOrders.isDisplayed(),"List Of All Orders label is not displayed");
		assertEquals(allOrdersPage.welcomeMsg.getText().replace(" | Logout", ""),"Welcome, " + userId + "!");
		assertTrue(allOrdersPage.viewAllOrders.isDisplayed(),"viewAllOrders is not displayed");
		assertTrue(allOrdersPage.orderTab.isDisplayed(),"orderTab is not displayed");
	}
	
	/*
	 *  Step 1. Navigate to loginpage
		Step 2. Assert that you are on loginpage
		Step 3. Login using valid credentials
		Step 4. Click on view all products
		Step 5. Verify following products are displayed:
				MyMoney
				FamilyAlbum
				ScreenSaver
		Step 6. Verify prices and discounts :
				MyMoney	    $100 8%
				FamilyAlbum	$80	15%
				ScreenSaver	$20	10%
	 */
	@Test(description="Verify default Products and prices")
	public void availableProductsTest() {
		assertEquals(driver.getTitle(),"Web Orders Login","LoginPage is not displayed. Application is down.");
		loginPage.login(userId, password);
		allOrdersPage = new AllOrderPage(driver);
		allOrdersPage.viewAllProducts.click();
		productsPage = new ProductsPage(driver);
		List<String> expProducts = Arrays.asList("MyMoney","FamilyAlbum","ScreenSaver");
		List<String> actProducts = new ArrayList<>();		
		
//		productsPage.productNames.forEach(
//				elem -> actProducts.add(elem.getText())
//		);
		for(WebElement prod : productsPage.productNames) {
			actProducts.add(prod.getText());
		}
		assertEquals(actProducts,expProducts);
		
		for (WebElement row : productsPage.productsRows) {

			System.out.println(row.getText());
			String[] prodData = row.getText().split(" ");
			switch(prodData[0]) {
				case "MyMoney":
					assertEquals(prodData[1],"$100");
					assertEquals(prodData[2],"8%");
					break;
				case "FamilyAlbum":
					assertEquals(prodData[1],"$80");
					assertEquals(prodData[2],"15%");
					break;
				case "ScreenSaver":
					assertEquals(prodData[1],"$20");
					assertEquals(prodData[2],"10%");
					break;		
			}
			
		}
		
	} 
	
	
	
	//==============================   HomeWork    ============================================
	
	@Test (description="Fill up the Personal Information and submit", priority = 3)
		public void productInformation() throws InterruptedException {
		loginPage.login(userId, password);
		informationProductPage = new InformationProductPage (driver);
		informationProductPage.order.click();
		Select list = new Select(informationProductPage.threeSelection);
		
		list.selectByIndex(data.number().numberBetween(0, 2));
		Thread.sleep(2000);
		String quant = (data.number().numberBetween(1, 100)) + "";
		informationProductPage.quantity.clear();
		Thread.sleep(400);
		informationProductPage.quantity.sendKeys(quant);
		informationProductPage.calculateButton.click();
		
		informationProductPage.customerName.sendKeys(data.name().fullName());
		informationProductPage.street.sendKeys(data.address().streetAddress());
		informationProductPage.city.sendKeys(data.address().cityName());
		informationProductPage.state.sendKeys(data.address().state());
		informationProductPage.zipCode.sendKeys(data.number().numberBetween(10000, 99999) + "");
		Thread.sleep(500);
		switch(data.number().numberBetween(1, 3)) {
		case 1:
			informationProductPage.MC.click();
			break;
		case 2:
			informationProductPage.AE.click();
			break;
		case 3:
			informationProductPage.visa.click();
			break;		
		}
		
		String cardnum = data.number().numberBetween(001000000000, 001234567654 ) +"";
		informationProductPage.cardNumber.sendKeys(cardnum);
		String month = data.number().numberBetween(11, 30)+"";
		String year = data.number().numberBetween(18, 26)+"";
		informationProductPage.expireDate.sendKeys(month + "/" + year);
		informationProductPage.processButton.click();

	}

	
	@AfterMethod
	public void logout() {
		allOrdersPage.logout();
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}

