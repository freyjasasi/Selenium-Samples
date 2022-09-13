package sampleTests;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import TestBase.TestBaseUtility;
import pageObjects.AlertPageObjects;
import utilities.TestUtilities;

public class AlertTests extends TestBaseUtility {

	AlertTests() {
		super();
	}

	@Test
	private void alertTests() throws InterruptedException {
		initialization(); // load browser
		driver.get(prop.getProperty("alert_url")); // load url

		// create object for page objects
		AlertPageObjects alertPageObjects = AlertPageObjects.getInstance();

		// simple alert
		WebElement simple_alert = alertPageObjects.getSimple_alert();
		simple_alert.click();
		System.out.println(TestUtilities.getAlertText());
		TestUtilities.alertAccept();

		// confirm alert
		WebElement confirm_alert = alertPageObjects.getConfirm_alert();
		confirm_alert.click();
		System.out.println(TestUtilities.getAlertText());
		TestUtilities.alertDismiss();

		// prompt alert
		WebElement prompt_alert = alertPageObjects.getPrompt_alert();
		prompt_alert.click();
		System.out.println(TestUtilities.getAlertText());
		TestUtilities.alertPrompt("Hello Alert!");
		TestUtilities.alertAccept();
		WebElement prompt_alert_text = alertPageObjects.getPrompt_alert_text_ok();
		TestUtilities.screenGrab("prompt_alert"); // screen capture
		System.out.println(prompt_alert_text.getText());

		driver.quit();

	}

}
