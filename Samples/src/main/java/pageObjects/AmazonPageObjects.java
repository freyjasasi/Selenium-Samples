package pageObjects;

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
		// only one object created all the time
		if (amazonPageObjects == null) {
			amazonPageObjects = new AmazonPageObjects();
		}
		return amazonPageObjects;
	}

	// used lombok to create getters, setters will be done by pageFactory

	@FindBy(id = "twotabsearchtextbox")
	private WebElement amazon_search_box;

	@FindBy(id = "nav-search-submit-button")
	private WebElement search_button;

	@FindBy(xpath = "(//span[contains(@class,'a-size-medium a-color-base')])")
	private List<WebElement> phone_elements;

	@FindBy(xpath = "(//span[@class='a-price-whole'])")
	private List<WebElement> price_elements;

	@FindBy(id = "nav-logo-sprites")
	private WebElement amazon_mainlogo_link;

	@FindBy(xpath = "(//img[@class='landscape-image'])[1]")
	private WebElement flight_tickets_image_element;

}
