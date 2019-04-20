package acesso_BD;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class NewSQL {

	public static void main(String[] args) {
		try {
			Connection conn = getConnection();
			Scanner entrada = new Scanner(System.in);
			while(true) {				
				String string = entrada.nextLine();
				if(string.equals("exit")) {
					break;
				}
				String insertTableSQL = string;
				PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
				ResultSet resultSet = preparedStatement.executeQuery();
				System.out.println(resultSet);
			}
			conn.close();
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static Connection getConnection() throws URISyntaxException, SQLException {
	    String dbUrl = System.getenv("JDBC_DATABASE_URL");
	    return DriverManager.getConnection(dbUrl);
	}
}


