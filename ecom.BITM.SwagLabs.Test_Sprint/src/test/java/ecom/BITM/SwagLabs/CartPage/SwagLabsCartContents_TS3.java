package ecom.BITM.SwagLabs.CartPage;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_CartPage_POM;

public class SwagLabsCartContents_TS3 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "integration" })
	public void verifyMultipleProductsInCart() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ProductPage_POM productPage = new SwagLabs_ProductPage_POM(driver);

		SwagLabs_CartPage_POM cartPage = new SwagLabs_CartPage_POM(driver);

		Reporter.log("Adding two products to cart", true);

		productPage.addProductToCart(0);
		productPage.addProductToCart(1);

		Assert.assertEquals(productPage.getCartCount(), 2, "Cart badge should show two products");

		cartPage.clickShoppingCart();

		Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart should contain two products");

		List<WebElement> cartNames = cartPage.getCartProductNames();

		List<String> actualNames = new ArrayList<>();

		for (WebElement item : cartNames) {
			actualNames.add(item.getText().trim());
		}

		Assert.assertEquals(actualNames.size(), 2, "Two product names should be displayed");

		Assert.assertTrue(cartPage.isCartBadgeDisplayed(), "Cart badge should be visible");

		Reporter.log("Both products are displayed in the cart: " + actualNames, true);
	}
}
