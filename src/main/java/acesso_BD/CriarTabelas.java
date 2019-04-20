package acesso_BD;

import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CriarTabelas {

	public static void main(String[] args) {
		try {
			Connection conn = getConnection();
			String insertTableSQL;
			insertTableSQL = "CREATE TABLE treinamento (" + 
					" id SERIAL PRIMARY KEY," + 
					" nome varchar (100) NOT NULL," + 
					" atividade varchar (100) NOT NULL," + 
					" mediaAccelX float NOT NULL," + 
					" mediaAccelY float NOT NULL," + 
					" mediaAccelZ float NOT NULL," + 
					" rmsAccelX float NOT NULL," + 
					" rmsAccelY float NOT NULL," + 
					" rmsAccelZ float NOT NULL," + 
					" mediaGyroX float NOT NULL," + 
					" mediaGyroY float NOT NULL," + 
					" mediaGyroZ float NOT NULL," + 
					" rmsGyroX float NOT NULL," + 
					" rmsGyroY float NOT NULL," + 
					" rmsGyroZ float NOT NULL" +
					");";
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			ResultSet resultSet = preparedStatement.executeQuery();
						
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
