import java.io.IOException;

import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;

public class JposDBClient {

    public static void main(String[] args) throws ISOException, IOException, InterruptedException {
    	ISOPackager gPackager = new GenericPackager(Messages.getString("JposDBClient.packgerLocation")); //$NON-NLS-1$
        int channelPort = Integer.parseInt(Messages.getString("JposDBClient.channelPort")); //$NON-NLS-1$
    	BaseChannel channel = new PostChannel(Messages.getString("JposDBClient.hostName"), channelPort, gPackager); //$NON-NLS-1$
        
        channel.connect();
        while(true)
        {
            ISOArray isoArray = jpostDatabaseConnection.getPendingMessage();
            if(isoArray == null)
            {
            	System.out.println("No Pending Messages Found"); //$NON-NLS-1$
            }else
            {
            	System.out.println("ID Number: " + isoArray.getIdNumber()); //$NON-NLS-1$
                ISOMsg request = isoArray.getMessage();
                
                //request.dump(System.out, "request: ");
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
                channel.setTimeout(10000);
                ISOMsg response = channel.receive();
                
                //System.out.println(ISOUtilities.doRender(response));
                response.dump(System.out, "DUMP:");  
                System.out.println("Response: " + response.getValue(39)); //$NON-NLS-1$
                UpdateJPOSStatus.updateStatus(isoArray.getIdNumber(), (String) response.getValue(39));
            }
            //System.out.println("Sleeping for a couple of seconds");
            Thread.sleep(3000);
        }
        


    }

}