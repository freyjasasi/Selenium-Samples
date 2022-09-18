package sampleTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import TestBase.TestBaseUtility;
import pageObjects.AmazonPageObjects;
import utilities.TestUtilities;

public class AmazonTests extends TestBaseUtility {
	private static final Logger log = LogManager.getLogger(Log.class);

	AmazonTests() {
		super(); // to load properties file by calling super class(TestBase class) constructor
	}

	@Test(priority = 1, groups = { "search", "amazon", "data driven" })
	private void extractPhoneDetailsTest(String search_term) throws IOException {
		log.info("Extraction of details started.. for " + search_term);

		driver.get(prop.getProperty("amazon_url"));
		// assert title
		String expected = "Online Shopping site in India: Shop Online for Mobiles, Books, Watches, Shoes and More - Amazon.in";
		Assert.assertEquals(TestUtilities.getPageTitle(), expected, "Title is not valid");

		// creating object for page object class
		AmazonPageObjects amazonPageObjects = AmazonPageObjects.getInstance();

		WebElement amazon_search_box = amazonPageObjects.getAmazon_search_box();
		amazon_search_box.sendKeys(search_term);

		WebElement search_button = amazonPageObjects.getSearch_button();
		search_button.click();
		TestUtilities.screenGrab("search completed image for " + search_term); // screen grab

		List<WebElement> phone_elements = amazonPageObjects.getPhone_elements();
		// used streams api to do the data transformation
		List<String> phone_names = phone_elements.parallelStream().map(ele -> ele.getText()).filter(e -> !e.isBlank())
				.collect(Collectors.toList());

		List<WebElement> price_elements = amazonPageObjects.getPrice_elements();
		List<String> price_text = price_elements.parallelStream().map(ele -> ele.getText()).filter(e -> !e.isBlank())
				.collect(Collectors.toList());

		LinkedList<String> phone_names_list = new LinkedList<>(phone_names);
		LinkedList<String> price_list = new LinkedList<>(price_text);

		LinkedList<LinkedList<String>> all_data = new LinkedList<>();
		all_data.add(phone_names_list);
		all_data.add(price_list);

		// header row's column names list
		LinkedList<String> header_rows = new LinkedList<>(List.of("product names", "price"));
		TestUtilities.writeListToExcelUsingPOI(all_data, search_term + "_search_results", header_rows);

		log.info("Extraction of details finished for " + search_term);
	}

	@Test(groups = { "amazon", "image" }, priority = 2)
	private void imageValidationTest() {
		// creating object for page object class
		AmazonPageObjects amazonPageObjects = AmazonPageObjects.getInstance();
		driver.get(prop.getProperty("amazon_url"));

		WebElement flight_tickets_image_element = amazonPageObjects.getFlight_tickets_image_element();
		boolean isImageValid = TestUtilities.validateImage(flight_tickets_image_element);
		if (isImageValid) {
			log.info("Image is valid");
		} else {
			log.info("Image is not valid");
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
			log.info("Link is valid " + link_status_code);
		} else {
			log.info("Link is not valid " + link_status_code);
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

	@Test(priority = 0, groups = { "amazon", "search", "data driven" })
	private void multipleProductSearch() throws IOException {
		FileInputStream inputStream = new FileInputStream(new File("src//main//resources//phone_details.xlsx"));
		// used LinkedHashSet to remove duplicates and ordered
		LinkedHashSet<String> search_terms = TestUtilities.readFromExcel(inputStream);
		search_terms.forEach(e -> {
			try {
				extractPhoneDetailsTest(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
}
