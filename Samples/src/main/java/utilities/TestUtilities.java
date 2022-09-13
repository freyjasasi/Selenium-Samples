package utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Alert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import TestBase.TestBaseUtility;

public class TestUtilities extends TestBaseUtility {

	// we can also define this inside config properties file
	public static final long PAGE_LOAD_TIMEOUT = 30;
	public static final long IMPLICIT_WAIT = 20;

	// more utils will be added, select, alert, action, screenshot, excel read and
	// write, highlight element,action

	public static void screenGrab(String fileName) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File source = screenshot.getScreenshotAs(OutputType.FILE);

		String time = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date());
		File destination = new File("screenGrabs//" + fileName + "-" + time + ".png");

		try {
			FileHandler.copy(source, destination);
			System.out.println("screen grab done for :" + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// alert utility
	public static void alertAccept() {
		Alert alert = driver.switchTo().alert();
		alert.accept();
		System.out.println("Alert accepted");
	}

	public static void alertDismiss() {
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		System.out.println("Alert Dismissed");
	}

	public static void alertPrompt(String prompt) {
		Alert alert = driver.switchTo().alert();
		alert.sendKeys(prompt);
		System.out.println("keys send to alert :" + prompt);
	}

	public static String getAlertText() {
		Alert alert = driver.switchTo().alert();
		return alert.getText();
	}

}
