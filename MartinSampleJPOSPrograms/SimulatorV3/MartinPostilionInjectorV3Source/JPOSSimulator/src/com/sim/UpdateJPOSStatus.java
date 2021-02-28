package com.sim;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Hashtable;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;

public class UpdateJPOSStatus {
	public static void updateStatus(int id, ISOMsg response, Hashtable<String,String> propertyDictionary) {
		try {

			Class.forName(propertyDictionary.get("sqlDriverClass")); //$NON-NLS-1$
			Connection con = DriverManager.getConnection(propertyDictionary.get("sqlConnectionString")); //$NON-NLS-1$
			
			String responseString = logISOMsg(response);
			System.out.println("Inserting results..."); //$NON-NLS-1$			
			

			String sql = "{"+ propertyDictionary.get("insertMessageResultsProcedure") + " (?,?)}";
			System.out.println("Attempting to run stored procedure [" + propertyDictionary.get("insertMessageResultsProcedure") + "] to update results");
			
			CallableStatement stmt = con.prepareCall(sql);
			stmt.setInt(1, id);
			stmt.setString(2, responseString);
			stmt.execute();
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
		}
	}
	
    private static String logISOMsg(ISOMsg msg) {
        System.out.println("----ISO MESSAGE-----");
        StringBuilder isoString = new StringBuilder();
        try {
        	isoString.append(msg.getMTI());
        	System.out.println("  MTI : " + msg.getMTI());
            for (int i=1;i <= msg.getMaxField();i++) {
                if (msg.hasField(i)) {
                    System.out.println("    Field-"+i+" : "+msg.getString(i));
                    if(i < 127){
                    	isoString.append("<"+i+">" + msg.getString(i)+ "</"+i+">" );
                    }                    
                }
            }
        } catch (ISOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("--------------------");
        }
        System.out.println("ISO String: " + isoString.toString());
        return isoString.toString();
    }
	
}
