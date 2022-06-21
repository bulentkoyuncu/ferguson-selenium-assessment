package com.build.qa.build.selenium.tests;

import org.junit.Assert;
import org.junit.Test;

import com.build.qa.build.selenium.framework.BaseFramework;
import com.build.qa.build.selenium.pageobjects.homepage.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class FergTest extends BaseFramework {

	/**
	 * Extremely basic test that outlines some basic
	 * functionality and page objects as well as assertJ
	 */
	@Test
	public void navigateToHomePage() {
		driver.get(getConfiguration("HOMEPAGE"));
		HomePage homePage = new HomePage(driver, wait);

		softly.assertThat(homePage.onHomePage())
				.as("The website should load up with the Build.com desktop theme.")
				.isTrue();
	}

	/**
	 * Search for the Moen m6702bn from the search bar
	 * @assert: That the product page we land on is what is expected by checking the product brand and product id
	 * @difficulty Easy
	 */
	@Test
	public void searchForProductLandsOnCorrectProduct() {
		// TODO: Implement this test

		driver.get(getConfiguration("HOMEPAGE"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.xpath("//input[@class='text-input search react-search-input-normal js-reload-value']")).sendKeys("Moen m6702bn" + Keys.ENTER);

		String productBrand = driver.findElement(By.xpath("//h2[@itemprop='name']")).getText();
		System.out.println("productBrand = " + productBrand);

		String productName = driver.findElement(By.xpath("//h1[@itemprop='name']")).getText();
		System.out.println("productName = " + productName);

		String productID = driver.findElement(By.xpath("//span[@itemprop='productID']")).getText();
		System.out.println("productID = " + productID);

		String expectedProductBrand = "Moen";
		String expectedProductID = "m6702bn";

		Assert.assertEquals("Product Name is NOT match",expectedProductBrand,productBrand);
		Assert.assertTrue("Product ID is not match",productID.contains(expectedProductID.toUpperCase()));

	}

	/**
	 * Go to the Bathroom Sinks category directly
	 * (https://www.ferguson.com/category/bathroom-plumbing/bathroom-faucets/bathroom-sink-faucets/_/N-zbq4i3)
	 * and add the second product on the search results (Category Drop) page to the cart.
	 * @assert: the product that is added to the cart is what is expected
	 * @difficulty Easy-Medium
	 */
	@Test
	public void addProductToCartFromCategoryDrop() {
		// TODO: Implement this test

		driver.get(getConfiguration("HOMEPAGE")+"/category/bathroom-plumbing/bathroom-faucets/bathroom-sink-faucets/_/N-zbq4i3");
		driver.navigate().to("https://www.ferguson.com/shoppingCart");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);


		String beforeProductAddToCart = driver.findElement(By.xpath("(//span[@class='count'])[1]")).getText();
		driver.navigate().back();

		WebElement secondProduct = driver.findElement(By.xpath("//div[@class='pc-grid4']/div/following-sibling::div"));
		secondProduct.click();

		WebElement secondItem = driver.findElement(By.xpath(("(//p[@data-placement='2'])[1]")));
		secondItem.click();

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

		WebDriverWait wait = new WebDriverWait(driver, 10);
		WebElement addToCart = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//button[@type='button'])[3]")));

		addToCart.click();


		driver.findElement(By.xpath("//li[@class='cart i-cart']")).click();

		String orderNumber = driver.findElement(By.xpath("//div[@class='page-orderno']")).getText();
		String productName = driver.findElement(By.xpath("//div[@class='cl-name']")).getText();
		String productQuantity = driver.findElement(By.xpath("//input[@name='updateQuantity']")).getAttribute("data-unit");
		String totalPrice = driver.findElement(By.xpath("//div[@class='total-price']/span[@class='f-bold']")).getText();

		String afterProductAddedToCart = driver.findElement(By.xpath("(//span[@class='shoppingCartAmount'])[1]")).getText();

		System.out.println("orderNumber = " + orderNumber);
		System.out.println("productName = " + productName);
		System.out.println("productQuantity = " + productQuantity);
		System.out.println("totalPrice = " + totalPrice);

		Assert.assertNotEquals("Cart quantity is SAME",beforeProductAddToCart, afterProductAddedToCart);


	}

	/**
	 * Add two different finishes of a product (such as Moen m6702bn) to cart,
	 * change the quantity of each finish on the cart page
	 * @assert that the product and cart total update as expected when the quantity is changed
	 * @difficulty Medium-Hard
	 */
	@Test
	public void addMultipleCartItemsAndChangeQuantity() {
		// TODO: Implement this test

		driver.get(getConfiguration("HOMEPAGE") + "/category/bathroom-plumbing/bathroom-faucets/bathroom-sink-faucets/_/N-zbq4i3?icid=mrch_ctg_cat_bathroom-plumbing_bathroom-sink-faucets");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement clickMoen = driver.findElement(By.xpath("//div[@class='ri-nav-ul-li-content']/p[.='Moen']"));
		clickMoen.click();

		WebElement moenBrandItem = driver.findElement(By.xpath("(//p[contains(@data-url,'m6702')])[3]"));

		moenBrandItem.click();

		driver.findElement(By.xpath("//button[@class='button mainly jq-quick-view-add-to-cart']")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement cartIcon = driver.findElement(By.xpath("//div[@class='cart']"));
		cartIcon.click();


		String firstProductQuantity = driver.findElement(By.xpath("//input[@name='updateQuantity']")).getAttribute("data-unit");
		System.out.println("firstProductQuantity = " + firstProductQuantity);

		driver.navigate().back();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement deltaFaucetBrandItem = driver.findElement(By.xpath("(//p[contains(@data-url,'m6702')])[3]"));
		deltaFaucetBrandItem.click();

		driver.findElement(By.xpath("//button[@class='button mainly jq-quick-view-add-to-cart']")).click();

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement cartIcon2 = driver.findElement(By.xpath("//div[@class='cart']"));
		cartIcon2.click();

		String secondProductQuantity = driver.findElement(By.xpath("//input[@name='updateQuantity']")).getAttribute("data-unit");
		System.out.println("secondProductQuantity = " + secondProductQuantity);

		Assert.assertTrue("Quantity is NOT changed", firstProductQuantity.equals(secondProductQuantity));


	}

	/**
	 * Go to a category drop page (such as Bathroom Faucets) and narrow by
	 * at least two filters (facets), e.g: Finish=Chromes and Brand=Brizo
	 * @assert that the correct filters are being narrowed, and the result count
	 * is correct, such that each facet selection is narrowing the product count.
	 * @difficulty Hard
	 */
	@Test
	public void facetNarrowBysResultInCorrectProductCounts() throws InterruptedException {
		// TODO: Implement this test

		driver.get(getConfiguration("HOMEPAGE"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement productsCategory = driver.findElement(By.xpath("//a[.='All Products']"));

		Actions actions = new Actions(driver);

		actions.moveToElement(productsCategory);


		WebElement bathroom = driver.findElement(By.xpath("//a[@class='mega-nav-dropdown-link w-inline-block hover']/div[.='Bathroom']"));

		actions.moveToElement(bathroom);

		actions.build().perform();

		WebElement bathroomFaucets = driver.findElement(By.xpath("//div[.='Bathroom Sink Faucets']"));
		bathroomFaucets.click();

		String beforeNarrowedQuantity = driver.findElement(By.xpath("//div[@class='word total-record']")).getText();
		System.out.println("beforeNarrowedQuantity = " + beforeNarrowedQuantity);


		WebElement finishChrome = driver.findElement(By.xpath("//div[@class='ri-nav-ul-li-content']//p[.='Chromes']"));

		finishChrome.click();

		String afterNarrowedQuantity = driver.findElement(By.xpath("//div[@class='word total-record']")).getText();
		System.out.println("afterNarrowedQuantity = " + afterNarrowedQuantity);

		Assert.assertEquals("Quantity didn't change",beforeNarrowedQuantity,afterNarrowedQuantity);
	}
}
