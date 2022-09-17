package pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestBase.TestBaseUtility;
import lombok.Getter;

@Getter
public class GoogleHomePageObjects extends TestBaseUtility {

	private static GoogleHomePageObjects googleHomePageObjects;

	private GoogleHomePageObjects() {
		PageFactory.initElements(driver, this);
	}

	public static GoogleHomePageObjects getInstance() {
		if (googleHomePageObjects == null) {
			googleHomePageObjects = new GoogleHomePageObjects();
		}

		return googleHomePageObjects;
	}

	@FindBy(linkText = "தமிழ்")
	private WebElement tamil_lan_selector_element;
}
