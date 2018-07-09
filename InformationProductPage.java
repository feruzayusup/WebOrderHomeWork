package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InformationProductPage {
	
	public InformationProductPage (WebDriver driver) {
		PageFactory.initElements(driver, this);   
	}
	@FindBy(xpath="//a[.='Order']")        
	public WebElement order;

	
	@FindBy(id="ctl00_MainContent_fmwOrder_ddlProduct")        //3 selection myMone1, FamilyAlbum, ScreenSaver
	public WebElement threeSelection;
	
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtQuantity")        
	public WebElement quantity;
	
//	@FindBy(id="ctl00_MainContent_fmwOrder_txtUnitPrice")        
//	public WebElement pricePerUnit;
	
	@FindBy(xpath="//input[@value='Calculate']")        
	public WebElement calculateButton;
	
	
	@FindBy(id="ctl00_MainContent_fmwOrder_txtName")        
	public WebElement customerName;
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox2")        
	public WebElement street;
	
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox3")        
	public WebElement city;
	
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox4")        
	public WebElement state;
	
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox5")        
	public WebElement zipCode;
	
	
	@FindBy(xpath="//input[@value='American Express']")      
	public WebElement AE;
	
	@FindBy(xpath="//input[@value='MasterCard']")      
	public WebElement MC;
	
	@FindBy(xpath="//input[@value='Visa']")      
	public WebElement visa;
	
		
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox6")        
	public WebElement cardNumber;
	
	
	@FindBy(id="ctl00_MainContent_fmwOrder_TextBox1")        
	public WebElement expireDate;
	
	
	@FindBy(id="ctl00_MainContent_fmwOrder_InsertButton")        
	public WebElement processButton;
	
	
	
	
}
