package sampleTests;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import TestBase.TestBaseUtility;
import pageObjects.AmazonPageObjects;
import utilities.TestUtilities;

public class SamsungPhonesTest extends TestBaseUtility {

	SamsungPhonesTest() {
		super(); // to load properties file by calling super class(TestBase class) constructor
	}

	@Test(priority = 1, enabled = true, groups = { "samsung", "phones", "amazon" })
	public void extractPhoneDetails() throws InterruptedException {
		System.out.println("Extraction of phone details");

		initialization(); // from test base class

		// creating object for page object class
		AmazonPageObjects amazonPageObjects = AmazonPageObjects.getInstance();
		driver.get(prop.getProperty("amazon_url"));

		WebElement amazon_search_box = amazonPageObjects.getAmazon_search_box();
		amazon_search_box.sendKeys("samsung phones");
		TestUtilities.screenGrab("search enter image");

		WebElement search_button = amazonPageObjects.getSearch_button();
		search_button.click();
		TestUtilities.screenGrab("search completed image");

		List<WebElement> phone_elements = amazonPageObjects.getPhone_elements();
		// used streams api to do the data transformation
		List<String> phone_names = phone_elements.parallelStream().map(ele -> ele.getText()).filter(e -> !e.isBlank())
				.collect(Collectors.toList());

		List<WebElement> price_elements = amazonPageObjects.getPrice_elements();
		List<String> price_text = price_elements.parallelStream().map(ele -> ele.getText()).filter(e -> !e.isBlank())
				.collect(Collectors.toList());

		phone_names.forEach(System.out::println);
		price_text.forEach(System.out::println);

		TimeUnit.SECONDS.sleep(1);

		driver.quit();

	}
}
