package ecom.BITM.SwagLabs.Login;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;

public class SwagLabsValidLoginPage_TS1 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration", "smoke", "ep" })
	public void validLoginTest() throws InterruptedException {

		Reporter.log("Starting valid login test", true);

		loginWithValidCredentials();

		Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
				"Login failed - Product page is not displayed");

		Reporter.log("Valid Login Successful - Product page displayed", true);
	}
}