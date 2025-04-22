package razinautomation.stepDefinition;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import razinautomation.TestComponents.BaseTest;
import razinautomation.pageobjects.CartPage;
import razinautomation.pageobjects.CheckoutPage;
import razinautomation.pageobjects.ConfirmationPage;
import razinautomation.pageobjects.LandingPage;
import razinautomation.pageobjects.ProductCatalogue;

public class StepDefinitionImpl extends BaseTest{

	public LandingPage landingPage;
	public ProductCatalogue productCatalogue;
	public ConfirmationPage confirmationPage;
	
	@Given("I landed on Ecommerece Page")
	public void I_landed_on_Ecommerece_Page() throws IOException {
		//code
		landingPage = launchApplication();
	}
	
	@Given("^Logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password) {
		productCatalogue = landingPage.loginApplication(username,password);
	}
	
	 
	 @When("^I add product (.+) to Cart$")
	 public void I_add_product_to_Cart(String productName) {
		 List<WebElement> products = productCatalogue.getProductList();
		 productCatalogue.addProductToCart(productName);
	 }
	 
	 
	 @When("^Checkout (.+) and submit the order$")
	 public void Checkout_and_submit_the_order(String productName) {
		 CartPage cartPage = productCatalogue.goToCartPage();
		 Boolean match = cartPage.VerifyProductDisplay(productName);
		 Assert.assertTrue(match);
		 CheckoutPage checkoutPage = cartPage.goToCheckout();
		 checkoutPage.selectCountry("Bangladesh");
		 confirmationPage = checkoutPage.submitOrder();
	 }
	 
	 //"Thankyou for the order." message is displayed on ConfirmationPage
	 @Then("{string} message is displayed on ConfirmationPage")
	 public void message_is_displayed_on_ConfirmationPage(String string) {
		 String confirmMessage = confirmationPage.getConfirmationMessage();
		 Assert.assertTrue(confirmMessage.equalsIgnoreCase(string));
		 driver.close();
	 }
	 
	 @Then("^\"([^\"]*)\" message is displayed$")
	 public void something_message_is_displayed(String strArg1) {
		
		 Assert.assertEquals(strArg1, landingPage.getErrorMessage());// test will fail
		 driver.close();
	 }
	 
	 
	 
	 
}
