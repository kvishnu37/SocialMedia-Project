package Util;

//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import org.h2.jdbcx.JdbcDataSource;
//import org.h2.tools.RunScript;

/**
 * The ConnectionUtil class will be utilized to create an active connection to
 * our database. This class utilizes the singleton design pattern. We will be
 * utilizing an in-memory called h2database for the sql demos.
 *
 * DO NOT CHANGE ANYTHING IN THIS CLASS
 */
public class ConnectionUtil {
	 private static String url = "jdbc:mysql://localhost:3306/socialmedia";
    private static String username = "root"; // your MySQL username
    private static String password = "Vishnu@576"; // your MySQL password
	 public static Connection getConnection() {
		 try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}
	
}
