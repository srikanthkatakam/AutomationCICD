package Ecommerce.SeleniumFrameworkDesign.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Ecommerce.SeleniumFrameworkDesign.TestComponents.BaseTest;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.CartPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.LandingPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.OrderPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.ProductCatalogue;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

public class SubmitOrderTest extends BaseTest {
	String productName = "IPHONE 13 PRO";

	@Test(dataProvider = "getData")
	public void submitbutton(HashMap<String, String> input) throws IOException, InterruptedException, AWTException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(input.get("email"), input.get("password"));

		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProducttoCart(input.get("product"));
		CartPage cartPage = productCatalogue.goToCartPage();
		ZoomOut(5);
		Boolean match = cartPage.VerifyProductDisplay(input.get("product"));
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();

		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();

		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
	}

	@Test(dependsOnMethods = { "submitbutton" })
	public void OrderHistoryTest() {
		ProductCatalogue productCatalogue = landingPage.loginApplication("rosebute@gmail.com", "Rose@123");
		OrderPage ordersPage = productCatalogue.goToOrderPage();
		Assert.assertTrue(ordersPage.VerifyOrderDisplay(productName));
	}

	@DataProvider
	public Object[][] getData() throws IOException {

		/*HashMap<String, String> map = new HashMap<String, String>();
		map.put("email", "rosebute@gmail.com");
		map.put("password", "Rose@123");
		map.put("product", "IPHONE 13 PRO");

		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("email", "anshika@gmail.com");
		map1.put("password", "Iamking@000");
		map1.put("product", "ZARA COAT 3");*/

		 List<HashMap<String, String>> data =
		 getJsonDataToMap(System.getProperty("user.dir") +
		 "\\src\\test\\java\\Ecommerce\\SeleniumFrameworkDesign\\data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

}
