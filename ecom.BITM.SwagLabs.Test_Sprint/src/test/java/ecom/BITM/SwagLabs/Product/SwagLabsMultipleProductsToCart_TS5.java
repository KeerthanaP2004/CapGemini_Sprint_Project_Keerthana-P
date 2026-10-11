package ecom.BITM.SwagLabs.Product;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;

public class SwagLabsMultipleProductsToCart_TS5 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration" })
	public void verifyMultipleProductsAddedToCart() throws InterruptedException {

		// =========================================
		// LOGIN
		// =========================================

		Reporter.log("Logging in with valid credentials", true);

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		// =========================================
		// GET PRODUCT NAMES
		// =========================================

		List<WebElement> productNames = productPage.getProductNames();

		Assert.assertTrue(productNames.size() >= 2, "At least two products should be available");

		String firstProductName = productNames.get(0).getText();

		String secondProductName = productNames.get(1).getText();

		Reporter.log("First selected product: " + firstProductName, true);

		Reporter.log("Second selected product: " + secondProductName, true);

		// =========================================
		// ADD FIRST PRODUCT
		// =========================================

		productPage.addProductToCart(0);

		Reporter.log("First product added to cart", true);

		// =========================================
		// ADD SECOND PRODUCT
		// =========================================

		productPage.addProductToCart(1);

		Reporter.log("Second product added to cart", true);

		// =========================================
		// VERIFY CART BADGE
		// =========================================

		Assert.assertEquals(productPage.getCartCount(), 2, "Cart badge should display 2");

		Reporter.log("Cart badge correctly displays 2", true);

		// =========================================
		// OPEN SHOPPING CART
		// =========================================

		cartPage.clickShoppingCart();

		Thread.sleep(1000);

		Reporter.log("Shopping Cart opened successfully", true);

		// =========================================
		// VERIFY CART ITEM COUNT
		// =========================================

		Assert.assertEquals(cartPage.getCartItemCount(), 2, "Two products should be present in the cart");

		Reporter.log("Two products are present in the cart", true);

		// =========================================
		// GET CART PRODUCTS
		// =========================================

		List<WebElement> cartProductNames = cartPage.getCartProductNames();

		// =========================================
		// VERIFY FIRST PRODUCT EXISTS
		// =========================================

		boolean firstProductFound = false;
		boolean secondProductFound = false;

		for (WebElement product : cartProductNames) {

			String cartProductName = product.getText();

			Reporter.log("Cart Product: " + cartProductName, true);

			if (cartProductName.equals(firstProductName)) {

				firstProductFound = true;
			}

			if (cartProductName.equals(secondProductName)) {

				secondProductFound = true;
			}
		}

		// =========================================
		// FINAL VALIDATION
		// =========================================

		Assert.assertTrue(firstProductFound, "First selected product is not present in Cart");

		Assert.assertTrue(secondProductFound, "Second selected product is not present in Cart");

		Reporter.log("Both selected products are correctly synchronized with Cart", true);
	}
}