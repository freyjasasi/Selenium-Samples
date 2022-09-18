package sampleTests;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import TestBase.TestBaseUtility;
import pageObjects.GoogleHomePageObjects;
import utilities.TestUtilities;

public class ActionsTests extends TestBaseUtility {
	private static final Logger log = LogManager.getLogger(Log.class);

	ActionsTests() {
		super();
	}

	@Test(groups = { "actions", "hoverOver" })
	private void hoverOverTest() {
		GoogleHomePageObjects googleHomePageObjects = GoogleHomePageObjects.getInstance();
		driver.get("https://www.google.com");
		log.info(driver.getTitle());

		WebElement tamil_lan_selector_element = googleHomePageObjects.getTamil_lan_selector_element();

		String before = tamil_lan_selector_element.getCssValue("text-decoration");
		log.info(before);

		TestUtilities.hoverOver(tamil_lan_selector_element);

		String after = tamil_lan_selector_element.getCssValue("text-decoration");
		log.info(after);

		WebElement how_search_works_link_element = googleHomePageObjects.getHow_search_works_link_element();
		WebElement google_search_box_element = googleHomePageObjects.getGoogle_search_box_element();
		TestUtilities.dragAndDrop(how_search_works_link_element, google_search_box_element);

		google_search_box_element.click();
		google_search_box_element.sendKeys(Keys.ENTER);
		log.info(driver.getTitle());
	}

}
