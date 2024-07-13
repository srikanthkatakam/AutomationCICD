package Ecommerce.SeleniumFrameworkDesign.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import Ecommerce.SeleniumFrameworkDesign.TestComponents.BaseTest;
import Ecommerce.SeleniumFrameworkDesign.TestComponents.Retry;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.CartPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.CheckoutPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.ConfirmationPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.LandingPage;
import Ecommerce.SeleniumFrameworkDesign.pageobjects.ProductCatalogue;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;

public class ErrorValidationTest extends BaseTest {

	@Test(groups = {"ErrorHandling"}, retryAnalyzer=Retry.class)
	public void loginErrorValidation() throws IOException {

		landingPage.loginApplication("rosebute@gmail.com", "Rose@23");
		Assert.assertEquals("Incorrect email password.", landingPage.getErrorMessage());
		
	}
	
	@Test
	public void productErrorValidation() throws IOException, InterruptedException, AWTException {

		String productName = "IPHONE 13 PRO";
		ProductCatalogue productCatalogue = landingPage.loginApplication("rosebute@gmail.com", "Rose@123");

		List<WebElement> products = productCatalogue.getProductList();

		productCatalogue.addProducttoCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();
		ZoomOut(4);
		Boolean match = cartPage.VerifyProductDisplay("IPHONE 13 PR");
		Assert.assertFalse(match);
	}

}