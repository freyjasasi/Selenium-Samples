package sampleTests;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import TestBase.TestBaseUtility;
import pageObjects.WindowPageObjects;
import utilities.TestUtilities;

public class WindowHandlingTest extends TestBaseUtility {
	private static final Logger log = LogManager.getLogger(Log.class);

	WindowHandlingTest() {
		super(); // to load properties file by calling super class(TestBase class) constructor
	}

	@Test(priority = 1, groups = { "windows" })
	private void singleWindowTest() {
		driver.get(prop.getProperty("window_url"));
		WindowPageObjects windowPageObjects = WindowPageObjects.getInstance();

		String home_page = driver.getWindowHandle();
		windowPageObjects.getHome_page_button().click();
		TestUtilities.goToWindowbyIndex(1, new ArrayList<String>(driver.getWindowHandles()));
		log.info(driver.getTitle());
		TestUtilities.goToParentWindow(home_page);
		log.info(driver.getTitle());

		windowPageObjects.getMulti_window_page_button().click();
		TestUtilities.closeAllExceptParentWindow(home_page, new ArrayList<String>(driver.getWindowHandles()));
		TestUtilities.goToParentWindow(home_page);
		log.info(driver.getTitle());
	}

}
