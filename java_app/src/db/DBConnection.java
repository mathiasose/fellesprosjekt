package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Appointment;

public class DBConnection {

	public static Connection connect() throws SQLException {
		String url = "jdbc:mysql://mysql.stud.ntnu.no/tornvall_felles";
		String username = "tornvall_g3";
		String password = "123abc";
		return DriverManager.getConnection(url, username, password);
	}

	public static int update(String update) throws SQLException {
		Connection connection = connect();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(update);
		return selectLastInsertID(connection);
	}

	public static ResultSet query(String query) throws SQLException {
		System.out.println(query);
		Statement stmt = connect().createStatement();
		return stmt.executeQuery(query);
	}

	private static int selectLastInsertID(Connection connection) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select last_insert_id()");
			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static int selectEmployeeId(String email)
			throws EmailNotInDatabaseException, SQLException {
		ResultSet rs = query("select Employee.id from Employee where Employee.email = '"
				+ email + "'");
		while (rs.next()) {
			return rs.getInt("id");
		}
		throw new EmailNotInDatabaseException();
	}

	public static boolean tryLogin(String email, String password)
			throws SQLException, EmailNotInDatabaseException {
		int id = selectEmployeeId(email);
		ResultSet rs = query("select Employee.password from Employee where Employee.id = "
				+ id);
		while (rs.next()) {
			return rs.getString("password").equals(password);
		}
		throw new EmailNotInDatabaseException();
	}

	public static ArrayList<String> selectParticipantEmails(int appointmentID)
			throws SQLException {
		ArrayList<String> emails = new ArrayList<String>();
		ResultSet rs = query("select Employee.email from Employee, Appointment, Invitation where (Appointment.id = "
				+ appointmentID
				+ ") and (Invitation.appointment_id = Appointment.id) and (Employee.id = Invitation.employee_id)");
		while (rs.next()) {
			emails.add(rs.getString("email"));
		}
		return emails;
	}

	public static ArrayList<Integer> selectAllRoomIDs() throws SQLException {
		ArrayList<Integer> rooms = new ArrayList<Integer>();
		ResultSet rs = query("select * from Room");
		while (rs.next()) {
			rooms.add(rs.getInt("id"));
		}
		return rooms;
		// show capacity?
	}

	public static int insertAppointment(String start_time, String duration,
			String location, String description, String canceled)
			throws SQLException {
		return update("insert into Appointment(start_time, duration, location, description, canceled) values('"
				+ start_time
				+ "', '"
				+ duration
				+ "0000"
				+ "', '"
				+ location
				+ "', '" + description + "', '" + canceled + "')");
	}

	public static void insertInvitation(String employeeId,
			String appointmentId, String creator, String hidden)
			throws SQLException {
		update("insert into Invitation (employee_id, appointment_id, creator, hidden) values('"
				+ employeeId
				+ "', '"
				+ appointmentId
				+ "' , '"
				+ creator
				+ "', '" + hidden + "')");
	}

	public static void insertReservation(String appointment_id, String room_id) {
		try {
			update("insert into Reservation (appointment_id, room_id) values('"
					+ appointment_id + "', '" + room_id + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Appointment> selectAppointments(String email, int weekNo)
	// finner avtalene som tilh�rer email parameteren:
			throws EmailNotInDatabaseException, SQLException {
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		ArrayList<Integer> participants = new ArrayList<Integer>();
		int id = selectEmployeeId(email);
		try {
			ResultSet rs = query("select Appointment.*, Reservation.* from Appointment left join Reservation on Appointment.id = Reservation.appointment_id, Employee, Invitation where (Employee.id = '"
					+ id
					+ "') and (Employee.id = Invitation.employee_id) and (Invitation.appointment_id = Appointment.id) and (week(start_time)='"+weekNo+"')");
			while (rs.next()) {
				Appointment appointment = new Appointment();
				appointment.setEventID(rs.getInt("id"));
				appointment.setLocation(rs.getString("location"));
				appointment.setDuration(rs.getInt("duration"));
				appointment.setDescription(rs.getString("description"));
				appointment.setAppointmentTime(rs.getTimestamp("start_time"));
				appointment.setMeetingRoom(rs.getInt("room_id"));
				appointment.setEventID(rs.getInt("id")); 
				// appointment.setParticipants();

				ResultSet rs2 = query("select Invitation.* from (Invitation left join Appointment on Invitation.appointment_id = Appointment.id)"
						+ " where Appointment.id ="
						+ rs.getInt("id")
						+ " and Invitation.appointment_id =" + rs.getInt("id"));
				while (rs2.next()) {
					participants.add(rs2.getInt("employee_id"));
				}
				//appointment.setParticipants(participants);
				appointments.add(appointment);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return appointments;
	}


	
//	public static Appointment selectAppointmentInfo(int appointmentID){
//		Appointment appointment = new Appointment();
//		try {
//
//			ResultSet rs = query("select * from Appointment where Appointment.id=" +appointmentID);
//
//			while(rs.next()){
//				appointment.setLocation(rs.getString("location"));
//				appointment.setDuration(rs.getInt("duration"));
//				appointment.setDescription(rs.getString("description"));
//				appointment.setAppointmentTime(rs.getTimestamp("start_time"));
//				appointment.setMeetingRoom(rs.getInt("room_id"));
//				//appointment.setParticipants();
//
//			}
//			ResultSet rs2 = query("select Reservation.room_id from Reservation where Reservation.appointment_id=" +appointmentID);
//			while(rs2.next()){
//				appointment.setMeetingRoom(rs2.getInt("room_id"));
//			}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return appointment;
//	}
	

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
