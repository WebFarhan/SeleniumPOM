package razinautomation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import io.github.bonigarcia.wdm.WebDriverManager;
import razinautomation.TestComponents.BaseTest;
import razinautomation.pageobjects.CartPage;
import razinautomation.pageobjects.CheckoutPage;
import razinautomation.pageobjects.ConfirmationPage;
import razinautomation.pageobjects.LandingPage;
import razinautomation.pageobjects.OrderPage;
import razinautomation.pageobjects.ProductCatalogue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SubmitOrderTest extends BaseTest{
	String productName = "ZARA COAT 3";

	@Test(dataProvider="getData",groups= {"Purchase"})
	public void submitOrder(HashMap<String,String> input) throws IOException {
		
		
		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));
		
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("Bangladesh");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("Thankyou for the order."));
		
	}
	
	@Test(dependsOnMethods= {"submitOrder"})
	public void OrderHistoryTest() {
		//"ZARA COAT 3"getData
		ProductCatalogue productCatalogue = landingPage.loginApplication("razin.qa@example.com", "Justdoit@123");
		OrderPage ordersPage = productCatalogue.gloToOrdersPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
		
	}

	
	
	
	@DataProvider
	public Object[][] getData() throws IOException 
	{
		
		List<HashMap<String,String>> data =  getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//razinautomation//data//PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
}

/*
 * HashMap<String,String> map = new HashMap<String,String>(); map.put("email",
 * "razin.qa@example.com"); map.put("password", "Justdoit@123");
 * map.put("product", "ZARA COAT 3");
 * 
 * HashMap<String,String> map1 = new HashMap<String,String>(); map1.put("email",
 * "razin@example.com"); map1.put("password", "Justdoit@123");
 * map1.put("product", "ADIDAS ORIGINAL");
 */
