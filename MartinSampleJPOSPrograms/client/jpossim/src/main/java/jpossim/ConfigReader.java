package jpossim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;

public class ConfigReader {
	final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
	String result = "";
	InputStream inputStream;
 
	public Hashtable<String,String> getPropValues(String propertyFileLocation) throws IOException {
 
		Hashtable<String,String> propertyDictionary = new Hashtable<String,String>();
		try {
			Properties prop = new Properties();
			inputStream = new FileInputStream(propertyFileLocation);
 
			LOGGER.debug("Attempting to read the property file");
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				LOGGER.error("Property file '" + propertyFileLocation + "' not found");
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
				LOGGER.debug("Found the [ " + property + " ] property: " + prop.getProperty(property));
			}
		} catch (Exception e) {
			LOGGER.error(e);
		} finally {
			inputStream.close();
		}
		return propertyDictionary;
	}
}