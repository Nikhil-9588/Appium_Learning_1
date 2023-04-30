package com.qa;


import io.appium.java_client.pagefactory.iOSXCUITFindBy;

import org.openqa.selenium.WebElement;

import com.qa.pages.SettingPage;

import io.appium.java_client.pagefactory.AndroidFindBy;


public class MenuPage extends BaseTest {
	
	@AndroidFindBy (xpath ="//android.view.ViewGroup[@content-desc=\"test-Menu\"]/android.view.ViewGroup/android.widget.ImageView") 
	@iOSXCUITFindBy (xpath = "//XCUIElementTypeOther[@name=\"test-Menu\"]") 
	private WebElement settingBtn;
	

	
	public SettingPage pressSettingBtn()
	{

		waitForVisibility(settingBtn);
		click(settingBtn);
		return new SettingPage();
		
	}
	
	
}
