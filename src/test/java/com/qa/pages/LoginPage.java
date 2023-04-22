package com.qa.pages;

import org.openqa.selenium.WebElement;

import com.qa.BaseTest;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import io.appium.java_client.pagefactory.AndroidFindBy;


public class LoginPage extends BaseTest {

	
	
	

	@AndroidFindBy (accessibility = "test-Username") 
	@iOSXCUITFindBy (accessibility = "test-Username") 
	private WebElement UsernameField;
	
	@AndroidFindBy (accessibility = "test-Password") 
	@iOSXCUITFindBy (accessibility = "test-Password") 
	private WebElement PasswordField;
	
	@AndroidFindBy (accessibility = "test-LOGIN") 
	@iOSXCUITFindBy(accessibility = "test-LOGIN") 
	private WebElement loginBtn;
	
	
	@AndroidFindBy (xpath = "//android.view.ViewGroup[@content-desc=\"test-Error message\"]/android.widget.TextView") 
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Error message\"]/child::XCUIElementTypeStaticText") 
	private WebElement  ErrorText;
	
	
	
	public LoginPage EnterUserName(String username )
	{ 
		clear(UsernameField);
		sendkeys(UsernameField , username);
		return this;
		
	}
	
	public LoginPage EnterPassword(String password )
	{
		clear(PasswordField);
		sendkeys(PasswordField , password);
		return this;
		
	}
	
	public ProductsPage PressLoginButton()
	{
		click(loginBtn);
		return new ProductsPage() ;
		
	}
	
	public String GetErrorText()
	{
		return getAttribute(ErrorText);
		
	}
	
	public ProductsPage login(String username , String password)
	{
		EnterUserName(username );
		EnterPassword(password );
		return PressLoginButton();
		
	}
	
	

}
