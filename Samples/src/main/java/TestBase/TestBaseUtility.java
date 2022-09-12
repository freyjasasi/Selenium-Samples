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

import io.github.bonigarcia.wdm.WebDriverManager;
import utilities.TestUtilities;

public class TestBaseUtility {
	public static Properties prop;
	public static WebDriver driver;

	public TestBaseUtility() {
		prop = new Properties();

		try {
			prop.load(new FileInputStream(new File("src//main//resources//config.properties")));
		} catch (IOException e) {
			throw new RuntimeException(e);
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
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}

		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtilities.PAGE_LOAD_TIMEOUT));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtilities.IMPLICIT_WAIT));

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();

	}

}
