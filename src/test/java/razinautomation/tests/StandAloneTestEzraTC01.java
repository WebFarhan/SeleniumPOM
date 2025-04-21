package razinautomation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import razinautomation.pageobjects.LandingPage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class StandAloneTestEzraTC01 {
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String memberEmail = "razin.farhan2018+ezramc@gmail.com";
		String memberPassword = "112233Aa";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://myezra-staging.ezra.com/sign-in");
		
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
		
		//Thread.sleep(4000);
		WebElement cancelButtonElement = driver.findElement(By.cssSelector(".basic.normal.dark"));
		wait.until(ExpectedConditions.visibilityOf(cancelButtonElement));
		
		cancelButtonElement.click(); //click on cancel button
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Sign out ']")));
		driver.findElement(By.xpath("//button[text()=' Sign out ']")).click();
		driver.close();
		
	}

}
