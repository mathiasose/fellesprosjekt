package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

	public static void update(String update) throws SQLException {
		Statement stmt = connect().createStatement();
		stmt.executeUpdate(update);
	}

	public static ResultSet query(String query) throws SQLException {
		Statement stmt = connect().createStatement();
		return stmt.executeQuery(query);
	}
	
	private static int selectLastInsertID() {
		try {
			ResultSet rs = query("select last_insert_id()");
			while (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static ResultSet selectAppointments(String email) {
		int id = selectEmployeeId(email);
		try {
			return query("select Appointment.* from Appointment, Employee, Invitation where (Employee.id = "
					+ id
					+ ") and (Employee.id = Invitation.employee_id) and (Invitation.appointment_id = Appointment.id)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> selectParticipantEmails(int appointmentID) {
		ArrayList<String> emails = new ArrayList<String>();
		try {
			ResultSet rs = query("select Employee.email from Employee, Appointment, Invitation where (Appointment.id = "
					+ appointmentID
					+ ") and (Invitation.appointment_id = Appointment.id) and (Employee.id = Invitation.employee_id)");
			while (rs.next()) {
				emails.add(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emails;
	}

	public static int selectEmployeeId(String email) {
		try {
			ResultSet rs = query("select Employee.id from Employee where Employee.email = "
					+ email);
			while (rs.next()) {
				return rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static ArrayList<Integer> selectAllRoomIDs() {
		ArrayList<Integer> rooms = new ArrayList<Integer>();
		try {
			ResultSet rs = query("select * from Room");
			while (rs.next()) {
				rooms.add(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rooms;
		// show capacity?
	}

	public static int insertAppointment(String start_time, String duration,
			String location, String canceled) {
		try {
			update("insert into Appointment(start_time, duration, location, canceled) values('"
					+ start_time
					+ "', '"
					+ duration
					+ "', '"
					+ location
					+ "', '" + canceled + "')");
			return selectLastInsertID();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void insertInvitation(String employeeId,
			String appointmentId, String creator, String hidden) {
		try {
			update("insert into Invitation (employee_id, appointment_id, creator, hidden) values('"
					+ employeeId
					+ "', '"
					+ appointmentId
					+ "' , '"
					+ creator
					+ "', '" + hidden + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertReservation(String appointment_id, String room_id) {
		try {
			update("insert into Reservation (appointment_id, room_id) values('"
					+ appointment_id + "', '" + room_id + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	// public static int selectAppointmentID() {
	// try {
	// ResultSet rs = query("select Appointment.id from Appointment");
	// while (rs.next()) {
	// return rs.getInt("id");
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// return -1;
	//
	// }
}
