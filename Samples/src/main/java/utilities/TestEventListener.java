package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import TestBase.TestBaseUtility;

public class TestEventListener extends TestBaseUtility implements WebDriverListener {

	@Override
	public void beforeClick(WebElement element) {
		TestUtilities.highlightElement(element, driver);
	}

	@Override
	public void afterFindElement(WebElement element, By locator, WebElement result) {
		TestUtilities.highlightElement(element, driver);
	}

}
