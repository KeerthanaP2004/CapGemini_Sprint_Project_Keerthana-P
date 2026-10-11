package ecom.BITM.SwagLabs.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ProductPage_POM;

public class SwagLabsProductSorting_TS2 extends SwagLabs_BaseClass {

	private SwagLabs_ProductPage_POM openProductPage() throws InterruptedException {
		Reporter.log("Logging in with valid credentials", true);
		loginWithValidCredentials();

		return new SwagLabs_ProductPage_POM(driver);
	}

	@Test(groups = { "functional", "integration", "smoke" })
	public void verifyProductSortingNameAToZ() throws InterruptedException {

		SwagLabs_ProductPage_POM productPage = openProductPage();

		productPage.selectNameAToZ();

		List<String> actualNames = new ArrayList<>();

		productPage.getProductNames().forEach(product -> actualNames.add(product.getText().trim()));

		List<String> expectedNames = new ArrayList<>(actualNames);
		Collections.sort(expectedNames, String.CASE_INSENSITIVE_ORDER);

		Assert.assertFalse(actualNames.isEmpty(), "No products are available for sorting");

		Assert.assertEquals(actualNames, expectedNames, "Products are not sorted by name A-Z");

		Reporter.log("Product sorting A-Z verified successfully", true);
	}

	@Test(groups = { "functional", "integration" })
	public void verifyProductSortingNameZToA() throws InterruptedException {

		SwagLabs_ProductPage_POM productPage = openProductPage();

		productPage.selectNameZToA();

		List<String> actualNames = new ArrayList<>();

		productPage.getProductNames().forEach(product -> actualNames.add(product.getText().trim()));

		List<String> expectedNames = new ArrayList<>(actualNames);

		Collections.sort(expectedNames, String.CASE_INSENSITIVE_ORDER);
		Collections.reverse(expectedNames);

		Assert.assertFalse(actualNames.isEmpty(), "No products are available for sorting");

		Assert.assertEquals(actualNames, expectedNames, "Products are not sorted by name Z-A");

		Reporter.log("Product sorting Z-A verified successfully", true);
	}

	@Test(groups = { "functional", "integration" })
	public void verifyProductSortingPriceLowToHigh() throws InterruptedException {

		SwagLabs_ProductPage_POM productPage = openProductPage();

		productPage.selectPriceLowToHigh();

		List<Double> actualPrices = getPrices(productPage);

		List<Double> expectedPrices = new ArrayList<>(actualPrices);
		Collections.sort(expectedPrices);

		Assert.assertFalse(actualPrices.isEmpty(), "No product prices are available for sorting");

		Assert.assertEquals(actualPrices, expectedPrices, "Products are not sorted by price low to high");

		Reporter.log("Price low-to-high sorting verified successfully", true);
	}

	@Test(groups = { "functional", "integration" })
	public void verifyProductSortingPriceHighToLow() throws InterruptedException {

		SwagLabs_ProductPage_POM productPage = openProductPage();

		productPage.selectPriceHighToLow();

		List<Double> actualPrices = getPrices(productPage);

		List<Double> expectedPrices = new ArrayList<>(actualPrices);
		Collections.sort(expectedPrices, Collections.reverseOrder());

		Assert.assertFalse(actualPrices.isEmpty(), "No product prices are available for sorting");

		Assert.assertEquals(actualPrices, expectedPrices, "Products are not sorted by price high to low");

		Reporter.log("Price high-to-low sorting verified successfully", true);
	}

	private List<Double> getPrices(SwagLabs_ProductPage_POM productPage) {

		List<Double> prices = new ArrayList<>();

		productPage.getProductPrices().forEach(priceElement -> {
			String priceText = priceElement.getText().replace("$", "").trim();

			prices.add(Double.parseDouble(priceText));
		});

		return prices;
	}
}
