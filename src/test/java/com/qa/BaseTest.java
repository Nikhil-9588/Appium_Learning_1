package com.qa;

import org.testng.annotations.Test;


import com.qa.utils.TestUtils;

import dev.failsafe.internal.util.Durations;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import io.appium.java_client.InteractsWithApps;


import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


// Appiurm_Learning_file_3

public class BaseTest {
  
	protected static ThreadLocal <AppiumDriver>  driver = new  ThreadLocal <AppiumDriver> (); 
	protected static  ThreadLocal <Properties> prop = new  ThreadLocal<Properties>();
	protected static ThreadLocal <HashMap<String,String>>  strings = new  ThreadLocal< HashMap<String,String>>();
	public static ThreadLocal <String>  platform =  new ThreadLocal<String>();
	protected static ThreadLocal <String> datetime= new ThreadLocal<String>();
	public static ThreadLocal <String>  DeviceName =  new ThreadLocal<String>();
	private static AppiumDriverLocalService server1;
	private static AppiumDriverLocalService server2;
	
	
	  
	TestUtils  utils = new TestUtils();
	
	
	
	
	String appPackage;
	String BundleID;
	
	InputStream stringise;
	
  public BaseTest()
  {
	  

	  PageFactory.initElements(new AppiumFieldDecorator(getDriver()), this);
  }
  
  
  
  
  @BeforeMethod
  public void beforeMethod()
  {
	  
	  
	  utils.log("Super Before Method");

	    if (getDriver() instanceof CanRecordScreen) {
	        ((CanRecordScreen) getDriver()).stopRecordingScreen();
	    }
	    
	    ((CanRecordScreen) getDriver()).startRecordingScreen();
	  
  }
  
