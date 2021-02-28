package com.martin.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

public class ConfigReader {
	String result = "";
	InputStream inputStream;
 
	public Hashtable<String,String> getPropValues(String propertyFileLocation) throws IOException {
 
		Hashtable<String,String> propertyDictionary = new Hashtable<String,String>();
		try {
			Properties prop = new Properties();
			
			// Need to place the values into the hashtable
			// Need to pass the hashtable back to the main class
			
			inputStream = new FileInputStream(propertyFileLocation);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propertyFileLocation + "' not found");
			}
 
			String[] propertyNames = {"user","password","packagerLocation","remoteServerPort"
					,"remoteServerHostname","messageTypeIndicatorColumnName","dataelementColumnIndicator"
					,"subelementColumnIndicator","sqlDriverClass","sqlConnectionString"
					,"getpendingMessagesProcedure","insertMessageResultsProcedure","sleepMilliseconds"
					,"channelTimeoutMilliseconds"};
			for(String property : propertyNames)
			{
				propertyDictionary.put(property, prop.getProperty(property));
				System.out.println("Found the [ " + property + " ] property: " + prop.getProperty(property));
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return propertyDictionary;
	}
}
