package razinautomation.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import razinautomation.AbstractComponents.AbstractComponent;

public class CheckoutPage extends AbstractComponent{

	WebDriver driver;
	
	public CheckoutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	//driver.findElement(By.cssSelector(".ta-item")).click();
	
	
	@FindBy(css=".action__submit")
	private WebElement submit;
	
	@FindBy(css="input[placeholder='Select Country']")
	private WebElement country;
	
	@FindBy(css=".ta-item")
	private WebElement selectCountry;
	
	private By results = By.cssSelector(".ta-results");
	
	
	public void selectCountry(String countryName) {
		
		Actions a = new Actions(driver);
		a.sendKeys(country, countryName).build().perform();
		waitForElementToAppear(results);
		selectCountry.click();
		
	}
	
	public ConfirmationPage submitOrder() {
		submit.click();
		return new ConfirmationPage(driver);
	}

}
