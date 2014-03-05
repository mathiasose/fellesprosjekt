package db;

import java.sql.*;

public class DBConnection {

    public static Connection connect() {
        try {
            String url = "jdbc:mysql://127.0.0.1/test";
            String username = "root";
            String password = "root";
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect", e);
        }
    }
    
    public static void main(String[] args) {
		Connection connection = connect();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Employee");
			while(rs.next()) {
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
