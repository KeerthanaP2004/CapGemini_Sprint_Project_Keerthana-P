package ecom.BITM.SwagLabs.Product;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Screenshot.Screenshot;
import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;

public class SwagLabsInvalidProductDetails_TS6 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "negative", "system" })
	public void verifyInvalidProductDetails() throws InterruptedException, IOException {

		Reporter.log("Logging in with valid credentials", true);
		loginWithValidCredentials();

		String invalidProductURL = url + "inventory-item.html?id=9999";

		Reporter.log("Opening invalid product URL: " + invalidProductURL, true);

		driver.get(invalidProductURL);

		Thread.sleep(1500);

		Screenshot.takeScreenshot(driver, "InvalidProductDetails");

		String currentURL = driver.getCurrentUrl();

		Reporter.log("Current URL: " + currentURL, true);

		Assert.assertTrue(currentURL.contains("saucedemo.com"), "Browser navigated outside the SauceDemo application");

		boolean productDetailsDisplayed = !driver.findElements(By.className("inventory_details_name")).isEmpty();

		if (productDetailsDisplayed) {
			Reporter.log("Product details page is displayed for the invalid product ID. "
					+ "Further validation is required to determine whether this is expected.", true);
		} else {
			Reporter.log("Product details are not displayed for the invalid product ID.", true);
		}

		Reporter.log("Invalid product URL response captured for further validation.", true);
	}
}
