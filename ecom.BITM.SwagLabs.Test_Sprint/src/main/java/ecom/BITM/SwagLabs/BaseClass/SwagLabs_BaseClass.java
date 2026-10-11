package ecom.BITM.SwagLabs.BaseClass;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import org.testng.Reporter;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import ecom.BITM.SwagLabs.POM.SwagLabs_HamburgerMenuPage_POM;
import ecom.BITM.SwagLabs.POM.SwagLabs_LoginPage_POM;

public class SwagLabs_BaseClass {

	// ==============================
	// WebDriver
	// ==============================

	protected WebDriver driver;

	// ==============================
	// Common Test Data
	// ==============================

	protected String browser;
	protected String url;

	protected String username;
	protected String password;

	// ==============================
	// Invalid Login Data
	// ==============================

	protected String invalidUsername;
	protected String invalidPassword;

	// ==============================
	// Empty Login Data
	// ==============================

	protected String emptyUsername;
	protected String emptyPassword;

	// ==============================
	// Purchase Data
	// ==============================

	protected String firstName;
	protected String lastName;
	protected String postalCode;

	// ==========================================================
	// Before Suite
	// ==========================================================

	@BeforeSuite(alwaysRun = true)
	public void beforeSuite() {
		Reporter.log("Open database connectivity", true);
	}

	// ==========================================================
	// Before Test
	// ==========================================================

	@BeforeTest(alwaysRun = true)
	public void beforeTest() {
		Reporter.log("Precondition", true);
	}

	// ==========================================================
	// Before Class
	// Browser parameter is taken from TestNG XML first.
	// Properties file is used only as a fallback.
	// ==========================================================

	@Parameters("browser")
	@BeforeClass(alwaysRun = true)
	public void beforeClass(@Optional("") String browserParameter) throws IOException {

		// ------------------------------------------
		// Load Common Data
		// ------------------------------------------

		Properties commonProperties = new Properties();

		try (FileInputStream commonFis = new FileInputStream(
				"./src/test/resources/ecom.BITM.SwagLabs.DDT/SwagLabsValidData.properties")) {

			commonProperties.load(commonFis);
		}

		// IMPORTANT:
		// Do not overwrite the browser requested by TestNG XML.
		if (browserParameter != null && !browserParameter.trim().isEmpty()) {

			browser = browserParameter.trim();

		} else {

			browser = commonProperties.getProperty("browser", "chrome").trim();
		}

		url = commonProperties.getProperty("url");
		username = commonProperties.getProperty("username");
		password = commonProperties.getProperty("password");

		Reporter.log("Requested browser: " + browser, true);

		// ------------------------------------------
		// Load Invalid Login Data
		// ------------------------------------------

		Properties invalidProperties = new Properties();

		try (FileInputStream invalidFis = new FileInputStream(
				"./src/test/resources/ecom.BITM.SwagLabs.DDT/SwagLabsInvalidData.properties")) {

			invalidProperties.load(invalidFis);
		}

		invalidUsername = invalidProperties.getProperty("invalidUsername");

		invalidPassword = invalidProperties.getProperty("invalidPassword");

		// ------------------------------------------
		// Load Empty Login Data
		// ------------------------------------------

		Properties emptyProperties = new Properties();

		try (FileInputStream emptyFis = new FileInputStream(
				"./src/test/resources/ecom.BITM.SwagLabs.DDT/SwagLabsEmptyData.properties")) {

			emptyProperties.load(emptyFis);
		}

		emptyUsername = emptyProperties.getProperty("emptyUsername");

		emptyPassword = emptyProperties.getProperty("emptyPassword");

		// ------------------------------------------
		// Load Purchase Data
		// ------------------------------------------

		Properties purchaseProperties = new Properties();

		try (FileInputStream purchaseFis = new FileInputStream(
				"./src/test/resources/ecom.BITM.SwagLabs.DDT/SwagLabsPurchaseData.properties")) {

			purchaseProperties.load(purchaseFis);
		}

		firstName = purchaseProperties.getProperty("firstName");
		lastName = purchaseProperties.getProperty("lastName");
		postalCode = purchaseProperties.getProperty("postalCode");

		// ==================================================
		// Browser Launch
		// ==================================================

		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions settings = new ChromeOptions();

			Map<String, Object> prefs = new HashMap<>();

			prefs.put("profile.password_manager_leak_detection", false);
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);

