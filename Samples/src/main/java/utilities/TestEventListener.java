package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;

import TestBase.TestBaseUtility;

public class TestEventListener extends TestBaseUtility implements WebDriverListener {

	@Override
	public void beforeClick(WebElement element) {
		TestUtilities.highlightElementOn(element, driver);
		WebDriverListener.super.beforeClick(element);
	}

	@Override
	public void afterFindElement(WebElement element, By locator, WebElement result) {
		TestUtilities.highlightElementOn(element, driver);
		WebDriverListener.super.afterFindElement(element, locator, result);
	}

}
