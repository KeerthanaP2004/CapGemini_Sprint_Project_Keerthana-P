package ecom.BITM.SwagLabs.ThankYouPage;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.time.Duration;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CheckoutPage_POM;

public class SwagLabsThankYouNavigation_TS2 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration", "system" })
	public void verifyBackHomeNavigationAfterSuccessfulOrder() throws InterruptedException, IOException {

		Reporter.log("Logging in with valid credentials", true);
		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		SwagLabs_CheckoutPage_POM checkoutPage = new SwagLabs_CheckoutPage_POM(driver);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Step 1: Add a product to the cart
		Reporter.log("Adding product to cart", true);

		productPage.addFirstProductToCart();

		Assert.assertEquals(productPage.getCartCount(), 1, "Cart badge should show one product");

		// Step 2: Open the cart
		Reporter.log("Opening shopping cart", true);

		cartPage.clickShoppingCart();

		Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one product");

		// Step 3: Proceed to checkout
		Reporter.log("Proceeding to checkout", true);

		cartPage.clickCheckout();

		wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));

		// Step 4: Enter valid checkout information
		Reporter.log("Entering checkout information", true);

		checkoutPage.enterFirstName(firstName);
		checkoutPage.enterLastName(lastName);
		checkoutPage.enterPostalCode(postalCode);

		checkoutPage.clickContinue();

		wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));

		// Step 5: Verify checkout overview
		Assert.assertTrue(driver.findElement(By.id("finish")).isDisplayed(), "Finish button should be displayed");

		Reporter.log("Checkout overview displayed", true);

		// Step 6: Complete the order
		Reporter.log("Completing the order", true);

		checkoutPage.clickFinish();

		wait.until(ExpectedConditions.urlContains("checkout-complete.html"));

		String confirmationMessage = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header"))).getText().trim();

		Assert.assertEquals(confirmationMessage, "Thank you for your order!",
				"Order confirmation message is incorrect");

		Reporter.log("Order confirmation verified: " + confirmationMessage, true);

		// Step 7: Click Back Home
		Reporter.log("Clicking Back Home", true);

		wait.until(ExpectedConditions.elementToBeClickable(By.id("back-to-products"))).click();

		// Step 8: Verify navigation to Products page
		wait.until(ExpectedConditions.urlContains("inventory.html"));

		String pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("title"))).getText()
				.trim();

		Assert.assertEquals(pageTitle, "Products", "Products page title is incorrect");

		Reporter.log("Successfully returned to Products page", true);

		Reporter.log("Thank You page navigation test passed", true);
	}
}
