package ecom.BITM.SwagLabs.CartPage;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Screenshot.Screenshot;
import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;

public class SwagLabsCartPersistence_TS5 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration" })
	public void verifyCartPersistenceDuringNavigation() throws InterruptedException, IOException {

		try {
			Reporter.log("Logging in with valid credentials", true);
			loginWithValidCredentials();

			SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

			SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

			String expectedProductName = productPage.getProductNames().get(0).getText().trim();

			Reporter.log("Selected product: " + expectedProductName, true);

			Reporter.log("Adding product to cart", true);
			productPage.addFirstProductToCart();

			Assert.assertEquals(productPage.getCartCount(), 1, "Cart badge should show one product");

			Reporter.log("Opening cart for the first time", true);
			cartPage.clickShoppingCart();

			Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one product");

			String firstCartProduct = cartPage.getCartProductName().trim();

			Assert.assertEquals(firstCartProduct, expectedProductName,
					"Cart product does not match the selected product");

			Reporter.log("Returning to Products page", true);
			driver.navigate().back();

			Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Products page was not displayed");

			Assert.assertEquals(productPage.getCartCount(), 1,
					"Cart badge should remain at one after returning to Products");

			Reporter.log("Opening cart for the second time", true);
			cartPage.clickShoppingCart();

			Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should still contain one product");

			Assert.assertEquals(cartPage.getCartProductName().trim(), expectedProductName,
					"Product was not preserved after navigation");

			Reporter.log("Cart persistence verified successfully", true);

		} catch (AssertionError | Exception e) {

			Reporter.log("TEST FAILED: " + e.getMessage(), true);

			try {
				Screenshot.takeScreenshot(driver, "FAILED_CartPersistence_TS5");
			} catch (Exception screenshotError) {
				Reporter.log("Unable to capture failure screenshot: " + screenshotError.getMessage(), true);
			}

			throw e;
		}
	}
}
