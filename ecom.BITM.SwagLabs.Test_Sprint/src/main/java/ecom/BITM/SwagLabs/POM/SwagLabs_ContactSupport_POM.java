package ecom.BITM.SwagLabs.POM;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwagLabs_ContactSupport_POM {

	private final WebDriver driver;
	private final WebDriverWait wait;

	public SwagLabs_ContactSupport_POM(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	// X / Twitter link
	@FindBy(xpath = "//footer//a[contains(@href,'twitter.com') or contains(@href,'x.com')]")
	private WebElement xLink;

	// Facebook link
	@FindBy(xpath = "//footer//a[contains(@href,'facebook.com')]")
	private WebElement facebookLink;

	// LinkedIn link
	@FindBy(xpath = "//footer//a[contains(@href,'linkedin.com')]")
	private WebElement linkedInLink;

	// All matching social media links
	private final By socialLinksLocator = By
			.xpath("//footer//a[contains(@href,'twitter.com') " + "or contains(@href,'x.com') "
					+ "or contains(@href,'facebook.com') " + "or contains(@href,'linkedin.com')]");

	// Scroll to footer
	public void scrollToFooter() {
		WebElement footer = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("footer")));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'end'});", footer);

		wait.until(ExpectedConditions.visibilityOf(footer));
	}

	// X / Twitter methods
	public boolean isXLinkDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(xLink)).isDisplayed();
	}

	public String getXLink() {
		return wait.until(ExpectedConditions.visibilityOf(xLink)).getAttribute("href");
	}

	public void clickXLink() {
		WebElement link = wait.until(ExpectedConditions.elementToBeClickable(xLink));

		link.click();
	}

	// Facebook methods
	public boolean isFacebookLinkDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(facebookLink)).isDisplayed();
	}

	public String getFacebookLink() {
		return wait.until(ExpectedConditions.visibilityOf(facebookLink)).getAttribute("href");
	}

	public void clickFacebookLink() {
		wait.until(ExpectedConditions.elementToBeClickable(facebookLink)).click();
	}

	// LinkedIn methods
	public boolean isLinkedInLinkDisplayed() {
		return wait.until(ExpectedConditions.visibilityOf(linkedInLink)).isDisplayed();
	}

	public String getLinkedInLink() {
		return wait.until(ExpectedConditions.visibilityOf(linkedInLink)).getAttribute("href");
	}

	public void clickLinkedInLink() {
		wait.until(ExpectedConditions.elementToBeClickable(linkedInLink)).click();
	}

	// Count matching social media links
	public int getSocialMediaLinkCount() {
		return driver.findElements(socialLinksLocator).size();
	}

	public boolean areThreeSocialLinksDisplayed() {
		return getSocialMediaLinkCount() == 3;
	}

	// Get all matching social media URLs
	public List<String> getAllSocialMediaLinks() {
		return driver.findElements(socialLinksLocator).stream().map(element -> element.getAttribute("href")).toList();
	}
}
