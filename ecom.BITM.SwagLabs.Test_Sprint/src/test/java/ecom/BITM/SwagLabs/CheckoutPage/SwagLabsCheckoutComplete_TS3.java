package ecom.BITM.SwagLabs.CheckoutPage;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Screenshot.Screenshot;
import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CheckoutPage_POM;

public class SwagLabsCheckoutComplete_TS3 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration", "system", "smoke" })
	public void verifySuccessfulOrderCompletion() throws InterruptedException, IOException {

		try {
			Reporter.log("Logging in with valid credentials", true);
			loginWithValidCredentials();

			SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

			SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

			SwagLabs_CheckoutPage_POM checkoutPage = new SwagLabs_CheckoutPage_POM(driver);

			Reporter.log("Adding product to cart", true);
			productPage.addFirstProductToCart();

			Assert.assertEquals(productPage.getCartCount(), 1, "Cart should contain one product");

			cartPage.clickShoppingCart();

			Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one product before checkout");

			cartPage.clickCheckout();

			Reporter.log("Entering checkout information", true);
			checkoutPage.enterFirstName(firstName);
			checkoutPage.enterLastName(lastName);
			checkoutPage.enterPostalCode(postalCode);
			checkoutPage.clickContinue();

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

			wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));

			Assert.assertTrue(driver.findElement(By.id("finish")).isDisplayed(), "Finish button should be displayed");

			Reporter.log("Finishing the order", true);
			checkoutPage.clickFinish();

			String confirmation = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header"))).getText()
					.trim();

			Assert.assertEquals(confirmation, "Thank you for your order!", "Order confirmation message is incorrect");

			Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"),
					"Order completion page was not displayed");

			Reporter.log("Order completed and confirmation verified successfully", true);

		} catch (AssertionError | Exception e) {

			Reporter.log("TEST FAILED: " + e.getMessage(), true);

			try {
				Screenshot.takeScreenshot(driver, "FAILED_CheckoutComplete_TS3");
			} catch (Exception screenshotError) {
				Reporter.log("Unable to capture failure screenshot: " + screenshotError.getMessage(), true);
			}

			throw e;
		}
	}
}
