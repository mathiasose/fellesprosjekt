package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {

	private java.sql.Connection connection = null;

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

	public void setConnection(java.sql.Connection connection) {
		this.connection = connection;
	}

	public java.sql.Connection getConnection() {
		return this.connection;
	}

	public static void getAppointment() {

	}

	// ///////////////lager metode for å legge til appointment i
	// DB//////////////////////

	public void addAppointment() {
		try {
			query("insert into Appointment(location) values('HAHAHAHAHHA')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////

	public static void update(String update) throws SQLException {
		Statement stmt = connect().createStatement();
		stmt.executeUpdate(update);
	}
	
	public static ResultSet query(String query) throws SQLException {
		Statement stmt = connect().createStatement();
		return stmt.executeQuery(query);
	}

	public static void main(String[] args) {
		try {
			update("insert into Appointment(start_time, duration, location) values('2014-5-17 12:00:00', '01:01:01', 'spise is')");
			
			ResultSet rs = query("select * from Employee");
			while (rs.next()) {
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
