package razinautomation.tests;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import razinautomation.TestComponents.EzraBaseTest;
import razinautomation.pageobjects.LandingPageDashboard;

public class EzraBookingProcess extends EzraBaseTest{
	
	@Test
	public void EzraTestMemberSignUp() throws InterruptedException {
		
		driver.get("https://myezra-staging.ezra.com/join");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement cookieAccept = driver.findElement(By.cssSelector(".t-acceptAllButton"));
		wait.until(ExpectedConditions.visibilityOf(cookieAccept));
		wait.until(ExpectedConditions.elementToBeClickable(cookieAccept));
		cookieAccept.click();
		System.out.println(driver.getCurrentUrl());
		
		String firstName = getRandomFirstName();
		String lastName = getRandomLastName();
		String memberEmail = generateEmail(firstName,lastName); 
		
		System.out.println("User info first name: "+firstName);
		System.out.println("User info last name: "+lastName);
		System.out.println("User info email: "+memberEmail);
		
		driver.findElement(By.cssSelector("#firstName")).sendKeys(firstName);
		
		driver.findElement(By.cssSelector("#lastName")).sendKeys(lastName);
		
		
		driver.findElement(By.cssSelector("#email")).sendKeys(memberEmail);
		
		driver.findElement(By.cssSelector("#phoneNumber")).sendKeys(generatePhoneNumber());
		
		driver.findElement(By.cssSelector("#password")).sendKeys("112233Aa");
		
		driver.findElement(By.xpath("//span[contains(text(),'I agree to Ezra')]")).click();
		
		driver.findElement(By.xpath("//button[.=' Submit ']")).click();
		
		String planSelectionUrl = driver.getCurrentUrl();
		
		//Assert.assertEquals(planSelectionUrl, "https://myezra-staging.ezra.com/sign-up/select-plan");
		
		Thread.sleep(4000);
		WebElement cancelButtonElement = driver.findElement(By.cssSelector(".basic.normal.dark"));
		wait.until(ExpectedConditions.visibilityOf(cancelButtonElement));

		cancelButtonElement.click(); // click on cancel button
		
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[.=' Confirm ']")).click();
		
		Thread.sleep(4000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Sign out ']")));
		driver.findElement(By.xpath("//button[text()=' Sign out ']")).click();
		
		
	}
	
	
	@Test(dataProvider="getData")
	public void EzraTestLogin(HashMap<String,String> input) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement cookieAccept = driver.findElement(By.cssSelector(".t-acceptAllButton"));
		wait.until(ExpectedConditions.visibilityOf(cookieAccept));
		wait.until(ExpectedConditions.elementToBeClickable(cookieAccept));
		cookieAccept.click();

	
		LandingPageDashboard landingPageDashboard = new LandingPageDashboard(driver);
		landingPageDashboard.loginMemberPortal(input.get("email"), input.get("password"));
		
		landingPageDashboard.verifydashBoard();
		landingPageDashboard.memberSignOut();
	}
	
	
	
	@Test(dataProvider="getData")
	public void EzraTestTC01(HashMap<String,String> input) throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement cookieAccept = driver.findElement(By.cssSelector(".t-acceptAllButton"));
		wait.until(ExpectedConditions.visibilityOf(cookieAccept));
		wait.until(ExpectedConditions.elementToBeClickable(cookieAccept));
		cookieAccept.click();

	
		LandingPageDashboard landingPageDashboard = new LandingPageDashboard(driver);
		landingPageDashboard.loginMemberPortal(input.get("email"), input.get("password"));
		
		landingPageDashboard.startBookingProcess();
		

		WebElement dobElement = driver.findElement(By.id("dob"));
		wait.until(ExpectedConditions.visibilityOf(dobElement));
		wait.until(ExpectedConditions.elementToBeClickable(dobElement));
		dobElement.clear();
		dobElement.sendKeys("04-09-1990");//in this date 4 plans will display
		
	
		WebElement planTextElement = driver.findElement(By.xpath("(//p[@class='encounter-card__title'])[5]"));
		wait.until(ExpectedConditions.visibilityOf(planTextElement));
		
