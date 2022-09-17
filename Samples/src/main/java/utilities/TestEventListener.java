package utilities;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import TestBase.TestBaseUtility;

public class TestEventListener extends TestBaseUtility implements WebDriverListener {

	private static final Logger log = LogManager.getLogger(Log.class);

	@Override
	public void beforeClick(WebElement element) {
		TestUtilities.highlightElement(element, driver);
	}

	@Override
	public void afterFindElement(WebElement element, By locator, WebElement result) {
		TestUtilities.highlightElement(element, driver);
	}

}
