import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UpdateJPOSStatus {
	public static void updateStatus(int id, String responseCode) {
		try {

			Class.forName(Messages.getString("UpdateJPOSStatus.driverClass")); //$NON-NLS-1$
			//Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/isomessages", "martin",
				//	"Password1");
			Connection con = DriverManager.getConnection(Messages.getString("UpdateJPOSStatus.jdbcURL")); //$NON-NLS-1$

			// STEP 4: Execute a query
			System.out.println("Inserting statement..."); //$NON-NLS-1$
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO messageresults (messageDatetime, id, element_39) VALUES (NOW()," + id + "," + responseCode + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			System.out.println(sql);
			stmt.executeUpdate(sql);
			
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			// Need to confirm all connections close
			
		}

	}
}
