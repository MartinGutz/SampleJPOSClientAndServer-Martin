package jpossim;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Hashtable;

import org.junit.BeforeClass;
import org.junit.Test;

public class ClientTest {

	static Hashtable<String,String> myProperties;
	@BeforeClass public static void onlyOnce() throws IOException {
		ConfigReader reader = new ConfigReader();
		myProperties = reader.getPropValues("C:\\temp\\config.properties");
    }
	
	@Test
	public void testPropertyUser() {
		assertEquals("myUser",myProperties.get("user"));
	}

	@Test
	public void testPropertyPassword() {
		assertEquals("Password1",myProperties.get("password"));
	}
	
	
}
