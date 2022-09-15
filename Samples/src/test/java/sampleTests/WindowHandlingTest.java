package sampleTests;

import java.util.ArrayList;

import org.testng.annotations.Test;

import TestBase.TestBaseUtility;
import pageObjects.WindowPageObjects;
import utilities.TestUtilities;

public class WindowHandlingTest extends TestBaseUtility {

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
		System.out.println(driver.getTitle());
		TestUtilities.goToParentWindow(home_page);
		System.out.println(driver.getTitle());

		windowPageObjects.getMulti_window_page_button().click();
		TestUtilities.closeAllExceptParentWindow(home_page, new ArrayList<String>(driver.getWindowHandles()));
		TestUtilities.goToParentWindow(home_page);
		System.out.println(driver.getTitle());
	}

}
