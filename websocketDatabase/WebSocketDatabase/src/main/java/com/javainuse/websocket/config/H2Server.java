package com.javainuse.websocket.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Server {
	
	public static void main(String[] args) throws SQLException {
		 H2Server h2 = new  H2Server();
		 System.out.println(h2.connectionToH2("Select * from students", "usergio", "1234"));
	}
	
	public String connectionToH2(String sql, String username, String password) throws SQLException {
		System.out.println(username);
		String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
        //String username = "usergio";
        //String password = "1234";
        
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connected to H2 in server mode.");
        
        String print = "";
        
        if(sql.toLowerCase().contains("select")) {
        	print = query(sql, connection);
        }else {
        	update(sql, connection);
        	sql = "SELECT * FROM students";
        	print = query(sql, connection);
        }
        connection.close();
        return print;
	}
	
	public String query(String sql, Connection connection) throws SQLException {
		Statement statement = connection.createStatement();
			 
        ResultSet resultSet = statement.executeQuery(sql);
 
        int count = 0;
        StringBuilder print = new StringBuilder();
 
        while (resultSet.next()) {
            count++;
 
            int ID = resultSet.getInt("ID");
            String name = resultSet.getString("name");
            print.append("\nStudent #" + count + ": " + ID + ", " + name);
            //System.out.println("Student #" + count + ": " + ID + ", " + name);
        }
        String ret = ""  + print;
        return ret;
	}
	
	public void update(String sql, Connection connection) throws SQLException {
		//String sql = "INSERT INTO students (name) values ('Lisa')";
        Statement statement = connection.createStatement();
        int result = statement.executeUpdate(sql);
        
        if(sql.toLowerCase().contains("insert")) {
        	if(result>0) {
	        	System.out.println("Se inserto un registro");
	        }
        }else if(sql.toLowerCase().contains("delete")){
        	if(result>0) {
	        	System.out.println("Se elimino un registro");
	        }
        }
        
        
	}
	
	 /*public static void main(String[] args) throws SQLException {
	        String jdbcURL = "jdbc:h2:tcp://localhost/~/test";
	        String username = "usergio";
	        String password = "1234";
	        
	        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
	        System.out.println("Connected to H2 in server mode.");
	        
	        
	        String sql = "INSERT INTO students (name) values ('Lisa')";
	        
	        //String sql = "SELECT * FROM students";
	 
	        Statement statement = connection.createStatement();
	        int resultINSERT = statement.executeUpdate(sql);
	 
	        if(resultINSERT>0) {
	        	System.out.println("Se inserto un registro");
	        }
	        
	 
	        sql = "SELECT * FROM students";
	 
	        statement = connection.createStatement();
	 
	        ResultSet resultSet = statement.executeQuery(sql);
	 
	        int count = 0;
	 
	        while (resultSet.next()) {
	            count++;
	 
	            int ID = resultSet.getInt("ID");
	            String name = resultSet.getString("name");
	            System.out.println("Student #" + count + ": " + ID + ", " + name);
	        }
	        
	        
	 
	        connection.close();
	    }*/
}