  @AfterMethod
  public  void afterMethod(ITestResult result)
  {
	  // recording not work in ios if ffmpeg binary dependency not install 
      utils.log("Super After  Method");
	  
	  if (result.getStatus()==2 )
 {
	  String media =((CanRecordScreen) getDriver()).stopRecordingScreen();
	  HashMap<String , String>  param = new HashMap<String,String>() ;
	  param = (HashMap<String, String>) result.getTestContext().getCurrentXmlTest().getAllParameters();
		
	  String dir = "videos" + File.separator  + getPlatform() + "-" + param.get("platformVersion") + "-" + getDeviceName()
		+ File.separator + getDateTime() + File.separator + result.getTestClass().getRealClass().getSimpleName() ;
	  
	  File VideoDir = new File(dir);
	  
	  
	  
	  if (!VideoDir.exists())
	  {
		  VideoDir.mkdirs();
	  }
	  
	  
	 try 
	 {
		FileOutputStream file = new FileOutputStream(VideoDir + File.separator + result.getName() + ".mp4" );
		try {
			file.write(Base64.decodeBase64(media));
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 } 
	  catch (FileNotFoundException e) 
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  
	 
	}
	  
	  
  }
  
  
  
	
  @Parameters({"deviceName" , "platformName" ,  "platformVersion" ,"udid",  "systemPort" , "chromeDriverPort" , "wdaLocalPort" , "webkitDebugProxyPort"})
  @BeforeTest
  public void beforeTest(String DeviceName , String  platformName , String PlatformVersion  , String udid  ,@Optional("androidOnly")String systemPort , @Optional("androidOnly")String chromeDriverPort, @Optional("iOSOnly")String wdaLocalPort , @Optional("iOSOnly")String webkitDebugProxyPort ) throws Exception {



	  server1 = getAppiumServer(4723);
	  server2= getAppiumServer(4724);
	  server1.start();
	  server2.start();
	  
	  TestUtils  utils = new TestUtils();
	  setDateTime(utils.DateTime()) ;
	  
	  
	  InputStream inputStream = null;
	  InputStream stringis = null;
	  Properties  prop = new Properties();
	  AppiumDriver driver;
	  
	  setPlatform(platformName);
	 
	  setDeviceName(DeviceName);
	  utils.log("===========" +  platformName +  "=================");
	  
	  String strFile = "log" + File.separator + platformName + "_" + DeviceName;
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		
		
		//route logs to separate file for each thread
		ThreadContext.put("ROUTINGKEY", strFile);
		utils.log().info("Test Message For log");
		

		 
		
	 
	  
	  try {
		  
		  
		 
		  
		  // config.property file read 
		  prop = new Properties();
//		  String propFileName = "config.properties";
//		  inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//		  prop.load(inputStream);
//		  setProps(prop);

		  String propFilePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "config.properties";
		  inputStream = new FileInputStream(propFilePath);
		  prop.load(inputStream);
		  setProps(prop);
		  
//		  // XML file read
         String xmlFileName = "strings/strings.xml";
         stringis = getClass().getClassLoader().getResourceAsStream(xmlFileName);
         setString(utils.parseStringXML(stringis));
         System.out.println(getString().get("product_title"));
		  
		  
		    URL url;
	  
		    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		    desiredCapabilities.setCapability("platformName", platformName);
		    desiredCapabilities.setCapability("appium:platformVersion", PlatformVersion );
		    desiredCapabilities.setCapability("appium:deviceName",DeviceName);
		      
		    switch(platformName)
		    {
		    case "Android" :
		    	
		    	
		    	String  Android_appUrl = getClass().getResource(prop.getProperty("AndroidappLocation")).getFile();
		    	utils.log("Android App Url is " + Android_appUrl );
		    	
			    desiredCapabilities.setCapability("automationName", prop.getProperty("androidAutomaionName"));
			    desiredCapabilities.setCapability("appPackage", prop.getProperty("AndroidappPackage")); 
			    desiredCapabilities.setCapability("appActivity", prop.getProperty("AndroidappActivity"));
			   // desiredCapabilities.setCapability("app" , Android_appUrl);
			    desiredCapabilities.setCapability("udid", udid);
			    desiredCapabilities.setCapability("systemPort", systemPort); 
			    desiredCapabilities.setCapability("chromeDriverPort", chromeDriverPort);
			   
			    

			    url = null;
			    url = new URL(prop.getProperty("AndroidAppiumUrl"));	
				driver = new AndroidDriver(url,desiredCapabilities);
				break;
			    
			     
		    case "iOS" :
		    	
		    	
		    	
		    	String  iOS_appUrl = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "app" + File.separator + "iOSSauceLabsMobileSample.app";
				//	getClass().getResource(prop.getProperty("iOSappLocation")).getFile();
		    	utils.log("iOS App Url is " + iOS_appUrl );
		    	//System.out.println("iOS App Url is " + iOS_appUrl );
		    	
		    	desiredCapabilities.setCapability("automationName", prop.getProperty("iOSAutomaionName"));
		    	desiredCapabilities.setCapability("bundleId" , prop.getProperty("iOSBundleID"));
		    	desiredCapabilities.setCapability("udid", udid);
		    	desiredCapabilities.setCapability("wdaLocalPort", wdaLocalPort);
		    	desiredCapabilities.setCapability("webkitDebugProxyPort" , webkitDebugProxyPort);
		    	
		    	
		    	
		    	//desiredCapabilities.setCapability("app" , iOS_appUrl);
			    
			    url = null;
			    url = new URL(prop.getProperty("iOSAppiumUrl"));	
				driver = new IOSDriver(url,desiredCapabilities);
			    break;
		    	
			 default :
				 
				 throw new Exception("Invalid platform  " + platformName);
		    }
			setDriver(driver);
			
			
	  }
	  
	  catch(Exception e )
	  {
		  e.printStackTrace();
		  throw e;
		  
	  }
	  
	  finally
		{
			if(inputStream != null)
			{
				inputStream.close();
			}
			
//			if(stringis != null)
//			{
//				stringis.close();
//			}
			
		}
	  
	  
	  
  }
  
  
public void waitForVisibility(WebElement e)
  {
	  WebDriverWait wait = new WebDriverWait(getDriver() , Duration.ofSeconds(TestUtils.wait));
	  wait.until(ExpectedConditions.visibilityOf(e));
	  
  }
  

public void click(WebElement e)
{ 
	waitForVisibility( e);
	e.click();
	
}


public void sendkeys(WebElement e ,  String txt)

{ 
	waitForVisibility(e);
	e.sendKeys(txt);
}


public String getAttribute(WebElement e  )

{
	switch(getPlatform()) 
	{
	case "Android" :
		waitForVisibility(e);
		return e.getAttribute("text");
		
	case "iOS" :
		waitForVisibility(e);
		return e.getAttribute("label");
	}
	String a="dd";
	return a;
	
	
}


public void clear(WebElement e  )
{  
	waitForVisibility(e);
	e.clear();
}




public void closeApp()

{

	switch(getPlatform()) 
	{
	case "Android" :
		appPackage = getProps().getProperty("AndroidappPackage");
		((InteractsWithApps) getDriver()).terminateApp(appPackage);
		break;
		
	case "iOS" :
		BundleID =getProps().getProperty("iOSBundleID");
		((InteractsWithApps) getDriver()).terminateApp(BundleID);

	}
	
}





public void launchApp()
{

	switch(getPlatform()) 
	{
	case "Android" :
		appPackage = getProps().getProperty("AndroidappPackage");
		((InteractsWithApps) getDriver()).activateApp(appPackage);
		break;
		
	case "iOS" :
		BundleID =getProps().getProperty("iOSBundleID");
		((InteractsWithApps) getDriver()).activateApp(BundleID);

	}
	
}



public AppiumDriver getDriver()
{
	return driver.get();
}

public void setDriver(AppiumDriver driver2)
{
	driver.set(driver2);
}


public String getDateTime()
{
	return datetime.get();
}


public void setDateTime(String datetime2)
{
	datetime.set(datetime2);
}

public String getPlatform()
{
	return platform.get();
}


public void setPlatform(String platform2)
{
	platform.set(platform2);
}


public String getDeviceName()
{
	return DeviceName.get();
}


public void setDeviceName(String DeviceName2)
{
	DeviceName.set(DeviceName2);
}



public HashMap<String,String> getString()
{
	return strings.get();
}



public void setString(HashMap<String,String> string2)
{
	strings.set(string2);
}

public Properties getProps()
{
	return prop.get();
}


public void setProps(Properties prop2)
{
	prop.set(prop2);
}



@AfterTest
public void aftertest()
{

//	if(getDriver() != null){
//		getDriver().quit();
//	}
	
	
	if (server1 != null) {
		
        server1.stop();
        server1 = null;
    }
    
    if (server2 != null) {
    	
        server2.stop();
        server2 = null;
    }
    
    
}



@BeforeSuite
public void beforeSuite() throws AppiumServerHasNotBeenStartedLocallyException, Exception
{
//	ThreadContext.put("ROUTINGKEY", "ServerLogs");
//	
//	
//	if(!checkIfAppiumServerIsRunnning(4724)) {
//		server.start();
//		server.clearOutPutStreams(); // -> Comment this if you want to see server logs in the console
//		utils.log().info("Appium server started");
//	} else 
//	{
//		utils.log().info("Appium server already running");
//	}	
	
	
}

@AfterSuite
public void afterSuite()
{

	utils.log().info("Appium Server Stop");
	
}


public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
    boolean isAppiumServerRunning = false;
    ServerSocket socket;
    try {
        socket = new ServerSocket(port);
        socket.close();
    } catch (IOException e) {
    	System.out.println("1");
        isAppiumServerRunning = true;
    } finally {
        socket = null;
    }
    return isAppiumServerRunning;
}


public AppiumDriverLocalService getAppiumServerDefault()
{
	return AppiumDriverLocalService.buildDefaultService();
}



public  AppiumDriverLocalService getAppiumServer(int port)
{
	HashMap<String, String> env = new HashMap<String, String>();
	env.put("ANDROID_HOME" , "/Users/nikhilkumargupta/Library/Android/sdk");
	env.put("PATH", "/usr/local/bin:/System/Cryptexes/App/usr/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin:/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home/bin:/Users/nikhilkumargupta/Library/Android/sdk/platform-tools:/Users/nikhilkumargupta/Library/Android/sdk/tools:/Users/nikhilkumargupta/Library/Android/sdk/tools/bin:/Users/nikhilkumargupta/Library/Android/sdk/emulator" + System.getenv("PATH") );
	return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
			.usingDriverExecutable(new File ("/usr/local/bin/node"))
            .withEnvironment(env)
			.withAppiumJS(new File ("/usr/local/lib/node_modules/appium/build/lib/main.js"))
			.usingPort(port)
			.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
			.withLogFile(new File ("ServerLogs/server.log"))
			);
			//withIPAddress("0.0.0.0")
}



