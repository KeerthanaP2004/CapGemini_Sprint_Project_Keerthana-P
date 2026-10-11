package ecom.BITM.SwagLabs.Product;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;

public class SwagLabsProductToCart_TS3 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration", "smoke" }, priority = 1)
	public void verifyProductDetailsBeforeAddingToCart() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		Assert.assertEquals(productPage.getProductsTitle(), "Products", "Products page title mismatch");

		Assert.assertTrue(productPage.getProductCount() > 0, "No products are displayed");

		String productName = productPage.getProductNames().get(0).getText().trim();

		String productPrice = productPage.getProductPrices().get(0).getText().trim();

		Assert.assertFalse(productName.isEmpty(), "Product name is empty");
		Assert.assertFalse(productPrice.isEmpty(), "Product price is empty");

		Reporter.log("Product name: " + productName, true);
		Reporter.log("Product price: " + productPrice, true);
	}

	@Test(groups = { "functional", "integration", "smoke" }, priority = 2)
	public void verifyProductAddedToCartBadge() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		productPage.addFirstProductToCart();

		Assert.assertTrue(productPage.isCartBadgeDisplayed(), "Cart badge is not displayed after adding the product");

		int cartCount = productPage.getCartCount();

		Reporter.log("Cart badge count: " + cartCount, true);

		Assert.assertEquals(cartCount, 1, "Cart badge count should be 1");
	}

	@Test(groups = { "functional", "integration" }, priority = 3)
	public void verifyProductDisplayedInCart() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		String expectedProductName = productPage.getProductNames().get(0).getText().trim();

		Reporter.log("Expected product name: " + expectedProductName, true);

		productPage.addFirstProductToCart();

		int badgeCount = productPage.getCartCount();

		Reporter.log("Cart badge count: " + badgeCount, true);

		Assert.assertEquals(badgeCount, 1, "Cart badge count should be 1");

		Reporter.log("Opening shopping cart", true);

		cartPage.clickShoppingCart();

		Reporter.log("Current URL: " + driver.getCurrentUrl(), true);

		int cartItemCount = cartPage.getCartItemCount();

		Reporter.log("Cart item count: " + cartItemCount, true);

		Assert.assertTrue(cartItemCount > 0, "Cart is empty even though the cart badge showed 1");

		String actualProductName = cartPage.getCartProductName().trim();

		Reporter.log("Product name in cart: " + actualProductName, true);

		Assert.assertEquals(actualProductName, expectedProductName,
				"Product name in cart does not match the selected product");
	}

	@Test(groups = { "functional", "integration" }, priority = 4)
	public void verifyProductPriceInCart() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		String expectedPrice = productPage.getProductPrices().get(0).getText().trim();

		Reporter.log("Expected product price: " + expectedPrice, true);

		productPage.addFirstProductToCart();

		Assert.assertEquals(productPage.getCartCount(), 1, "Cart badge count should be 1");

		cartPage.clickShoppingCart();

		Assert.assertTrue(cartPage.isItemDisplayed(), "Product is not displayed in cart");

		String actualPrice = cartPage.getCartProductPrice().trim();

		Reporter.log("Product price in cart: " + actualPrice, true);

		Assert.assertEquals(actualPrice, expectedPrice, "Product price in cart does not match the selected product");
	}
}
