package jpossim;

import java.io.IOException;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.internal.DefaultLogBuilder;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;

import jpossim.util.DatabaseConnection;
import jpossim.util.ISOArray;

public class Client {

	
	public static void main(String[] args) throws IOException, ISOException, InterruptedException {
		// TODO Auto-generated method stub
		final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
		LOGGER.info("Starting Program");
		System.out.println("Starting Client");
		System.out.println("Running Application");
		String propertyFileLocation = args[0];
		System.out.println("Property File Location Provided: " + propertyFileLocation);
		
		ConfigReader reader = new ConfigReader();
		Hashtable<String,String> propertyDictionary = reader.getPropValues(propertyFileLocation);
		
		ISOPackager gPackager = new GenericPackager(propertyDictionary.get("packagerLocation"));
        int channelPort = Integer.parseInt(propertyDictionary.get("remoteServerPort")); 
    	BaseChannel channel = new PostChannel(propertyDictionary.get("remoteServerHostname"), channelPort, gPackager); 
    	channel.connect();
    	Long sleepValue = Long.parseLong(propertyDictionary.get("sleepMilliseconds"));
    	int timeoutValue = Integer.parseInt(propertyDictionary.get("channelTimeoutMilliseconds"));
    	System.out.println("Channel Timeout set to " + timeoutValue + " milliseconds");
    	while(true)
        {
            ISOArray isoArray = DatabaseConnection.getPendingMessage(propertyDictionary);
            if(isoArray == null)
            {
            	System.out.println("No Pending Messages Found"); //$NON-NLS-1$
            }else
            {
            	System.out.println("ID Number: " + isoArray.getIdNumber()); //$NON-NLS-1$
                ISOMsg request = isoArray.getMessage();
                
                request.dump(System.out, "request: ");
                if(channel.isConnected())
                {
                	
                	System.out.println("Attempting to send message"); //$NON-NLS-1$
                	channel.send(request);
                }else
                {
                	System.out.println("Attempting to reconnect"); //$NON-NLS-1$
                	channel.connect();
                	
                	System.out.println("Attempting to send message"); //$NON-NLS-1$
                	channel.send(request);	
                }
                channel.setTimeout(timeoutValue);
                ISOMsg response = channel.receive();
                
                //System.out.println(ISOUtilities.doRender(response));
                response.dump(System.out, "DUMP:");
                System.out.println("Response: " + response.getValue(39)); //$NON-NLS-1$
                UpdateJPOSStatus.updateStatus(isoArray.getIdNumber(), response, propertyDictionary );
            }
            System.out.println("Sleeping for " + sleepValue + " milliseconds");
            Thread.sleep(sleepValue);
        }
		
	}

}
