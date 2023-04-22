
package com.qa.pages;
import com.qa.MenuPage;
import com.qa.pages.LoginPage;


import org.openqa.selenium.WebElement;

import com.qa.BaseTest;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

public class SettingPage extends BaseTest {
	
	
	
@AndroidFindBy (accessibility ="test-LOGOUT") 
@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-LOGOUT\"]") 
private WebElement logoutBtn;
	

	
	public LoginPage pressLogoutBtn()
	{
		click(logoutBtn);
		return new LoginPage();
		
	}



	
	
	

}
