package ecom.BITM.SwagLabs.POM;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwagLabs_CartPage_POM {

	private final WebDriver driver;
	private final WebDriverWait wait;

	private final By shoppingCartLink = By.className("shopping_cart_link");
	private final By cartItems = By.className("cart_item");
	private final By cartProductNames = By.className("inventory_item_name");
	private final By cartProductPrices = By.className("inventory_item_price");
	private final By cartTitle = By.className("title");
	private final By cartBadge = By.className("shopping_cart_badge");
	private final By checkoutButton = By.id("checkout");

	public SwagLabs_CartPage_POM(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void clickShoppingCart() {
		wait.until(ExpectedConditions.elementToBeClickable(shoppingCartLink)).click();

		wait.until(ExpectedConditions.urlContains("cart.html"));

		wait.until(ExpectedConditions.visibilityOfElementLocated(cartTitle));
	}

	public boolean isItemDisplayed() {
		wait.until(ExpectedConditions.urlContains("cart.html"));
		return !driver.findElements(cartItems).isEmpty();
	}

	public int getCartItemCount() {
		wait.until(ExpectedConditions.urlContains("cart.html"));
		return driver.findElements(cartItems).size();
	}

	public String getCartProductName() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductNames)).getText();
	}

	public String getCartProductPrice() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(cartProductPrices)).getText();
	}

	public List<WebElement> getCartProductNames() {
		return driver.findElements(cartProductNames);
	}

	public List<WebElement> getCartProductPrices() {
		return driver.findElements(cartProductPrices);
	}

	public void removeBackpack() {
		removeProduct("remove-sauce-labs-backpack");
	}

	public void removeProduct(String removeButtonId) {
		wait.until(ExpectedConditions.elementToBeClickable(By.id(removeButtonId))).click();
	}

	public boolean isCartBadgeDisplayed() {
		return !driver.findElements(cartBadge).isEmpty();
	}

	public int getCartCount() {
		List<WebElement> badges = driver.findElements(cartBadge);

		if (badges.isEmpty()) {
			return 0;
		}

		return Integer.parseInt(badges.get(0).getText().trim());
	}

	public void clickCheckout() {
		wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();

		wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));
	}
}
