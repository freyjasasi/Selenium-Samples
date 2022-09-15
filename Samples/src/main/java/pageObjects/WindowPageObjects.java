package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestBase.TestBaseUtility;
import lombok.Getter;

@Getter
public class WindowPageObjects extends TestBaseUtility {

	private static WindowPageObjects windowPageObjects;

	private WindowPageObjects() {
		PageFactory.initElements(driver, this);
	}

	public static WindowPageObjects getInstance() {
		if (windowPageObjects == null) {
			windowPageObjects = new WindowPageObjects();
		}
		return windowPageObjects;
	}

	@FindBy(id = "home")
	private WebElement home_page_button;

	@FindBy(id = "multi")
	private WebElement multi_window_page_button;

}
