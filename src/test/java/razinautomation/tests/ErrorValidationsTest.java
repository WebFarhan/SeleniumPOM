package razinautomation.tests;

import org.testng.annotations.Test;

import org.testng.AssertJUnit;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import razinautomation.TestComponents.BaseTest;
import razinautomation.TestComponents.Retry;
import razinautomation.pageobjects.CartPage;
import razinautomation.pageobjects.CheckoutPage;
import razinautomation.pageobjects.ConfirmationPage;
import razinautomation.pageobjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest{
	

	@Test(groups= {"ErrorHandling"}, retryAnalyzer = Retry.class)
	public void loginErrorValidation () throws IOException {
		
		landingPage.loginApplication("razin.qa@example.com", "Justdoit@123s");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());// test will fail - to see screenshot

	}
	
	@Test
	public void ProductErrorValidation() throws IOException {
		String productName = "ZARA COAT 3";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("razin.qa@example.com", "Justdoit@123");
		
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
		Assert.assertFalse(match);
		
		
		
	}

}
