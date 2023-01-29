package com.steps;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.pages.GreenkartPage;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class GreenKartSteps {
	WebDriver driver;
	ArrayList<Integer> priceList;
	int totalPrice;
	GreenkartPage pf;
	
	
	@Given("^user go to Green Kart home page$")
	public void user_go_to_Green_Kart_home_page() throws Throwable {
	    //Create an object for WebDriver interface to use methods from ChromDriver class
		driver = new ChromeDriver();
	    //Open browser and navigate to GreenKart home page
		driver.get("https://rahulshettyacademy.com/seleniumPractise/#/");
		//maximize window
		driver.manage().window().maximize();
		pf = new GreenkartPage(driver);
	    
	}

	@Given("^user verify page title as \"([^\"]*)\"$")
	public void user_verify_page_title_as(String title) throws Throwable {
		//Getting the actual tile from web page
		String actual = driver.getTitle();
		//capturing title from feature file
		String expected = title;
		//Printing the actual title
		System.out.println("Actual title: "+actual);
		//Verifying actual title with junit assert
		Assert.assertTrue("Title did not match",expected.equalsIgnoreCase(actual)); 
	    
	}

	@When("^user capture all price from the page in descending order$")
	public void user_capture_all_price_from_the_page_in_descending_order() throws Throwable {
	    
		//web element to capture all team names 
		List<WebElement> items= driver.findElements(By.xpath("//p[@class='product-price']"));
		
		priceList =new ArrayList<Integer>();		
		
		//Using for loop iterate through all the team names in list of web element				
		for (WebElement element : items) {
			String price = element.getText();
			int priceInt = Integer.parseInt(price);
			priceList.add(priceInt);
		}
		System.out.println("Before sort: "+priceList);
		
		Collections.sort(priceList, Collections.reverseOrder());
		System.out.println("After sort: "+priceList);
	    
	}

	@Then("^user add to cart max and min price item and capture total price$")
	public void user_add_to_cart_max_and_min_price_item_and_capture_total_price() throws Throwable {
	    int max = Collections.max(priceList);
	    int min = Collections.min(priceList);
	    
	    System.out.println(max+","+min);
	    
	    totalPrice = max;
	    
	    
	    WebElement maxItem= driver.findElement(By.xpath("//*[contains(text(),'"+ max +"')]/following-sibling::*/button"));
	    Thread.sleep(3000);
	    JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,2000)");
	    
	    //WebElement minItem= driver.findElement(By.xpath("(//*[contains(text(),'ADD TO CART')])[14]"));
	    
	    maxItem.click();
	    //minItem.click();
	    
	}

	@Then("^user click on cart button then proceed to check out$")
	public void user_click_on_cart_button_then_proceed_to_check_out() throws Throwable {
	    //driver.findElement(By.xpath("//*[@alt='Cart']")).click();
		pf.getCartIcon().click();
	    Thread.sleep(3000);
	    driver.findElement(By.xpath("//*[contains(text(),'PROCEED TO CHECKOUT')]")).click();
	    
	}

	@Then("^user verify total price from previous page$")
	public void user_verify_total_price_from_previous_page() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='totAmt']")));
		
		String totalPriceString = driver.findElement(By.xpath("//*[@class='totAmt']")).getText();
		Integer totalPriceInt = Integer.parseInt(totalPriceString);
		Integer totalPriceAddition = totalPrice;
		Assert.assertTrue("The total price is incorrect", totalPriceInt.equals(totalPriceAddition));
	    
	}

	@Then("^user click on place order$")
	public void user_click_on_place_order() throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Place Order')]")));
		driver.findElement(By.xpath("//*[contains(text(),'Place Order')]")).click();
		

	    
	}

	@Then("^user choose country as \"([^\"]*)\"$")
	public void user_choose_country_as(String arg1) throws Throwable {
		Select dropdown = new Select(driver.findElement(By.xpath("//*[@style=\"width: 200px;\"]")));
		dropdown.selectByValue(arg1);
		
		driver.findElement(By.xpath("//*[@type='checkbox']")).click();
		driver.findElement(By.xpath("//*[contains(text(), 'Proceed')]")).click();
	    
	}

	@Then("^user click on check box for agree to terms and conditions$")
	public void user_click_on_check_box_for_agree_to_terms_and_conditions() throws Throwable {
		driver.findElement(By.xpath("//*[@type='checkbox']")).click();
		
	    
	}

	@Then("^user click on the proceed button$")
	public void user_click_on_the_proceed_button() throws Throwable {
		driver.findElement(By.xpath("//*[contains(text(), 'Proceed')]")).click();
	    
	}

	@Then("^user verify \"([^\"]*)\" message$")
	public void user_verify_message(String arg1) throws Throwable {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//*[contains(text(),'" + arg1 + "')]")));
		WebElement actualElement = driver
				.findElement(By.xpath("//*[contains(text(),'Thank you, your order has been placed successfully')]"));
		String actual = actualElement.getText();
//	    System.out.println(actual);
		Assert.assertTrue("The text for product shipment is incorrect", actual.contains(arg1));
		driver.quit(); 
	    
	}
}
