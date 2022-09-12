package amazon;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import TestBase.TestBaseUtility;
import lombok.Getter;

@Getter
public class AmazonPageObjects extends TestBaseUtility {

	private static AmazonPageObjects amazonPageObjects;

	private AmazonPageObjects() {
		/*
		 * in your test create object for this class and page objects will be
		 * Initialized
		 */
		PageFactory.initElements(driver, this);
	}

	public static AmazonPageObjects getInstance() {
		if (amazonPageObjects == null) {
			amazonPageObjects = new AmazonPageObjects();
		}
		return amazonPageObjects;
	}

	@FindBy(id = "twotabsearchtextbox")
	private WebElement amazon_search_box;

	@FindBy(id = "nav-search-submit-button")
	private WebElement search_button;

	@FindBy(xpath = "(//span[contains(@class,'a-size-medium a-color-base')])")
	private List<WebElement> phone_elements;

	@FindBy(xpath = "(//span[@class='a-price-whole'])")
	private List<WebElement> price_elements;

	// used to lombok to create getters, setters will be done by pageFactory

}
