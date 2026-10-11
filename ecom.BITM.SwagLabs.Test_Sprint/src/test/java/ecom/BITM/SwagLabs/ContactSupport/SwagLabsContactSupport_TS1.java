package ecom.BITM.SwagLabs.ContactSupport;

import java.util.Set;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import ecom.BITM.SwagLabs.BaseClass.SwagLabs_BaseClass;
import ecom.BITM.SwagLabs.POM.SwagLabs_ContactSupport_POM;

public class SwagLabsContactSupport_TS1 extends SwagLabs_BaseClass {

	@Test(groups = { "functional", "smoke" }, priority = 1)
	public void verifyFooterSocialLinksDisplayed() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ContactSupport_POM contactSupport = new SwagLabs_ContactSupport_POM(driver);

		contactSupport.scrollToFooter();

		Assert.assertTrue(contactSupport.isXLinkDisplayed(), "X/Twitter link is not displayed");

		Assert.assertTrue(contactSupport.isFacebookLinkDisplayed(), "Facebook link is not displayed");

		Assert.assertTrue(contactSupport.isLinkedInLinkDisplayed(), "LinkedIn link is not displayed");

		Reporter.log("All three social media links are displayed", true);
	}

	@Test(groups = { "functional", "integration" }, priority = 2)
	public void verifySocialMediaLinkURLs() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ContactSupport_POM contactSupport = new SwagLabs_ContactSupport_POM(driver);

		contactSupport.scrollToFooter();

		String xUrl = contactSupport.getXLink();
		String facebookUrl = contactSupport.getFacebookLink();
		String linkedInUrl = contactSupport.getLinkedInLink();

		Reporter.log("X/Twitter URL: " + xUrl, true);
		Reporter.log("Facebook URL: " + facebookUrl, true);
		Reporter.log("LinkedIn URL: " + linkedInUrl, true);

		Assert.assertTrue(xUrl != null && xUrl.matches("(?i)^https?://.*(twitter\\.com|x\\.com)(/.*)?$"),
				"X/Twitter URL is invalid");

		Assert.assertTrue(facebookUrl != null && facebookUrl.matches("(?i)^https?://.*facebook\\.com(/.*)?$"),
				"Facebook URL is invalid");

		Assert.assertTrue(linkedInUrl != null && linkedInUrl.matches("(?i)^https?://.*linkedin\\.com(/.*)?$"),
				"LinkedIn URL is invalid");

		Reporter.log("Social media URLs validated successfully", true);
	}

	@Test(groups = { "functional", "integration" }, priority = 3)
	public void verifySocialMediaLinkCount() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ContactSupport_POM contactSupport = new SwagLabs_ContactSupport_POM(driver);

		contactSupport.scrollToFooter();

		int linkCount = contactSupport.getSocialMediaLinkCount();

		Reporter.log("Social media link count: " + linkCount, true);

		Assert.assertEquals(linkCount, 3, "Expected exactly three matching social media links");

		Assert.assertTrue(contactSupport.areThreeSocialLinksDisplayed(),
				"Exactly three social links were not detected");

		Reporter.log("Social media link count validated", true);
	}

	@Test(groups = { "functional", "integration" }, priority = 4)
	public void verifyXLinkNavigation() throws InterruptedException {

		loginWithValidCredentials();

		SwagLabs_ContactSupport_POM contactSupport = new SwagLabs_ContactSupport_POM(driver);

		contactSupport.scrollToFooter();

		String expectedUrl = contactSupport.getXLink();
		String originalWindow = driver.getWindowHandle();

		Set<String> originalWindows = driver.getWindowHandles();

		Reporter.log("Expected X URL: " + expectedUrl, true);

		contactSupport.clickXLink();

		try {
			boolean destinationVerified = false;

			for (int i = 0; i < 20; i++) {

				Set<String> currentWindows = driver.getWindowHandles();

				// Handle a newly opened tab or window
				if (currentWindows.size() > originalWindows.size()) {

					for (String handle : currentWindows) {
						if (!originalWindows.contains(handle)) {
							driver.switchTo().window(handle);
							break;
						}
					}

					break;
				}

				// Handle navigation in the current tab
				String currentUrl = driver.getCurrentUrl();

				if (currentUrl.startsWith("https://x.com/")) {
					destinationVerified = true;
					break;
				}

				Thread.sleep(500);
			}

			// If a new tab opened, wait for its destination URL.
			if (driver.getWindowHandles().size() > originalWindows.size()) {

				for (int i = 0; i < 20; i++) {

					String currentUrl = driver.getCurrentUrl();

					if (currentUrl.startsWith("https://x.com/")) {
						destinationVerified = true;
						break;
					}

					Thread.sleep(500);
				}
			}

			Reporter.log("Actual URL after click: " + driver.getCurrentUrl(), true);

			Assert.assertTrue(destinationVerified, "X link did not navigate to https://x.com/");

			Reporter.log("X link click and navigation verified successfully", true);

		} finally {

			// Close only tabs opened during this test.
			for (String handle : driver.getWindowHandles()) {

				if (!originalWindows.contains(handle)) {
					driver.switchTo().window(handle);
					driver.close();
				}
			}

			// Restore the original SauceDemo tab.
			if (driver.getWindowHandles().contains(originalWindow)) {
				driver.switchTo().window(originalWindow);
			}
		}
	}
}
