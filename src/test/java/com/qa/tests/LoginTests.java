package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.internal.Utils;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingPage;
import com.qa.utils.TestUtils;

import io.appium.java_client.AppiumBy;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import com.qa.*;


public class LoginTests extends BaseTest{

  LoginPage loginpage;
  ProductsPage productpage;
  SettingPage settingpage;
  TestUtils Utils = new TestUtils();
  
  
  JSONObject loginUsers;

  @BeforeClass
  public void beforeClass() throws IOException {
	  
	  
	  
	  InputStream datais =null;
	  try
	  {
		  String DatafileName = "data/loginUser.json";
		  datais = getClass().getClassLoader().getResourceAsStream(DatafileName);
		  JSONTokener tokener = new JSONTokener(datais);
		  loginUsers = new JSONObject(tokener);
		  
	  }
	  
	  catch (Exception e )
	  
	  {
		  e.printStackTrace();
		  throw e;
	  }
	  
	  finally 
	  {
		  if (datais!= null)
		  {
			  datais.close();
		  }
	  }
	 
	  
	  
	  
  }

  @AfterClass
  public void afterClass() {
	  
	  closeApp();
	  launchApp();
  }

  @BeforeMethod
  public void beforeMethod(Method m) {
	  
	  
	  Utils.log("Login Before Method");
	  loginpage = new LoginPage();
	  Utils.log(m.getName());
	  
	  
	  
  }

  @AfterMethod
  public void afterMethod() {
	  
	  Utils.log("Login After Method");
	  closeApp();
	  launchApp();
  }
  
 
  @Test
  public void InvalidUserName() throws InterruptedException 
  {
	  loginpage.EnterUserName(loginUsers.getJSONObject("InvalidUser").getString("username"));
	  loginpage.EnterPassword(loginUsers.getJSONObject("InvalidUser").getString("password"));
	  loginpage.PressLoginButton();
      String ActualMessage =  loginpage.GetErrorText();
	  
	  
     
	  String Exp_Message = getString().get("invalid_username_or_password");
	  
	
	  
	  //strings.get("invalid_username_or_password");
	  Utils.log("Actual Message " +  ActualMessage + "\n" + "Expected Message"  + Exp_Message );
	  Assert.assertEquals(ActualMessage , Exp_Message );
	
	  
	  
	  
	 
  // We can use listers insteaded of try and catch block
	  
//  try{
//	 
//	
//		 loginpage.EnterUserName("Nikhil");
//		  loginpage.EnterPassword("Test");
//		  loginpage.PressLoginButton();
//		  String ActualMessage =  loginpage.GetErrorText("text");
//		  String ExpectedMessage = "Username and password do not match any user in this service.";
//		  Assert.assertEquals(ActualMessage , ExpectedMessage );
//			
//	 }
// 
//  catch (Exception e)
// 
//     {
//	    StringWriter sw= new  StringWriter();
//	    PrintWriter pw  = new PrintWriter(sw);
//	    
//  		e.printStackTrace(pw);
//		System.out.println(sw.toString());
//  		Assert.fail(sw.toString());
//  		
//     }
	  
  
  }
  @Test
  public void ValidUser() throws InterruptedException {
		
	  loginpage.EnterUserName(loginUsers.getJSONObject("ValidUser").getString("username"));
	  loginpage.EnterPassword(loginUsers.getJSONObject("ValidUser").getString("password"));
	  productpage = loginpage.PressLoginButton();
	  
	  String Actualtitle =  productpage.getTitle();;
	 
	  String EXP_Product_title = getString().get("product_title");
	  Utils.log("Actual title " +  Actualtitle + "\n" + "Expected title"  + EXP_Product_title);
			  //strings.get("product_title");
	 
//	  settingpage =productpage.pressSettingBtn();
//	  loginpage = settingpage.pressLogoutBtn();
	  
	  Assert.assertEquals(Actualtitle , EXP_Product_title );
		
		
   }
  
}
