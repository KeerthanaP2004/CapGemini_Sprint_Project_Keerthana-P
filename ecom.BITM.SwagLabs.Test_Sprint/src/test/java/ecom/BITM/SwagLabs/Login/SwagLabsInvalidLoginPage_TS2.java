package ecom.BITM.SwagLabs.Login;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Screenshot.Screenshot;
import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_LoginPage_POM;

public class SwagLabsInvalidLoginPage_TS2 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "negative", "ep" })
	public void invalidLoginTest() throws InterruptedException, IOException {

		SwagLabs_LoginPage_POM loginPage = new SwagLabs_LoginPage_POM(driver);

		Reporter.log("Entering invalid username", true);

		loginPage.enterUsername(invalidUsername);

		Thread.sleep(1000);

		Reporter.log("Entering invalid password", true);

		loginPage.enterPassword(invalidPassword);

		Thread.sleep(1000);

		Reporter.log("Clicking Login button", true);

		loginPage.clickLogin();
		Screenshot.takeScreenshot(driver, "Invalid_Username");

		Thread.sleep(2000);

		Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
				"Error message is not displayed for invalid credentials");

		String actualMessage = loginPage.getErrorMessage();

		Reporter.log("Error message displayed: " + actualMessage, true);

		Assert.assertTrue(actualMessage.contains("Username and password do not match"),
				"Incorrect error message displayed");

		Reporter.log("Invalid login validation successful", true);
	}
}