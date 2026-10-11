package ecom.BITM.SwagLabs.CartPage;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;

public class SwagLabsRemoveProductFromCart_TS2 extends SwagLabs_BaseClass {

	private void addBackpackAndOpenCart() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		productPage.addFirstProductToCart();

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		cartPage.clickShoppingCart();
	}

	@Test(groups = { "functional", "integration" })
	public void verifyProductDisplayedBeforeRemoval() throws InterruptedException {

		addBackpackAndOpenCart();

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		Assert.assertEquals(cartPage.getCartItemCount(), 1, "Cart should contain the product before removal");

		Assert.assertTrue(cartPage.isItemDisplayed(), "Product should be displayed before removal");

		Reporter.log("Product verified before removal", true);
	}

	@Test(groups = { "functional", "integration" })
	public void verifyProductRemovedFromCart() throws InterruptedException {

		addBackpackAndOpenCart();

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		cartPage.removeBackpack();

		Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart should be empty after removing the product");

		Assert.assertFalse(cartPage.isItemDisplayed(), "Product should no longer be displayed");

		Reporter.log("Product removed successfully", true);
	}

	@Test(groups = { "functional", "integration" })
	public void verifyCartBadgeAfterRemovingLastProduct() throws InterruptedException {

		addBackpackAndOpenCart();

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		Assert.assertTrue(cartPage.isCartBadgeDisplayed(), "Cart badge should be displayed before removal");

		cartPage.removeBackpack();

		Assert.assertFalse(cartPage.isCartBadgeDisplayed(),
				"Cart badge should disappear after removing the last product");

		Assert.assertEquals(cartPage.getCartCount(), 0, "Cart badge count should be zero");

		Reporter.log("Cart badge verified after removal", true);
	}
}
