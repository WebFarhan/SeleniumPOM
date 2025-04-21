package razinautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import razinautomation.AbstractComponents.AbstractComponent;

public class LandingPageDashboard extends AbstractComponent{

	WebDriver driver;
	
	public LandingPageDashboard(WebDriver driver) {
		super(driver);
		//initialize driver
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//WebElement userEmail = driver.findElement(By.id("userEmail"));
	//PageFactory
	
	@FindBy(id="email")
	WebElement userEmail;
	
	@FindBy(id="password")
	WebElement userPassword;
	
	@FindBy(xpath="//button[contains(.,'Submit')]")
	WebElement submitLogin;
	
	@FindBy(xpath="//button[text()=' Sign out ']")
	WebElement signOut;
	
	@FindBy(css="div[class='section-header'] button[class='basic mini-new black-chocolate']")
	WebElement bookScan;
	
	@FindBy(css="div[class='section-header']")
	WebElement dashboardVerify;
	
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
	public void loginMemberPortal(String email, String password) 
	{
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		submitLogin.click();
		//ProductCatalogue productCatalogue = new ProductCatalogue(driver);
		//return productCatalogue;
	}
	
	public void startBookingProcess() {
		waitForWebElementToAppear(bookScan);
		bookScan.click();
	}
	
	public void memberSignOut() {
		waitForWebElementToAppear(signOut);
		signOut.click();
	}
	
	public void verifydashBoard() {
		waitForWebElementToAppear(dashboardVerify);
		String appointmentText = dashboardVerify.getText();
		Assert.assertTrue(appointmentText.contains("Appointments"));
	}
	
	
	/**
	 * This method redirect to landing page
	 */
	public void goToLandingPage() 
	{
		driver.get("https://myezra-staging.ezra.com/sign-in");
	}
	
}
