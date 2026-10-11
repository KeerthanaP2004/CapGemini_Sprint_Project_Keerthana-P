package ecom.BITM.SwagLabs.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwagLabs_HamburgerMenuPage_POM {

	WebDriver driver;

	public SwagLabs_HamburgerMenuPage_POM(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "react-burger-menu-btn")
	private WebElement menuButton;

	@FindBy(id = "inventory_sidebar_link")
	private WebElement allItems;

	@FindBy(id = "logout_sidebar_link")
	private WebElement logoutButton;

	public void clickMenu() {

		menuButton.click();
	}

	public void clickAllItems() {

		allItems.click();
	}

	public void clickLogout() {

		logoutButton.click();
	}
}