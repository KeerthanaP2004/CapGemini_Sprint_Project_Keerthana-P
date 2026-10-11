package ecom.BITM.SwagLabs.Product;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;

public class SwagLabsProductPageValidation_TS1 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "smoke" })
	public void verifyProductPageValidation() throws InterruptedException {

		// =========================================
		// LOGIN
		// =========================================

		Reporter.log("Logging in with valid credentials", true);

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		// =========================================
		// VERIFY PRODUCT PAGE
		// =========================================

		Reporter.log("Verifying Product Page", true);

		Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "Product page is not displayed");

		Assert.assertEquals(productPage.getProductsTitle(), "Products", "Product page title is incorrect");

		Assert.assertTrue(productPage.getProductCount() > 0, "Products are not displayed");

		Reporter.log("Product Page displayed successfully", true);

		// =========================================
		// VERIFY PRODUCT INFORMATION
		// =========================================

		Reporter.log("Verifying Product Information", true);

		Assert.assertTrue(productPage.getProductNames().size() > 0, "Product names are not displayed");

		Assert.assertTrue(productPage.getProductImages().size() > 0, "Product images are not displayed");

		Assert.assertTrue(productPage.getProductPrices().size() > 0, "Product prices are not displayed");

		Assert.assertEquals(productPage.getProductNames().size(), productPage.getProductImages().size(),
				"Product names and images count do not match");

		Assert.assertEquals(productPage.getProductNames().size(), productPage.getProductPrices().size(),
				"Product names and prices count do not match");

		for (int i = 0; i < productPage.getProductNames().size(); i++) {

			String productName = productPage.getProductNames().get(i).getText();

			String productPrice = productPage.getProductPrices().get(i).getText();

			Assert.assertTrue(productPage.getProductImages().get(i).isDisplayed(),
					"Product image is not displayed for " + productName);

			Assert.assertFalse(productName.isEmpty(), "Product name is empty");

			Assert.assertFalse(productPrice.isEmpty(), "Product price is empty");

			Reporter.log("Product: " + productName + " | Price: " + productPrice, true);
		}

		Reporter.log("Product Information verified successfully", true);

		// =========================================
		// VERIFY PRODUCT DETAILS
		// =========================================

		String selectedProductName = productPage.getProductNames().get(0).getText();

		Reporter.log("Opening Product Details: " + selectedProductName, true);

		productPage.clickFirstProduct();

		Thread.sleep(1000);

		Assert.assertTrue(driver.getCurrentUrl().contains("inventory-item"), "Product detail page is not displayed");

		Reporter.log("Product Details opened successfully", true);

		// =========================================
		// RETURN TO PRODUCT PAGE
		// =========================================

		driver.navigate().back();

		Thread.sleep(1000);

		Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"),
				"Product page is not displayed after returning");

		Reporter.log("Product Page Validation completed successfully", true);
	}
}