			settings.setExperimentalOption("prefs", prefs);

			driver = new ChromeDriver(settings);

		} else if (browser.equalsIgnoreCase("edge")) {

			driver = new EdgeDriver();

		} else if (browser.equalsIgnoreCase("firefox")) {

			driver = new FirefoxDriver();

		} else {

			throw new IllegalArgumentException(
					"Unsupported browser: " + browser + ". Supported browsers: chrome, edge, firefox");
		}

		// ==================================================
		// Browser Configuration
		// ==================================================

		driver.manage().window().maximize();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		Reporter.log("Browser launched successfully: " + driver.getClass().getSimpleName(), true);
	}

	// ==========================================================
	// Before Method
	// ==========================================================

	@BeforeMethod(alwaysRun = true)
	public void beforeMethod() throws InterruptedException {

		driver.get(url);

		Thread.sleep(2000);

		Reporter.log("Swag Labs application launched", true);
	}

	// ==========================================================
	// Valid Login Method
	// ==========================================================

	protected void loginWithValidCredentials() throws InterruptedException {

		SwagLabs_LoginPage_POM login = new SwagLabs_LoginPage_POM(driver);

		login.enterUsername(username);

		Thread.sleep(500);

		login.enterPassword(password);

		Thread.sleep(500);

		login.clickLogin();

		Thread.sleep(2000);

		Reporter.log("Login successful", true);
	}

	// ==========================================================
	// Invalid Login Method
	// ==========================================================

	protected void loginWithInvalidCredentials() throws InterruptedException {

		SwagLabs_LoginPage_POM login = new SwagLabs_LoginPage_POM(driver);

		login.enterUsername(invalidUsername);

		Thread.sleep(500);

		login.enterPassword(invalidPassword);

		Thread.sleep(500);

		login.clickLogin();

		Thread.sleep(1000);

		Reporter.log("Invalid login credentials submitted", true);
	}

	// ==========================================================
	// Empty Login Method
	// ==========================================================

	protected void loginWithEmptyCredentials() throws InterruptedException {

		SwagLabs_LoginPage_POM login = new SwagLabs_LoginPage_POM(driver);

		login.enterUsername(emptyUsername);

		Thread.sleep(500);

		login.enterPassword(emptyPassword);

		Thread.sleep(500);

		login.clickLogin();

		Thread.sleep(1000);

		Reporter.log("Empty login credentials submitted", true);
	}

	// ==========================================================
	// Logout
	// ==========================================================

	protected void logout() throws InterruptedException {

		Reporter.log("Logout started", true);

		SwagLabs_HamburgerMenuPage_POM menu = new SwagLabs_HamburgerMenuPage_POM(driver);

		menu.clickMenu();

		Thread.sleep(1000);

		menu.clickLogout();

		Thread.sleep(1000);

		Reporter.log("Logout successful", true);
	}

	// ==========================================================
	// After Method
	// ==========================================================

	@AfterMethod(alwaysRun = true)
	public void afterMethod() {

		Reporter.log("Test method execution completed", true);
	}

	// ==========================================================
	// After Class
	// ==========================================================

	@AfterClass(alwaysRun = true)
	public void afterClass() {

		Reporter.log("Close the browser", true);

		if (driver != null) {
			driver.quit();
			driver = null;
		}
	}

	// ==========================================================
	// After Test
	// ==========================================================

	@AfterTest(alwaysRun = true)
	public void afterTest() {

		Reporter.log("Postcondition", true);
	}

	// ==========================================================
	// After Suite
	// ==========================================================

	@AfterSuite(alwaysRun = true)
	public void afterSuite() {

		Reporter.log("Close database connectivity", true);
	}
}
