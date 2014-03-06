package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class DBConnection {

    public static Connection connect() {
        try {
            String url = "jdbc:mysql://mysql.stud.ntnu.no/tornvall_felles";
            String username = "tornvall_g3";
            String password = "123abc";
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
