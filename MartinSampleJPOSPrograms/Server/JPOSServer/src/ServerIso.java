import java.io.IOException;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.ISORequestListener;
import org.jpos.iso.ISOServer;
import org.jpos.iso.ISOSource;
import org.jpos.iso.channel.NACChannel;
import org.jpos.iso.channel.PostChannel;
import org.jpos.iso.packager.GenericPackager;
import org.jpos.util.LogSource;
import org.jpos.util.Logger;
import org.jpos.util.SimpleLogListener;

public class ServerIso implements ISORequestListener{	
	public static void main(String[] args) throws IOException, ISOException {
		Logger logger = new Logger ();
		ISOPackager packager = new GenericPackager(Messages.getString("ServerIso.packagerLocation")); //$NON-NLS-1$
		logger.addListener (new SimpleLogListener (System.out));
		int serverPort = Integer.parseInt(Messages.getString("ServerIso.localPort")); //$NON-NLS-1$
		PostChannel channel = new PostChannel("localhost",serverPort, packager); //$NON-NLS-1$
		((LogSource)channel).setLogger (logger, "channel"); //$NON-NLS-1$
		ISOServer server = new ISOServer (serverPort, channel, null);
		server.setLogger (logger, "server"); //$NON-NLS-1$
		server.addISORequestListener(new ServerIso());
		new Thread (server).start ();
	}
	
	@Override
	public boolean process(ISOSource source, ISOMsg m) {
		try {
			System.out.println("Server's Custom Response"); //$NON-NLS-1$
			m.setResponseMTI ();
			m.set (39, Messages.getString("ServerIso.messageResponse")); //$NON-NLS-1$
			source.send (m);
			} catch (ISOException e) {
			e.printStackTrace();
			} catch (IOException e) {
			e.printStackTrace();
  		}
 		return true;
	}

}