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

public class SwagLabsCheckoutOverview_TS2 extends SwagLabs_BaseClass {

	private void openCheckoutOverview() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		SwagLabs_CheckoutPage_POM checkoutPage = new SwagLabs_CheckoutPage_POM(driver);

		productPage.addFirstProductToCart();
		cartPage.clickShoppingCart();
		cartPage.clickCheckout();

		checkoutPage.enterFirstName(firstName);
		checkoutPage.enterLastName(lastName);
		checkoutPage.enterPostalCode(postalCode);
		checkoutPage.clickContinue();

		new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.urlContains("checkout-step-two.html"));
	}

	@Test(groups = { "functional", "integration" })
	public void verifyCheckoutOverviewDetails() throws InterruptedException, IOException {

		Reporter.log("Opening checkout overview", true);
		openCheckoutOverview();

		Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"),
				"Checkout overview page was not displayed");

		Assert.assertEquals(driver.findElements(By.className("cart_item")).size(), 1,
				"Overview should contain one product");

		Assert.assertTrue(driver.findElement(By.id("finish")).isDisplayed(), "Finish button should be displayed");

		Reporter.log("Checkout overview verified successfully", true);
	}

	@Test(groups = { "functional", "integration" })
	public void verifyCancelCheckoutReturnsToProducts() throws InterruptedException, IOException {

		Reporter.log("Opening checkout overview before cancellation", true);
		openCheckoutOverview();

		Screenshot.takeScreenshot(driver, "Checkout_BeforeCancellation");

		driver.findElement(By.id("cancel")).click();

		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("inventory.html"));

		Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
				"Cancel should return to the products page");

		Reporter.log("Checkout cancellation verified", true);
	}
}