public synchronized void OpenAppWithStringUrl (String url)
{
	 switch(getPlatform())
	 { 
	 case "Android" :
		 HashMap<String,String> deepUrl = new  HashMap<String,String>();
		 deepUrl.put("url" , url);
		 deepUrl.put("package" , getProps().getProperty("AndroidappPackage"));
		 getDriver().executeScript("mobile: deepLink", deepUrl);
		 break;
		 
	 case "iOS" :
		 
		
		//XCUIElementTypeButton[@name="Go"]
		//XCUIElementTypeTextField[@name="URL"]
//		By urlBtn = AppiumBy.xpath("//XCUIElementTypeTextField[@name=\"URL\"]");
//		By gobtn = AppiumBy.xpath("//XCUIElementTypeButton[@name=\"Go\"]");
        
		 By urlBtn = AppiumBy.accessibilityId("CapsuleNavigationBar?isSelected=true");
	     //By urlFld = AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeTextField' && name CONTAINS 'URL'");
	     By openBtn = AppiumBy.iOSNsPredicateString("type == 'XCUIElementTypeButton' && name CONTAINS 'Open'");
	     ((InteractsWithApps) getDriver()).activateApp("com.apple.mobilesafari");
	     WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
	     wait.until(ExpectedConditions.visibilityOfElementLocated(urlBtn)).click();
	     wait.until(ExpectedConditions.visibilityOfElementLocated(urlBtn)).sendKeys("" + url + "\uE007");
	     wait.until(ExpectedConditions.visibilityOfElementLocated(openBtn)).click();
	     break;
	 
	 }
}



public WebElement scrollToElement() {
	  return getDriver().findElement(AppiumBy.androidUIAutomator(
			  "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
					  + "new UiSelector().description(\"test-Price\"));"));
}

public void iOSScrollToElement() {
//	  RemoteWebElement element = (RemoteWebElement)getDriver().findElement(By.name("test-ADD TO CART"));
//	  String elementID = element.getId();
	  HashMap<String, String> scrollObject = new HashMap<String, String>();
//	  scrollObject.put("element", elementID);
	  scrollObject.put("direction", "down");
//	  scrollObject.put("predicateString", "label == 'ADD TO CART'");
//	  scrollObject.put("name", "test-ADD TO CART");
//	  scrollObject.put("toVisible", "sdfnjksdnfkld");
	  getDriver().executeScript("mobile:scroll", scrollObject);
}



public String getText(WebElement e, String msg) {
	  String txt = null;
	  switch(getPlatform()) {
	  case "Android":
		  txt = getAttribute(e);
		  break;
	  case "iOS":
		  txt = getAttribute(e);
		  break;
	  }
	  
	  return txt;
}



}
