package TestBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.TestEventListener;
import utilities.TestUtilities;

public class TestBaseUtility {
	public static Properties prop;
	public static WebDriver driver;
	public static WebDriverListener listener;

	public TestBaseUtility() {
		prop = new Properties();
		try (FileInputStream inputStream = new FileInputStream(new File("src//main//resources//config.properties"))) {
			prop.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initialization() { // then open browser based on input from properties file
		String browserName = prop.getProperty("browser");

		if (browserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		} else if (browserName.equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else {
			// no browser matched use default chrome
			System.err.println("please use valid browser name in properties file");
			System.exit(0);
		}

		// listener class implementation
		listener = new TestEventListener();
		driver = new EventFiringDecorator<WebDriver>(listener).decorate(driver);

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtilities.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtilities.IMPLICIT_WAIT));

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

	}

	@BeforeTest
	public void beforeEachTest() {
		System.out.println("Launching Browser..");
		initialization();
		System.out.println("Browser launched!");
	}

	@AfterTest
	public void afterEachTest() {
		System.out.println("Closing Browser..");
		driver.quit();
		System.out.println("Browser closed!");
	}

}
