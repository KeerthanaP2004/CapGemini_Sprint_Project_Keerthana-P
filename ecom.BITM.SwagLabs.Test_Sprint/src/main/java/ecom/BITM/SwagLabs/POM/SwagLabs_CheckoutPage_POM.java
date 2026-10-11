package ecom.BITM.SwagLabs.POM;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwagLabs_CheckoutPage_POM {

	private final WebDriver driver;
	private final WebDriverWait wait;

	@FindBy(id = "first-name")
	private WebElement firstName;

	@FindBy(id = "last-name")
	private WebElement lastName;

	@FindBy(id = "postal-code")
	private WebElement postalCode;

	@FindBy(id = "continue")
	private WebElement continueButton;

	@FindBy(id = "finish")
	private WebElement finishButton;

	@FindBy(className = "complete-header")
	private WebElement orderConfirmation;

	@FindBy(className = "error-message-container")
	private WebElement errorMessageContainer;

	public SwagLabs_CheckoutPage_POM(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		PageFactory.initElements(driver, this);
	}

	public void enterFirstName(String value) {
		wait.until(ExpectedConditions.visibilityOf(firstName)).clear();
		firstName.sendKeys(value);
	}

	public void enterLastName(String value) {
		wait.until(ExpectedConditions.visibilityOf(lastName)).clear();
		lastName.sendKeys(value);
	}

	public void enterPostalCode(String value) {
		wait.until(ExpectedConditions.visibilityOf(postalCode)).clear();
		postalCode.sendKeys(value);
	}

	public void clickContinue() {
		wait.until(ExpectedConditions.elementToBeClickable(continueButton)).click();
	}

	public void clickFinish() {
		wait.until(ExpectedConditions.elementToBeClickable(finishButton)).click();

		wait.until(ExpectedConditions.visibilityOf(orderConfirmation));
	}

	public String getOrderConfirmation() {
		return wait.until(ExpectedConditions.visibilityOf(orderConfirmation)).getText();
	}

	public String getErrorMessage() {
		return wait.until(ExpectedConditions.visibilityOf(errorMessageContainer)).getText();
	}

	public boolean isErrorMessageDisplayed() {
		return !driver.findElements(org.openqa.selenium.By.className("error-message-container")).isEmpty();
	}
}
