package jpossim.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.jpos.iso.ISOMsg;

public class DatabaseConnection {
	public static ISOArray getPendingMessage(Hashtable<String,String> propertyDictionary)
	{
		final org.apache.logging.log4j.Logger LOGGER = LogManager.getLogger();
		ISOArray isoArray = new ISOArray();
		ISOMsg request = null; 
		String messageTypeIndicatorColumnName = propertyDictionary.get("messageTypeIndicatorColumnName"); 
		String elementSearchString = propertyDictionary.get("dataelementColumnIndicator"); 
		String subelementSearchString = propertyDictionary.get("subelementColumnIndicator"); 
		LOGGER.debug("Attempting try for getPendingMessages");
		try {
			Class.forName(propertyDictionary.get("sqlDriverClass")); 
			Connection con = DriverManager.getConnection(propertyDictionary.get("sqlConnectionString")); 

			String sql = propertyDictionary.get("getpendingMessagesProcedure");
			LOGGER.debug("Attempting to run stored procedure [" + propertyDictionary.get("getpendingMessagesProcedure") + "] to get results");
			CallableStatement stmt = con.prepareCall(sql);
			ResultSet rs = stmt.executeQuery();
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			int isoElementNumber = 0;
			String subelementColumnName = null;
			while (rs.next()) 
			{
				isoArray.setIdNumber(rs.getInt("id")); 
				request = new ISOMsg();
				for (int i = 1; i <= columnCount; i++ ) {
				  String columnName = rsmd.getColumnName(i);
				  System.out.println("Found Column Name:" + columnName);
				  request.setMTI(rs.getString(messageTypeIndicatorColumnName));
				  if(columnName.contains(elementSearchString))
				  {
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
			System.out.println("ISO Message Found"); 
			return isoArray;
		}		
	}
	
}
