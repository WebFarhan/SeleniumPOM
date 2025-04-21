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

public class StandAloneTestEzra {
	


	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		String productName = "ZARA COAT 3";
		
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
		
		

		
		
	    
		
		driver.findElement(By.id("email")).sendKeys("razin.farhan2018+ezratest1@gmail.com");
		
		driver.findElement(By.id("password")).sendKeys("112233Aa");
		driver.findElement(By.xpath("//button[contains(.,'Submit')]")).click();
		
		
		
		
		
		
		Thread.sleep(400);
		
		
		

	    
		
	    Thread.sleep(5000);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=' Sign out ']")));
		driver.findElement(By.xpath("//button[text()=' Sign out ']")).click();
		//driver.close();
		
	}

}
