package sampleTests;

import org.apache.commons.logging.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import TestBase.TestBaseUtility;
import pageObjects.AlertPageObjects;
import utilities.TestUtilities;

public class AlertTests extends TestBaseUtility {
	private static final Logger log = LogManager.getLogger(Log.class);

	AlertTests() {
		super();
	}

	@Test
	private void alertTests() throws InterruptedException {
		driver.get(prop.getProperty("alert_url")); // load url

		// create object for page objects
		AlertPageObjects alertPageObjects = AlertPageObjects.getInstance();

		// simple alert
		WebElement simple_alert = alertPageObjects.getSimple_alert();
		simple_alert.click();
		log.info(TestUtilities.getAlertText());
		TestUtilities.alertAccept();

		// confirm alert
		WebElement confirm_alert = alertPageObjects.getConfirm_alert();
		confirm_alert.click();
		log.info(TestUtilities.getAlertText());
		TestUtilities.alertDismiss();

		// prompt alert
		WebElement prompt_alert = alertPageObjects.getPrompt_alert();
		prompt_alert.click();
		log.info(TestUtilities.getAlertText());
		TestUtilities.alertPrompt("Hello Alert!");
		TestUtilities.alertAccept();
		WebElement prompt_alert_text = alertPageObjects.getPrompt_alert_text_ok();
		TestUtilities.screenGrab("prompt_alert"); // screen capture
		log.info(prompt_alert_text.getText());

	}

}
