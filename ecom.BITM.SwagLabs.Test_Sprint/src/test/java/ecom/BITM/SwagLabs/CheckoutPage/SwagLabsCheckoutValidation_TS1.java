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

public class SwagLabsCheckoutValidation_TS1 extends SwagLabs_BaseClass {

	private SwagLabs_CheckoutPage_POM openCheckoutInformation() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		productPage.addFirstProductToCart();
		cartPage.clickShoppingCart();
		cartPage.clickCheckout();

		return new SwagLabs_CheckoutPage_POM(driver);
	}

	private String submitAndCaptureError(String screenshotName) throws IOException {

		new SwagLabs_CheckoutPage_POM(driver).clickContinue();

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		String error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='error']")))
				.getText();

		Screenshot.takeScreenshot(driver, screenshotName);

		Reporter.log("Checkout validation error: " + error, true);

		return error;
	}

	@Test(groups = { "functional", "negative", "integration", "EP" })
	public void verifyFirstNameRequired() throws InterruptedException, IOException {

		Reporter.log("Testing checkout without first name", true);

		SwagLabs_CheckoutPage_POM checkoutPage = openCheckoutInformation();

		checkoutPage.enterLastName(lastName);
		checkoutPage.enterPostalCode(postalCode);

		String error = submitAndCaptureError("Checkout_MissingFirstName");

		Assert.assertTrue(error.contains("First Name is required"),
				"Expected first-name validation error. Actual: " + error);

		Reporter.log("First-name validation verified", true);
	}

	@Test(groups = { "functional", "negative", "integration", "EP" })
	public void verifyLastNameRequired() throws InterruptedException, IOException {

		Reporter.log("Testing checkout without last name", true);

		SwagLabs_CheckoutPage_POM checkoutPage = openCheckoutInformation();

		checkoutPage.enterFirstName(firstName);
		checkoutPage.enterPostalCode(postalCode);

		String error = submitAndCaptureError("Checkout_MissingLastName");

		Assert.assertTrue(error.contains("Last Name is required"),
				"Expected last-name validation error. Actual: " + error);

		Reporter.log("Last-name validation verified", true);
	}

	@Test(groups = { "functional", "negative", "integration", "EP" })
	public void verifyPostalCodeRequired() throws InterruptedException, IOException {

		Reporter.log("Testing checkout without postal code", true);

		SwagLabs_CheckoutPage_POM checkoutPage = openCheckoutInformation();

		checkoutPage.enterFirstName(firstName);
		checkoutPage.enterLastName(lastName);

		String error = submitAndCaptureError("Checkout_MissingPostalCode");

		Assert.assertTrue(error.contains("Postal Code is required"),
				"Expected postal-code validation error. Actual: " + error);

		Reporter.log("Postal-code validation verified", true);
	}
}
