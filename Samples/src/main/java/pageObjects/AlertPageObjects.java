package pageObjects;

import TestBase.TestBaseUtility;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class AlertPageObjects extends TestBaseUtility {

    private static AlertPageObjects alertPageObjects;

    private AlertPageObjects() {
        PageFactory.initElements(driver, this);
    }

    public static AlertPageObjects getInstance() {
        if (alertPageObjects == null) {
            alertPageObjects = new AlertPageObjects();
        }
        return alertPageObjects;
    }

    @FindBy(id = "accept")
    private WebElement simple_alert;

    @FindBy(id = "confirm")
    private WebElement confirm_alert;

    @FindBy(id = "prompt")
    private WebElement prompt_alert;

    @FindBy(id = "myName")
    private WebElement prompt_alert_text_ok;

}
