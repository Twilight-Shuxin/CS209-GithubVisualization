import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreSQLJDBC {
	public static Connection connect() throws Exception {
		Connection connection = null;
		Class.forName("org.postgresql.Driver");
		connection = DriverManager
				.getConnection("jdbc:postgresql://localhost:5432/githubdata",
						"postgres", "shuxin");
		System.out.println("Opened database successfully");
		return connection;
	}
}