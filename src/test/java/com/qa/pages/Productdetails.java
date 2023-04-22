package com.qa.pages;
import org.openqa.selenium.WebElement;

import com.qa.MenuPage;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class Productdetails extends MenuPage {
	
	
	@AndroidFindBy(xpath ="//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]" )
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeStaticText[@name=\"Sauce Labs Backpack\"]") 
	private WebElement SLBtitle;
	
	@AndroidFindBy(xpath ="//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]" )
	@iOSXCUITFindBy(accessibility = "carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.") 
	private WebElement SLBtext;
	
	
	@AndroidFindBy(accessibility  ="test-BACK TO PRODUCTS" )
	@iOSXCUITFindBy (accessibility = "test-BACK TO PRODUCTS") 
	private WebElement BackToProduct;
	
	
	@iOSXCUITFindBy (id = "test-ADD TO CART") private WebElement addToCartBtn;
	
	
	public String getSLBtitle()
	{
		return getAttribute(SLBtitle);
	}

	
	
	public String getSLBtext()
	
	{
		return getAttribute(SLBtext);
	}
	
	
	
	public ProductsPage pressBackToProduct()
	{
		click(BackToProduct);
		return new ProductsPage() ;
	}
	
	
//	public String scrollToSLBPriceAndGetSLBPrice() {
//		return getText(scrollToElement(), "");
//	}
//
//	public void scrollPage() {
//		iOSScrollToElement();
//	}
//
//	public Boolean isAddToCartBtnDisplayed() {
//		return addToCartBtn.isDisplayed();
//	}
//
//	public ProductsPage pressBackToProductsBtn() {
//		click(BackToProduct);
//		return new ProductsPage();
//	}
	
	

}
