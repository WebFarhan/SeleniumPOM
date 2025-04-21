package razinautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import razinautomation.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		super(driver);
		//initialize driver
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	//PageFactory
	
	@FindBy(id="userEmail")
	WebElement userEmail;
	
	@FindBy(id="userPassword")
	WebElement userPassword;
	
	@FindBy(id="login")
	WebElement submitLogin;
	
	@FindBy(css="[class*='flyInOut']")
	WebElement errorMessage;
	
	
	public String getErrorMessage() 
	{
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}
	
	/**
	 * This method take user inputs and login the user to product catalog page
	 * @param email
	 * @param password
	 * @return 
	 */
	public ProductCatalogue loginApplication(String email, String password) 
	{
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submitLogin.click();
		ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		return productCatalogue;
	}
	
	/**
	 * This method redirect to landing page
	 */
	public void goToLandingPage() 
	{
		driver.get("https://rahulshettyacademy.com/client");
	}
	
}
