package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * This is the utility class for data read write
 *
 * @author QAIT
 *
 */
public class ConfigPropertyReader {

	public static String getProperty(String value) { 
	try {
		Properties prop = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "\\Config.properties");
		prop.load(ip);
		return prop.getProperty(value);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return null;
	}
}
