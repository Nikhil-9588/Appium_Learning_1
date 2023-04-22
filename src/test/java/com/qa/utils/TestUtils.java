package com.qa.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Date;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

//import javax.swing.text.Document;
//import javax.swing.text.Element;

import org.openqa.selenium.By;
import org.w3c.dom.Document ;
import org.w3c.dom.NodeList;

import com.qa.BaseTest;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

//import org.w3c.dom.Element;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class TestUtils {
	
	public static final long wait = 20;
	
	
	public HashMap<String , String> parseStringXML (InputStream file ) throws Exception
	{
		
		HashMap<String , String> StringMap =  new HashMap<String , String>();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(file);
        
        
        document.getDocumentElement().normalize();
        Element root =  (Element) document.getDocumentElement();
        
        NodeList nodeList = root.getElementsByTagName("string");
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                StringMap.put(element.getAttribute("name") , element.getTextContent());
        }
        }
        
        System.out.println("++++++++++++++++++++++++++++" +  StringMap);
        return StringMap;
		
		
	}
        
	
	public String DateTime()
	{
		LocalDateTime time  =LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-ss-mm");
		String formDate = time.format(myFormatObj);
		System.out.println(formDate);
		return formDate;
		
	}
	
	public void log(String txt)
	{
		BaseTest base = new BaseTest();
		String msg= Thread.currentThread().getId() + ":" + base.getPlatform() +
		":" +  base.getDeviceName() + ":" + Thread.currentThread().getStackTrace()[2].getClassName()  + ":" + txt;
		
		System.out.println(msg);
		
		
		String strFile = "logss" + File.separator + base.getPlatform() + "-" + base.getDeviceName()
		+ File.separator + base.getDateTime();
		
		File  logfile = new File(strFile);
		if (!logfile.exists())
		{
			logfile.mkdirs();
		}
		
		FileWriter filewrite = null;
		
		try {
			filewrite = new FileWriter( logfile + File.separator + "log.txt"   , true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printwriter = new PrintWriter(filewrite);
		printwriter.println(msg);
		printwriter.close();
		
		
		
	}

	
	public Logger log() {
		return (Logger) LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());
	}

        

	
}
