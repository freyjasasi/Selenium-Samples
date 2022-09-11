package amazon;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SamsungPhones {

	private static SamsungPhones samsungPhones;

	private SamsungPhones() {
		// for Singleton design pattern
	}

	public static SamsungPhones getInstance() {
		if (samsungPhones == null) {
			samsungPhones = new SamsungPhones();
		}

		return samsungPhones;

	}

	public void extractPhones() throws InterruptedException {
		System.out.println("Extraction of phone details");

		WebDriverManager.chromedriver().setup();
		ChromeDriver driver = new ChromeDriver();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();

		driver.get("https://www.amazon.in/");

		WebElement amazon_search_box = driver.findElement(By.id("twotabsearchtextbox"));
		amazon_search_box.sendKeys("samsung phones");

		WebElement search_button = driver.findElement(By.id("nav-search-submit-button"));
		search_button.click();

		List<WebElement> phone_elements = driver
				.findElements(By.xpath("(//span[contains(@class,'a-size-medium a-color-base')])"));
		List<String> phone_names = phone_elements.parallelStream().map(ele -> ele.getText()).filter(e -> !e.isBlank())
				.collect(Collectors.toList());

		List<WebElement> price_elements = driver.findElements(By.xpath("(//span[@class='a-price-whole'])"));
		List<String> price_text = price_elements.parallelStream().map(ele -> ele.getText()).filter(e -> !e.isBlank())
				.collect(Collectors.toList());

		phone_names.forEach(System.out::println);
		price_text.forEach(System.out::println);

		TimeUnit.SECONDS.sleep(1);

		driver.quit();

	}
}
