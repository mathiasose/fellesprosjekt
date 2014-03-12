package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;


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

	public static ResultSet getAppointments(String email) {
		int id = getEmployeeId(email);
		try{
			return query("select Appointment.* from Appointment, Employee, Invitation where (Employee.id = "+id+") and (Employee.id = Invitation.employee_id) and (Invitation.appointment_id = Appointment.id)");
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayList<String> getParticipantEmails(int appointmentID) {
		ArrayList<String> emails = new ArrayList<String>();
		try {
			ResultSet rs = query("select Employee.email from Employee, Appointment, Invitation where (Appointment.id = "+appointmentID+") and (Invitation.appointment_id = Appointment.id) and (Employee.id = Invitation.employee_id)");
			while(rs.next()){
				emails.add(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emails;
	}
	
	public static int getEmployeeId(String email){
		try{
			ResultSet rs = query("select Employee.id from Employee where Employee.email = "+email);
			while(rs.next()){
				return rs.getInt("id");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static ArrayList<Integer> getAllRoomIDs(){
		ArrayList<Integer> rooms = new ArrayList<Integer>();
		try{
			ResultSet rs = query("select * from Room");
			while(rs.next()){
				rooms.add(rs.getInt("id"));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return rooms;
	}
	
	
	public static void addAppointment(String start_time, String duration, String location, String canceled){
		try {
			update("insert into Appointment(start_time, duration, location, canceled) values("+"'"+start_time+"'"+", " + "'"+duration+"'"+", " + "'"+location+"'"+", " + "'"+canceled+"'"+")");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	

	// ///////////////lager metode for å legge til appointment i
	// DB//////////////////////

//	public void addAppointment() {
//		try {
//			query("insert into Appointment(location) values('HAHAHAHAHHA')");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

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
		System.out.println(getParticipantEmails(1));
	}
}
