package ecom.BITM.SwagLabs.CartPage;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;

public class SwagLabsAddProductToCart_TS1 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration", "smoke" })
	public void verifyProductAddedToCart() throws InterruptedException {

		Reporter.log("Logging in with valid credentials", true);
		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		Reporter.log("Adding the first product to cart", true);
		productPage.addFirstProductToCart();

		Assert.assertEquals(productPage.getCartCount(), 1, "Cart badge should show one product");

		Reporter.log("Opening the shopping cart", true);
		cartPage.clickShoppingCart();

		Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain one product");

		Assert.assertTrue(cartPage.isItemDisplayed(), "Product should be displayed in cart");

		Reporter.log("Product added and verified successfully", true);
	}
}
