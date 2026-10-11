package ecom.BITM.SwagLabs.ThankYouPage;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CheckoutPage_POM;

public class SwagLabsThankYouPage_TS1 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration", "system", "smoke" })
	public void verifyThankYouPageAfterSuccessfulOrder() throws InterruptedException, IOException {

		Reporter.log("Logging in with valid credentials", true);
		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		SwagLabs_CheckoutPage_POM checkoutPage = new SwagLabs_CheckoutPage_POM(driver);

		Reporter.log("Adding product to cart", true);
		productPage.addFirstProductToCart();

		Assert.assertEquals(productPage.getCartCount(), 1, "Cart should contain one product");

		cartPage.clickShoppingCart();
		cartPage.clickCheckout();

		Reporter.log("Entering checkout information", true);
		checkoutPage.enterFirstName(firstName);
		checkoutPage.enterLastName(lastName);
		checkoutPage.enterPostalCode(postalCode);
		checkoutPage.clickContinue();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));

		Reporter.log("Finishing the order", true);
		checkoutPage.clickFinish();

		wait.until(ExpectedConditions.urlContains("checkout-complete.html"));

		String confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")))
				.getText();

		Assert.assertEquals(confirmation, "Thank you for your order!", "Thank-you message is incorrect");

		Assert.assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"), "Thank-you page URL is incorrect");

		Assert.assertTrue(driver.findElement(By.className("pony_express")).isDisplayed(),
				"Order completion image should be displayed");

		Reporter.log("Thank-you page verified successfully", true);
	}
}
