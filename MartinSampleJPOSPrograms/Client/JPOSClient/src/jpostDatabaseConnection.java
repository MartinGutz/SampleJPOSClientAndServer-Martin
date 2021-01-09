import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import org.jpos.iso.ISOMsg;

public class jpostDatabaseConnection {
	public static ISOArray getPendingMessage()
	{
		ISOArray isoArray = new ISOArray();
		ISOMsg request = null; //new ISOMsg();
		String messageTypeIndicatorColumnName = Messages.getString("jpostDatabaseConnection.messageTypeColumnName"); //$NON-NLS-1$
		String elementSearchString = Messages.getString("jpostDatabaseConnection.elementColumnNameSearchString"); //$NON-NLS-1$
		String subelementSearchString = Messages.getString("jpostDatabaseConnection.subelementColumnNameSearchString"); //$NON-NLS-1$

		try {
			
			Class.forName(Messages.getString("jpostDatabaseConnection.driverType")); //$NON-NLS-1$
			Connection con = DriverManager.getConnection(Messages.getString("jpostDatabaseConnection.jdbcURL")); //$NON-NLS-1$
			//allowMultiQueries=true
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(Messages.getString("jpostDatabaseConnection.5")); //$NON-NLS-1$
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			//System.out.println(columnCount);
			

			// The column count starts from 1
			int isoElementNumber = 0;
			String subelementColumnName = null;
			while (rs.next()) 
			{
				isoArray.setIdNumber(rs.getInt("id")); //$NON-NLS-1$
				request = new ISOMsg();
				for (int i = 1; i <= columnCount; i++ ) {
				  String columnName = rsmd.getColumnName(i);
				  //System.out.println(columnName);
				  request.setMTI(rs.getString(messageTypeIndicatorColumnName));
				  if(columnName.contains(elementSearchString))
				  {
					  //System.out.println(columnName.substring(8));
					  isoElementNumber = Integer.parseInt(columnName.substring(elementSearchString.length() + 1));
					  request.set(isoElementNumber, rs.getString(columnName));
				  }
				  if(columnName.contains(subelementSearchString))
				  {
					  subelementColumnName = columnName.substring(subelementSearchString.length() + 1).replace('_', '.');
					  request.set(subelementColumnName, rs.getString(columnName));
				  }
				}	
			}
			
			rs.close();	
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		finally
		{
			// Need to confirm all connections close 
		}
		if (request == null) {
			//System.out.println("The results were null");
			return null;
		}
		else
		{
			isoArray.setMessage(request);
			//request.dump(System.out, "request: ");
			System.out.println("ISO Message Found"); //$NON-NLS-1$
			return isoArray;
		}
	}
	
}