		String fullBodyPlusPlan =  planTextElement.getText();
		
		System.out.println(fullBodyPlusPlan);
		Assert.assertTrue(fullBodyPlusPlan.contains("Full Body Plus"));
		
		dobElement.clear();
		dobElement.sendKeys("04-10-1990");
		
		WebElement planFlagElement = driver.findElement(By.xpath("(//div[@class='encounter-card disabled'])[1]"));
		wait.until(ExpectedConditions.visibilityOf(planFlagElement));
		
		Boolean planFlag = planFlagElement.isEnabled();//this is true means disable  
		
		System.out.println("Full Body Plus plan status: "+planFlag);
		Assert.assertTrue(planFlag);
		
		WebElement cancelButtonElement = driver.findElement(By.cssSelector(".basic.normal.dark"));
		wait.until(ExpectedConditions.visibilityOf(cancelButtonElement));
		cancelButtonElement.click(); //click on cancel button
		
		landingPageDashboard.memberSignOut();
	}
	
	@Test
	public void EzraTestTC02() throws InterruptedException {
		String memberEmail = "razin.farhan2018+ezramc@gmail.com";
		String memberPassword = "112233Aa";
		
	
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement cookieAccept = driver.findElement(By.cssSelector(".t-acceptAllButton"));
		wait.until(ExpectedConditions.visibilityOf(cookieAccept));
		wait.until(ExpectedConditions.elementToBeClickable(cookieAccept));
		cookieAccept.click();
		System.out.println(driver.getCurrentUrl());
		
		driver.findElement(By.id("email")).sendKeys(memberEmail);
		driver.findElement(By.id("password")).sendKeys(memberPassword);
		driver.findElement(By.xpath("//button[contains(.,'Submit')]")).click();
		
		
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("div[class='section-header'] button[class='basic mini-new black-chocolate']")).click(); //start booking 
		
	
		//Dob selection
		WebElement dobElement = driver.findElement(By.id("dob"));
		wait.until(ExpectedConditions.visibilityOf(dobElement));
		wait.until(ExpectedConditions.elementToBeClickable(dobElement));
		dobElement.clear();
		dobElement.sendKeys("04-09-1990");//in this date 4 plans will display
		
		//sex selection
		driver.findElement(By.cssSelector("div[role='combobox']")).click();
		
		Thread.sleep(5000);
		driver.findElement(By.xpath("//ul[@class='multiselect__content']/li[.='Male']")).click();
		
		//plan selection
		Thread.sleep(5000);
		List<WebElement> plans = driver.findElements(By.cssSelector(".encounter-list-item"));
		
		System.out.println("Number of elements "+plans.size());
		
		for(WebElement e:plans) {
			System.out.println(e.getText());
		}
		
		WebElement plan = plans.stream().filter(product-> 
		product.findElement(By.cssSelector("span[class$='encounter-title h4']")).getText().equals("Full Body Flash")).findFirst().orElse(null);
		
		plan.click(); // plan selection
		
		Thread.sleep(5000);
		
		//button[@data-test='submit']
		WebElement continueButtonElement = driver.findElement(By.xpath("//button[@data-test='submit']"));
		wait.until(ExpectedConditions.elementToBeClickable(continueButtonElement));
		
		Boolean continueButtonFlag =  driver.findElement(By.xpath("//button[@data-test='submit']")).isEnabled();
		System.out.println("Continue button enable status: "+continueButtonFlag);
		continueButtonElement.click();
		
		
		WebElement newyorkAMRIC = driver.findElement(By.xpath("//div[@physicianordername='AMRIC']"));
		wait.until(ExpectedConditions.visibilityOf(newyorkAMRIC));
		wait.until(ExpectedConditions.elementToBeClickable(newyorkAMRIC));
		
		newyorkAMRIC.click();
		
		
		WebElement calendarElement = driver.findElement(By.cssSelector(".calendar"));
		wait.until(ExpectedConditions.visibilityOf(calendarElement));
		wait.until(ExpectedConditions.elementToBeClickable(calendarElement));
		
		Boolean calendarFlag = calendarElement.isDisplayed();
		
		System.out.println("Calendar display status: "+calendarFlag);
		Assert.assertTrue(calendarFlag);
		
		WebElement dayElement = driver.findElement(By.cssSelector("div[class='vuecal__cell vuecal__cell--day6']"));
		wait.until(ExpectedConditions.visibilityOf(dayElement));
		wait.until(ExpectedConditions.elementToBeClickable(dayElement));
		
		dayElement.isEnabled();
		System.out.println("Considering day availablety status: "+dayElement.isEnabled());
		dayElement.click();
		
		
		List<WebElement> availableAppointmentTimeSlots = driver.findElements(By.cssSelector(".appointments__individual-appointment"));
		System.out.println("Number of avialable time slots: "+ availableAppointmentTimeSlots.size());
		
		driver.findElement(By.xpath("//button[@data-test='cancel']")).click(); //back button
		
		//cancel
		WebElement cancelButtonElement = driver.findElement(By.cssSelector(".basic.normal.dark"));
		wait.until(ExpectedConditions.visibilityOf(cancelButtonElement));
		
		cancelButtonElement.click(); //click on cancel button
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Sign out ']")));
		driver.findElement(By.xpath("//button[text()=' Sign out ']")).click();
	
		
	}
	
	
	@Test
	public void EzraTestTC03() throws InterruptedException {
		System.out.println("TC name : Appointment Booking Prevention in same Time Slot");
		
	}
	
	@Test
	public void EzraTestTC04() throws InterruptedException {
		
		System.out.println("TC name : DOB Input validation and Age Restriction Handling");
		String memberEmail = "razin.farhan2018+ezramc@gmail.com";
		String memberPassword = "112233Aa";
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement cookieAccept = driver.findElement(By.cssSelector(".t-acceptAllButton"));
		wait.until(ExpectedConditions.visibilityOf(cookieAccept));
		wait.until(ExpectedConditions.elementToBeClickable(cookieAccept));
		cookieAccept.click();
		System.out.println(driver.getCurrentUrl());
		
		driver.findElement(By.id("email")).sendKeys(memberEmail);
		driver.findElement(By.id("password")).sendKeys(memberPassword);
		driver.findElement(By.xpath("//button[contains(.,'Submit')]")).click();
		
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("div[class='section-header'] button[class='basic mini-new black-chocolate']")).click();
		
		//Dob selection
		WebElement dobElement = driver.findElement(By.id("dob"));
		wait.until(ExpectedConditions.visibilityOf(dobElement));
		wait.until(ExpectedConditions.elementToBeClickable(dobElement));
		dobElement.clear();
		dobElement.sendKeys("11-20-1990");//in this date 4 plans will display
		
	
		WebElement planTextElement = driver.findElement(By.xpath("(//p[@class='encounter-card__title'])[5]"));
		wait.until(ExpectedConditions.visibilityOf(planTextElement));
		
		String fullBodyPlusPlan =  planTextElement.getText();
		
		System.out.println(fullBodyPlusPlan);
		Assert.assertTrue(fullBodyPlusPlan.contains("Full Body Plus"));
		
		dobElement.clear();
		dobElement.sendKeys("11-20-2025");
		
		WebElement errorElement = driver.findElement(By.cssSelector(".error-message.dob"));
		wait.until(ExpectedConditions.visibilityOf(errorElement));
		
		System.out.println("Error message: "+errorElement.getText());
		
		//cancel
		WebElement cancelButtonElement = driver.findElement(By.cssSelector(".basic.normal.dark"));
		wait.until(ExpectedConditions.visibilityOf(cancelButtonElement));
				
		cancelButtonElement.click(); //click on cancel button
				
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Sign out ']")));
		driver.findElement(By.xpath("//button[text()=' Sign out ']")).click();
		
	}
	
	@Test
	public void EzraTestTC05() throws InterruptedException {
		System.out.println("TC05 - Sex at birth Selection Validation and Enforcement");
		//Some member has Dob and Sex selected by default
		//this member does not has by default selection
		String memberEmail = "razin.farhan2018+ezratest7@gmail.com";
		String memberPassword = "112233Aa";
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement cookieAccept = driver.findElement(By.cssSelector(".t-acceptAllButton"));
		wait.until(ExpectedConditions.visibilityOf(cookieAccept));
		wait.until(ExpectedConditions.elementToBeClickable(cookieAccept));
		cookieAccept.click();
		System.out.println(driver.getCurrentUrl());
		
		driver.findElement(By.id("email")).sendKeys(memberEmail);
		driver.findElement(By.id("password")).sendKeys(memberPassword);
		driver.findElement(By.xpath("//button[contains(.,'Submit')]")).click();
		
		Thread.sleep(5000);
		driver.findElement(By.cssSelector("div[class='section-header'] button[class='basic mini-new black-chocolate']")).click();
		
		//Dob selection
		WebElement dobElement = driver.findElement(By.id("dob"));
		wait.until(ExpectedConditions.visibilityOf(dobElement));
		wait.until(ExpectedConditions.elementToBeClickable(dobElement));
		dobElement.clear();
		dobElement.sendKeys("11-20-1990");//in this date 4 plans will display
		
		// plan selection
		Thread.sleep(5000);
		List<WebElement> plans = driver.findElements(By.cssSelector(".encounter-list-item"));

		System.out.println("Number of elements " + plans.size());

		for (WebElement e : plans) {
			System.out.println(e.getText());
		}

		WebElement plan = plans.stream().filter(product -> product
				.findElement(By.cssSelector("span[class$='encounter-title h4']")).getText().equals("Full Body Flash"))
				.findFirst().orElse(null);

		plan.click(); // plan selection
		
		
		//when sex is not selected
		Boolean continueButtonFlagBefore =  driver.findElement(By.xpath("//button[normalize-space()='Continue']")).isEnabled();
		System.out.println("Continue button before sex selection: "+continueButtonFlagBefore);
		
		//Mandatory Selection Enforcement
		Assert.assertFalse(continueButtonFlagBefore);
		
		// sex selection
		driver.findElement(By.cssSelector("div[role='combobox']")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//ul[@class='multiselect__content']/li[.='Male']")).click();
		
		
		// when sex selected
		Boolean continueButtonFlagAfter = driver.findElement(By.xpath("//button[normalize-space()='Continue']")).isEnabled();
		System.out.println("Continue button after sex selection: " + continueButtonFlagAfter);
		
		//Valid Selection and Data Handling:
		Assert.assertTrue(continueButtonFlagAfter);
		
		//Data integrity verification is not implemented
		
		
		//cancel
		WebElement cancelButtonElement = driver.findElement(By.cssSelector(".basic.normal.dark"));
		wait.until(ExpectedConditions.visibilityOf(cancelButtonElement));

		cancelButtonElement.click(); // click on cancel button

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Sign out ']")));
		driver.findElement(By.xpath("//button[text()=' Sign out ']")).click();
		
		
	}
	
	@Test
	public void EzraTestTC06() throws InterruptedException {
		
	}
	
	
	@DataProvider
	public Object[][] getData() throws IOException 
	{
		
		List<HashMap<String,String>> data =  getJsonDataToMap(System.getProperty("user.dir")+"//src//test//java//razinautomation//data//MemberLoginCredentials.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
	
}
