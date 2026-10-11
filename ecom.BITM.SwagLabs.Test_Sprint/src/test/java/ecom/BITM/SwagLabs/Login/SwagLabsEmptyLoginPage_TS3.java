package ecom.BITM.SwagLabs.Login;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import Screenshot.Screenshot;
import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_LoginPage_POM;

public class SwagLabsEmptyLoginPage_TS3 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "negative", "ep" })
	public void emptyLoginTest() throws InterruptedException, IOException {

		SwagLabs_LoginPage_POM loginPage = new SwagLabs_LoginPage_POM(driver);

		Reporter.log("Entering empty username", true);

		loginPage.enterUsername(emptyUsername);

		Reporter.log("Entering empty password", true);

		loginPage.enterPassword(emptyPassword);

		Reporter.log("Clicking Login button", true);

		loginPage.clickLogin();
		Screenshot.takeScreenshot(driver, "EmptyLoginPage");

		Thread.sleep(2000);

		Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");

		String actualMessage = loginPage.getErrorMessage();

		Reporter.log("Error message displayed: " + actualMessage, true);

		Assert.assertTrue(actualMessage.contains("Username is required"), "Incorrect error message displayed");

		Reporter.log("Empty login validation successful", true);
	}
}