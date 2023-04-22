package com.qa.pages;
import com.qa.MenuPage;



import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class ProductsPage extends MenuPage{

	@AndroidFindBy (xpath = "//android.widget.ScrollView[@content-desc=\"test-PRODUCTS\"]/preceding-sibling::android.view.ViewGroup/	\n"
			+ "	android.view.ViewGroup/android.widget.TextView") 
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeStaticText[@name=\"PRODUCTS\"]") 
	private WebElement  title;
	
	
	
	@AndroidFindBy(xpath ="(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]" )
	@iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Item title\"])[1]") 
	private WebElement SLBtitle;
	
	@AndroidFindBy(xpath ="(//android.widget.TextView[@content-desc=\"test-Price\"])[1]" )
	@iOSXCUITFindBy (xpath = "(//XCUIElementTypeStaticText[@name=\"test-Price\"])[1]") 
	private WebElement SLBprice;
	
	
	
	public String getTitle()
	{
		return getAttribute(title  );
		
	}
	
	
	public String getSLBtitle()
	{
		return getAttribute(SLBtitle);
	}

	public String getSLBprice()
	{
		return getAttribute(SLBprice);
	}
	
	
	public Productdetails pressSLBtitle()
	{
		 click(SLBtitle) ;
		 return new Productdetails();
	}
	
	
	
 
}
