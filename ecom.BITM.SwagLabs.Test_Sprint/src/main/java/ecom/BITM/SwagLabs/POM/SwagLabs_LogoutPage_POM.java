package ecom.BITM.SwagLabs.POM;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SwagLabs_LogoutPage_POM {

	WebDriver driver;

	public SwagLabs_LogoutPage_POM(WebDriver driver) {

		this.driver = driver;

		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "react-burger-menu-btn")
	private WebElement userMenu;

	@FindBy(id = "logout_sidebar_link")
	private WebElement logout;

	public void clickUserMenu() {

		userMenu.click();
	}

	public void clickLogout() {

		logout.click();
	}
}