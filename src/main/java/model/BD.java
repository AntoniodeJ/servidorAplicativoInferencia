package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class BD {
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost/inferenciaAtividades?useTimezone=true&serverTimezone=UTC";

	   //  Database credentials
	private static final String USER = "root";
	private static final String PASS = "mono";
	private Connection conn = null;
	private Statement stmt = null;
	
	public void close(){

	      try {
	    	  //stmt.close();
	    	  conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public void connect(){
		  
		   try{
		      //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);

		      //STEP 3: Open a connection
		      System.out.println("Conectando ao BD");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		   }catch(Exception e){
		      //Handle errors for Class.forName
		      e.printStackTrace();
		   }
	}

	public boolean saveTreinamento(String nome, String atividade,
			float mediaAccelX, float mediaAccelY, float mediaAccelZ,
			float rmsAccelX, float rmsAccelY, float rmsAccelZ,
			float mediaGyroX, float mediaGyroY, float mediaGyroZ,
			float rmsGyroX, float rmsGyroY, float rmsGyroZ){
		if(conn==null){
			return false;
		}
		String insertTableSQL = "INSERT INTO treinamento"
				+ "(nome,atividade,mediaAccelX,mediaAccelY,mediaAccelZ,rmsAccelX,rmsAccelY,rmsAccelZ,mediaGyroX,mediaGyroY,mediaGyroZ,rmsGyroX,rmsGyroY,rmsGyroZ) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			System.out.println("Salvando no BD");
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, atividade);
			preparedStatement.setFloat(3, mediaAccelX);
			preparedStatement.setFloat(4, mediaAccelY);
			preparedStatement.setFloat(5, mediaAccelZ);
			preparedStatement.setFloat(6, rmsAccelX);
			preparedStatement.setFloat(7, rmsAccelY);
			preparedStatement.setFloat(8, rmsAccelZ);
			preparedStatement.setFloat(9, mediaGyroX);
			preparedStatement.setFloat(10, mediaGyroY);
			preparedStatement.setFloat(11, mediaGyroZ);
			preparedStatement.setFloat(12, rmsGyroX);
			preparedStatement.setFloat(13, rmsGyroY);
			preparedStatement.setFloat(14, rmsGyroZ);
			
			preparedStatement .executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public ResultSet selectContextData(String type, String location){
		if(conn==null){
			return null;
		}
		String insertTableSQL = "Select * from contextdata where name=? and data like ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, type);
			preparedStatement.setString(2,"%"+location+"%");
			return preparedStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean findContextData(String id){
		if(conn==null){
			return false;
		}
		String insertTableSQL = "Select * from contextdata where uuid = ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, "%"+id+"%");
			return preparedStatement.executeQuery().next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean checkConnection(){
		return conn!=null;
	}
	
	public void updateData(int id,String data){
		if(conn==null){
			return ;
		}
		String insertTableSQL = "update context.contextdata set `data`=?  where id=? ";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
	
			preparedStatement.setString(1, data);
			preparedStatement.setInt(2, id);
			 
			if(preparedStatement.execute()){
				System.out.println("registro "+id+" atualizado");
				return;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void dump(){
		if(conn==null){
			return ;
		}
		String insertTableSQL = "SELECT * FROM context.contextdata where id>8539 and `data` like '%{\"position\":\"right hip(pants)%'";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(insertTableSQL);
			preparedStatement.executeQuery();
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()){
				Integer id=resultSet.getInt("id");
				String dataStr = resultSet.getString("data");
				String prefix="{\"position\":\"waist(belt)";
				
				System.out.println(id);
				System.out.println(dataStr);
				System.out.println(prefix+dataStr.substring(29));
				updateData(id, prefix+dataStr.substring(29));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
}
