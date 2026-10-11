package ecom.BITM.SwagLabs.Product;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Screenshot.Screenshot;
import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;

public class SwagLabsUnauthorizedProductPage_TS4 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "negative", "system" })
	public void verifyUnauthorizedProductPageAccess() throws InterruptedException, IOException {

		Reporter.log("Attempting to access Product Page without login", true);

		driver.get(url + "inventory.html");

		Thread.sleep(2000);

		Reporter.log("Direct Product Page access attempted", true);

		// Product page should NOT be accessible

		Assert.assertFalse(driver.getCurrentUrl().contains("inventory.html"),
				"Unauthorized user is able to access Product Page");
		Screenshot.takeScreenshot(driver, "UnauthorizedProductPage");

		Reporter.log("Unauthorized user is blocked from Product Page", true);

		// User should remain within SauceDemo

		Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"),
				"User is not redirected to SauceDemo Login Page");

		Reporter.log("Unauthorized Product Page validation successful", true);
	}
}