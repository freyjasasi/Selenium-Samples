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

	@FindBy(linkText = "How Search works")
	private WebElement how_search_works_link_element;

	@FindBy(name = "q")
	private WebElement google_search_box_element;
}
