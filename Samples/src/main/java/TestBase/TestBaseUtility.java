package TestBase;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utilities.TestEventListener;
import utilities.TestUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class TestBaseUtility {
    public static Properties prop;
    public static WebDriver driver;
    public static WebDriverListener listener;
    private static final Logger log = LogManager.getLogger(Log.class);

    public TestBaseUtility() {
        prop = new Properties();
        try (FileInputStream inputStream = new FileInputStream("src//main//resources//config.properties")) {
            prop.load(inputStream);
            log.info("Properties loaded!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initialization() { // then open browser based on input from properties file
        String browserName = prop.getProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            // using selenium manager
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            // no browser matched use default chrome
            log.error("please use valid browser name in properties file");
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
        log.info("Launching Browser..");
        initialization();
        log.info("Browser launched!");
    }

    @AfterTest
    public void afterEachTest() {
        log.info("Closing Browser..");
        driver.quit();
        log.info("Browser closed!");
    }

}
