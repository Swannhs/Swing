import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
	static Connection con;
	static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/swann","root","swann");
		}catch(ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
}
