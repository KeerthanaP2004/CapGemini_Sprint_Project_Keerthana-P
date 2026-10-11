package ecom.BITM.SwagLabs.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwagLabs_LoginPage_POM {

	WebDriver driver;

	public SwagLabs_LoginPage_POM(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "user-name")
	private WebElement username;

	@FindBy(id = "password")
	private WebElement password;

	@FindBy(id = "login-button")
	private WebElement loginButton;

	@FindBy(css = "[data-test='error']")
	private WebElement errorMessage;

	public void enterUsername(String value) {

		username.sendKeys(value);
	}

	public void enterPassword(String value) {

		password.sendKeys(value);
	}

	public void clickLogin() {

		loginButton.click();
	}

	public boolean isErrorMessageDisplayed() {

		try {

			return errorMessage.isDisplayed();

		} catch (Exception e) {

			return false;
		}
	}

	public String getErrorMessage() {

		return errorMessage.getText();
	}
}