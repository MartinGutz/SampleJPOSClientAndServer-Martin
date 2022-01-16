package jpossim.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;

import org.jpos.iso.ISOMsg;

public class DatabaseConnection {
	public static ISOArray getPendingMessage(Hashtable<String,String> propertyDictionary)
	{
		ISOArray isoArray = new ISOArray();
		ISOMsg request = null; //new ISOMsg();
		String messageTypeIndicatorColumnName = propertyDictionary.get("messageTypeIndicatorColumnName"); //$NON-NLS-1$
		String elementSearchString = propertyDictionary.get("dataelementColumnIndicator"); //$NON-NLS-1$
		String subelementSearchString = propertyDictionary.get("subelementColumnIndicator"); //$NON-NLS-1$
		System.out.println("Attempting try for getPendingMessages");
		try {
			Class.forName(propertyDictionary.get("sqlDriverClass")); //$NON-NLS-1$
			Connection con = DriverManager.getConnection(propertyDictionary.get("sqlConnectionString")); //$NON-NLS-1$

			String sql = propertyDictionary.get("getpendingMessagesProcedure");
			System.out.println("Attempting to run stored procedure [" + propertyDictionary.get("getpendingMessagesProcedure") + "] to get results");
			CallableStatement stmt = con.prepareCall(sql);
			ResultSet rs = stmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			// The column count starts from 1
			int isoElementNumber = 0;
			String subelementColumnName = null;
			while (rs.next()) 
			{
				isoArray.setIdNumber(rs.getInt("id")); //$NON-NLS-1$
				request = new ISOMsg();
				for (int i = 1; i <= columnCount; i++ ) {
				  String columnName = rsmd.getColumnName(i);
				  System.out.println("Found Column Name:" + columnName);
				  // Setting the MTI
				  request.setMTI(rs.getString(messageTypeIndicatorColumnName));
				  if(columnName.contains(elementSearchString))
				  {
					  // Setting the data elements
					  System.out.println("Attempting to parse out the element location from: " + (columnName.substring(elementSearchString.length() + 1)));
					  isoElementNumber = Integer.parseInt(columnName.substring(elementSearchString.length() + 1));
					  System.out.println("Printing out the value:" + isoElementNumber);
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
		}
		if (request == null) {
			return null;
		}
		else
		{
			isoArray.setMessage(request);
			System.out.println("ISO Message Found"); //$NON-NLS-1$
			return isoArray;
		}		
	}
	
}
