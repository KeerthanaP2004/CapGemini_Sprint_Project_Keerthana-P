package ecom.BITM.SwagLabs.CartPage;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Screenshot.Screenshot;

import java.io.IOException;
import java.time.Duration;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;

public class SwagLabsEmptyCart_TS4 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "negative", "integration", "EP" })
	public void verifyCheckoutWithEmptyCart() throws InterruptedException, IOException {

		Reporter.log("Logging in with valid credentials", true);
		loginWithValidCredentials();

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		Reporter.log("Opening the empty shopping cart", true);
		cartPage.clickShoppingCart();

		Screenshot.takeScreenshot(driver, "EmptyCart_AfterCheckoutClick");

		Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty");

		Assert.assertFalse(cartPage.isCartBadgeDisplayed(), "Cart badge should not be displayed");

		Reporter.log("Verifying checkout behavior for an empty cart", true);

		cartPage.clickCheckout();

		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.urlContains("checkout-step-one.html"));

		Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),
				"Checkout information page should be displayed");

		Reporter.log("Empty-cart checkout behavior verified", true);
	}
}
