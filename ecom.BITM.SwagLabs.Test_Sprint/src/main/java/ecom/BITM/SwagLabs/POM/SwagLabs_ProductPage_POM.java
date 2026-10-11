package ecom.BITM.SwagLabs.POM;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwagLabs_ProductPage_POM {

	private final WebDriver driver;
	private final WebDriverWait wait;

	private final By inventoryList = By.className("inventory_list");
	private final By inventoryItems = By.className("inventory_item");
	private final By inventoryButton = By.cssSelector("button.btn_inventory");
	private final By cartBadge = By.className("shopping_cart_badge");

	public SwagLabs_ProductPage_POM(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	@FindBy(className = "title")
	private WebElement productsTitle;

	@FindBy(className = "product_sort_container")
	private WebElement sortDropdown;

	public String getProductsTitle() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title"))).getText();
	}

	public int getProductCount() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryList));
		return driver.findElements(inventoryItems).size();
	}

	public List<WebElement> getProductNames() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryList));
		return driver.findElements(By.className("inventory_item_name"));
	}

	public List<WebElement> getProductImages() {
		return driver.findElements(By.cssSelector(".inventory_item_img img"));
	}

	public List<WebElement> getProductPrices() {
		return driver.findElements(By.className("inventory_item_price"));
	}

	public void selectNameAToZ() {
		new Select(sortDropdown).selectByVisibleText("Name (A to Z)");
	}

	public void selectNameZToA() {
		new Select(sortDropdown).selectByVisibleText("Name (Z to A)");
	}

	public void selectPriceLowToHigh() {
		new Select(sortDropdown).selectByVisibleText("Price (low to high)");
	}

	public void selectPriceHighToLow() {
		new Select(sortDropdown).selectByVisibleText("Price (high to low)");
	}

	public void clickFirstProduct() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));

		getProductNames().get(0).click();
	}

	public boolean isProductDetailsDisplayed() {
		return !driver.findElements(By.className("inventory_details_name")).isEmpty();
	}

	public String getProductDetailsName() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_details_name")))
				.getText();
	}

	public String getProductDetailsPrice() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_details_price")))
				.getText();
	}

	public String getProductDetailsDescription() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_details_desc")))
				.getText();
	}

	public boolean isProductDetailsImageDisplayed() {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_details_img")))
				.isDisplayed();
	}

	public void addFirstProductToCart() {
		addProductToCart(0);
	}

	public void addProductToCart(int index) {

		wait.until(ExpectedConditions.visibilityOfElementLocated(inventoryList));

		List<WebElement> productCards = driver.findElements(inventoryItems);

		if (index < 0 || index >= productCards.size()) {
			throw new IllegalArgumentException(
					"Invalid product index: " + index + "; available products: " + productCards.size());
		}

		WebElement productCard = productCards.get(index);

		WebElement button = wait.until(driver -> {
			List<WebElement> buttons = productCard.findElements(inventoryButton);

			if (buttons.isEmpty()) {
				return null;
			}

			return buttons.get(0);
		});

		String buttonText = button.getText().trim();

		// The product may already be in the cart from an earlier test.
		if (buttonText.equalsIgnoreCase("Remove")) {

			wait.until(ExpectedConditions.elementToBeClickable(button)).click();

			wait.until(driver -> {
				List<WebElement> buttons = productCard.findElements(inventoryButton);

				return !buttons.isEmpty() && buttons.get(0).getText().trim().equalsIgnoreCase("Add to cart");
			});
		}

		// Locate the button again after the DOM updates.
		WebElement addButton = wait.until(driver -> {
			List<WebElement> buttons = productCard.findElements(inventoryButton);

			if (buttons.isEmpty()) {
				return null;
			}

			WebElement currentButton = buttons.get(0);

			if (currentButton.getText().trim().equalsIgnoreCase("Add to cart")) {
				return currentButton;
			}

			return null;
		});

		wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();

		// Wait until the cart badge reflects the added product.
		wait.until(driver -> {
			List<WebElement> badges = driver.findElements(cartBadge);

			if (badges.isEmpty()) {
				return false;
			}

			try {
				return Integer.parseInt(badges.get(0).getText().trim()) > 0;
			} catch (NumberFormatException e) {
				return false;
			}
		});
	}

	public int getCartCount() {
		List<WebElement> badges = driver.findElements(cartBadge);

		if (badges.isEmpty()) {
			return 0;
		}

		return Integer.parseInt(badges.get(0).getText().trim());
	}

	public boolean isCartBadgeDisplayed() {
		return !driver.findElements(cartBadge).isEmpty();
	}
}
