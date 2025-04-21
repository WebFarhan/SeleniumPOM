package razinautomation.TestComponents;

import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import razinautomation.pageobjects.LandingPage;
import razinautomation.pageobjects.LandingPageDashboard;

public class EzraBaseTest {
	
	public WebDriver driver;
	public LandingPageDashboard landingPageDashboard;
	
	private static final String[] FIRST_NAMES = {
	        "Alice", "Bob", "Carol", "David", "Ella", "Frank", "Grace", "Henry", "Isla", "Jack"
	    };

	private static final String[] LAST_NAMES = {
	        "Anderson", "Brown", "Clark", "Davis", "Evans", "Foster", "Garcia", "Hill", "Irwin", "Johnson"
	    };
	
	private static final Random random = new Random();
	
	// Generate a random first name
    public static String getRandomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
    }

    // Generate a random last name
    public static String getRandomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)];
    }
	
    public static String generateEmail(String firstName, String lastName) {
        // Clean and format the inputs
        String cleanFirst = firstName.trim().toLowerCase().replaceAll("[^a-z]", "");
        String cleanLast = lastName.trim().toLowerCase().replaceAll("[^a-z]", "");

        // Generate a short unique identifier (e.g., 5 random characters)
        String uniqueId = UUID.randomUUID().toString().substring(0, 5);

        // Construct and return the email address
        return cleanFirst + "." + cleanLast + uniqueId + "@exampletest.com";
    }
    
    public static String generatePhoneNumber() {
        // Area code (cannot start with 0 or 1)
        int areaCode = 200 + random.nextInt(800);  // Range: 200-999

        // Central office code (first digit 2–9)
        int centralOfficeCode = 200 + random.nextInt(800);

        // Line number
        int lineNumber = 1000 + random.nextInt(9000);

        // Format: (XXX) XXX-XXXX or just digits
        return String.format("%03d-%03d-%04d", areaCode, centralOfficeCode, lineNumber);
    }

	
	public WebDriver initializeDriver() throws IOException 
	{
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//razinautomation//resources//GlobalData.properties");
		prop.load(fis);
		String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : prop.getProperty("browser");
		
		//String browserName = prop.getProperty("browser");
		
		if(browserName.contains("chrome")) {
			Map<String, Object> prefs = new HashMap<String, Object>();

			prefs.put("profile.default_content_setting_values.notifications", 2);

			// Create an instance of ChromeOptions
			ChromeOptions options = new ChromeOptions();
			if(browserName.contains("headless")) {
				options.addArguments("headless");
			}
			// set ExperimentalOption - prefs
			options.setExperimentalOption("prefs", prefs);

			WebDriverManager.chromedriver().setup();
		    driver = new ChromeDriver(options);
			driver.manage().window().setSize(new Dimension(1440,900));//full screen
		}
		else if(browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		
		//read json file to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
	
		//String to HashMap Jackson databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String,String>>>() {
		});
		
		return data;
	}

	/**
	 * Screenshot utility
	 * @return 
	 * @throws IOException 
	 */
	public String getScreenShot(String testCaseName, WebDriver driver) throws IOException {
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File file = new File(System.getProperty("user.dir")+"//reports//"+testCaseName+".png");
		FileUtils.copyFile(source, file);
		
		return System.getProperty("user.dir")+"//reports//"+testCaseName+".png";
	}
	
	
	
	
	
	
	@BeforeMethod(alwaysRun=true)
	public void launchApplication() throws IOException 
	{
		driver = initializeDriver();
		landingPageDashboard = new LandingPageDashboard(driver);
		landingPageDashboard.goToLandingPage();
		
	}

	@AfterMethod(alwaysRun=true)
	public void tearDown() 
	{
		driver.close();
	}
	
}
