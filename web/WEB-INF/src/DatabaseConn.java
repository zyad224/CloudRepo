import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConn {
    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/students";
        return DriverManager.getConnection(url, "root", "123");

    }
}
