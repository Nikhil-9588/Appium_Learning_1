package com.qa.tests;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.Productdetails;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingPage;

import io.appium.java_client.AppiumBy;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import com.qa.*;

import com.qa.pages.ProductsPage;



public class ProductTests extends BaseTest{

  LoginPage loginpage;
  SettingPage settingpage;
  ProductsPage  productpage;
  Productdetails productdetails;
  
 
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
  public void beforeMethod(Method m) throws InterruptedException {
	  
	  
	  System.out.println("productpage Before Method");
	  
	  loginpage = new LoginPage();
	  System.out.println(m.getName());
	  
	 // OpenAppWithStringUrl("swaglabs://swag-overview/0,1");
	 // productpage = new ProductsPage();
	  
	  
    productpage = loginpage.login(loginUsers.getJSONObject("ValidUser").getString("username") ,
			  loginUsers.getJSONObject("ValidUser").getString("password") );
  
  }

  @AfterMethod
  public  void afterMethod() {
	  
	  System.out.println("productpage  After Method");
	  settingpage =productpage.pressSettingBtn();
	  loginpage = settingpage.pressLogoutBtn();
	  
  }
  
 
  
	  
	  
	 
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
	  
  
  
  @Test
  public void validateProductOnProductsPage()
  {
	  SoftAssert sa = new SoftAssert();

	  
	  
	  String SBLtitle = productpage.getSLBtitle();
	  sa.assertEquals(SBLtitle, getString().get("product_details_page_slb_title"));
	  
	  String SLbprice = productpage.getSLBprice();
	  sa.assertEquals(SLbprice, getString().get("products_page_slb_price"));
      
      
      sa.assertAll();
	
	 
	  
	 
	  
	  
  }
  
  @Test
  public void validateProductOnProductsdDetailPage()
  {
	  SoftAssert sa = new SoftAssert();
	  

	  productdetails = productpage.pressSLBtitle();
	  
	  
	 
	  String SBLtitle = productdetails.getSLBtitle();
	  sa.assertEquals(SBLtitle, getString().get("product_details_page_slb_title"));
	  
		  
	      String SLBTxt = productdetails.getSLBtext();
		  sa.assertEquals(SLBTxt, getString().get("product_details_page_slb_txt"));
		  
		  productpage = productdetails.pressBackToProduct();
		  
		  sa.assertAll();
		   
	  }	
	  
	 
	  
	  
	  
	  
	  
	  
  }
  
  
	  
	  
  
  
  
  
  
  

