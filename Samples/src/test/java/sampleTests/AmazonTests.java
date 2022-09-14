package sampleTests;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import TestBase.TestBaseUtility;
import pageObjects.AmazonPageObjects;
import utilities.TestUtilities;

public class AmazonTests extends TestBaseUtility {

	AmazonTests() {
		super(); // to load properties file by calling super class(TestBase class) constructor
	}

	@Test(priority = 1, enabled = true, groups = { "samsung", "phones", "amazon" })
	private void extractPhoneDetailsTest() {
		System.out.println("Extraction of phone details");

		// creating object for page object class
		AmazonPageObjects amazonPageObjects = AmazonPageObjects.getInstance();
		driver.get(prop.getProperty("amazon_url"));

		// assert title
		String expected = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
		Assert.assertEquals(TestUtilities.getPageTitle(), expected, "Title is not valid");

		WebElement amazon_search_box = amazonPageObjects.getAmazon_search_box();
		amazon_search_box.sendKeys("samsung phones");

		WebElement search_button = amazonPageObjects.getSearch_button();
		search_button.click();
		TestUtilities.screenGrab("search completed image"); // screen grab

		List<WebElement> phone_elements = amazonPageObjects.getPhone_elements();
		// used streams api to do the data transformation
		List<String> phone_names = phone_elements.parallelStream().map(ele -> ele.getText()).filter(e -> !e.isBlank())
				.collect(Collectors.toList());

		List<WebElement> price_elements = amazonPageObjects.getPrice_elements();
		List<String> price_text = price_elements.parallelStream().map(ele -> ele.getText()).filter(e -> !e.isBlank())
				.collect(Collectors.toList());

		LinkedList<String> phone_names_linked = new LinkedList<>(phone_names);

		System.out.println(phone_names_linked);
		price_text.forEach(System.out::println);

		// TimeUnit.SECONDS.sleep(1);

	}

	@Test(groups = { "amazon", "image" }, priority = 2)
	private void imageValidationTest() {
		// creating object for page object class
		AmazonPageObjects amazonPageObjects = AmazonPageObjects.getInstance();
		driver.get(prop.getProperty("amazon_url"));

		WebElement flight_tickets_image_element = amazonPageObjects.getFlight_tickets_image_element();
		boolean isImageValid = TestUtilities.validateImage(flight_tickets_image_element);
		if (isImageValid) {
			System.out.println("Image is valid");
		} else {
			System.out.println("Image is not valid");
		}
	}

	@Test(groups = { "amazon", "links" }, priority = 3)
	private void linkValidationTest() {
		// creating object for page object class
		AmazonPageObjects amazonPageObjects = AmazonPageObjects.getInstance();
		driver.get(prop.getProperty("amazon_url"));

		WebElement amazon_mainlogo_link = amazonPageObjects.getAmazon_mainlogo_link();
		int link_status_code = TestUtilities.validateLink(amazon_mainlogo_link.getAttribute("href"));
		if (link_status_code == 200) {
			System.out.println("Link is valid " + link_status_code);
		} else {
			System.out.println("Link is not valid " + link_status_code);
		}

	}

	@Test(priority = 4, enabled = true, groups = { "amazon", "dropdown", "phones" })
	private void dropDownsTest() throws InterruptedException {
		driver.get(prop.getProperty("amazon_url"));
		AmazonPageObjects amazonPageObjects = AmazonPageObjects.getInstance();

		WebElement search_suggesstions = amazonPageObjects.getCatagory_dropdown();
		TestUtilities.selectByIndex(search_suggesstions, 10);
		TimeUnit.SECONDS.sleep(2); // Books
		TestUtilities.selectByValue(search_suggesstions, "search-alias=shoes");
		TimeUnit.SECONDS.sleep(2); // shoes
		TestUtilities.selectByVisibleText(search_suggesstions, "Watches");
		TimeUnit.SECONDS.sleep(2); // watches

		TestUtilities.getAllOptions(search_suggesstions).forEach(System.out::println);

	}
}
