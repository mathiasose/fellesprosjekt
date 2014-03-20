package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Appointment;
import app.App;

public class DBConnection {

	public static Connection connect() throws SQLException {
		String url = "jdbc:mysql://mysql.stud.ntnu.no/tornvall_felles";
		String username = "tornvall_g3";
		String password = "123abc";
		return DriverManager.getConnection(url, username, password);
	}

	private static int update(String update) throws SQLException {
		Connection connection = connect();
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(update);
		return selectLastInsertID(connection);
	}

	public static ResultSet query(String query) throws SQLException {
		if (App.DEBUG) {
			System.out.println(query);
		}
		Statement stmt = connect().createStatement();
		return stmt.executeQuery(query);
	}

	private static int selectLastInsertID(Connection connection)
			throws SQLException {
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select last_insert_id()");
		while (rs.next()) {
			return rs.getInt(1);
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

	public static ArrayList<String> selectAllRoomIDs() throws SQLException {
		ArrayList<String> rooms = new ArrayList<String>();
		ResultSet rs = query("select * from Room");
		while (rs.next()) {
			rooms.add(rs.getString("id"));
			rooms.add(rs.getString("capacity"));
		}
		return rooms;
	}

	public static int insertAppointment(String start_time, String duration,
			String location, String description, String canceled)
			throws SQLException {
		return update("insert into Appointment(start_time, duration, location, description, canceled) values('"
				+ start_time
				+ "', '"
				+ duration
				+ "', '"
				+ location
				+ "', '"
				+ description + "', '" + canceled + "')");
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

	public static void insertReservation(String appointment_id, String room_id)
			throws SQLException {
		update("insert into Reservation (appointment_id, room_id) values('"
				+ appointment_id + "', '" + room_id + "')");
	}

	public static ArrayList<Appointment> selectAppointments(String email,
			int weekNo) throws EmailNotInDatabaseException, SQLException {
		weekNo = weekNo - 1; // sql 0-indexes weeks
		if (weekNo < 0) {
			weekNo = 51;
		}

		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		ArrayList<String> participants = new ArrayList<String>();
		int id = selectEmployeeId(email);
		ResultSet rs = query("select Appointment.*, Reservation.* "
				+ "from (Appointment left join Reservation on Appointment.id = Reservation.appointment_id), "
				+ "Employee, Invitation where (Employee.id = '"
				+ id
				+ "') "
				+ "and (Employee.id = Invitation.employee_id) "
				+ "and (Invitation.appointment_id = Appointment.id) and (week(start_time)="
				+ weekNo + ")");
		while (rs.next()) {
			Appointment appointment = new Appointment();
			int appointmentID = rs.getInt("id");
			appointment.setEventID(appointmentID);
			appointment.setLocation(rs.getString("location"));
			appointment.setDuration(rs.getInt("duration"));
			appointment.setDescription(rs.getString("description"));
			appointment.setAppointmentTime(rs.getTimestamp("start_time"));
			appointment.setMeetingRoom(rs.getInt("room_id"));
			appointment.setEventID(appointmentID);

			// appointment.setParticipants();

			ResultSet rs2 = query("select email "
					+ "from (Invitation left join Appointment on (Invitation.appointment_id = Appointment.id) "
					+ "left join Employee on (Invitation.employee_id = Employee.id)) "
					+ "where (Invitation.appointment_id=" + appointmentID
					+ ") and (Invitation.appointment_id =" + appointmentID
					+ ")");
			while (rs2.next()) {
				participants.add(rs2.getString("email"));
			}
			appointment.setParticipants(participants);

			ResultSet rs3 = query("select employee_id from Invitation where creator = 1 and appointment_id ="
					+ appointmentID);
			rs3.next();
			appointment.setCreatedByID(rs3.getInt("employee_id"));

			appointments.add(appointment);
		}

		return appointments;
	}

	public static ArrayList<Boolean> selectAttendingStatus(int appointmentID)
			throws SQLException {
		ArrayList<Boolean> attendingStatus = new ArrayList<Boolean>();
		ResultSet rs = query("select Invitation.attending from Invitation"
				+ " where Invitation.appointment_id =" + appointmentID);
		while (rs.next()) {
			String s = rs.getString("attending");
			if (s == null) {
				attendingStatus.add(null);
			} else if (s == "1") {
				attendingStatus.add(true);
			} else if (s == "0") {
				attendingStatus.add(false);
			}
		}

		return attendingStatus;

	}

	public static void updateInvitationStatus(int appointmentID,
			int employeeID, boolean status) throws SQLException {
		String sqlBoolStatus = status ? "1" : "0";
		update("update Invitation SET attending = " + sqlBoolStatus
				+ " where (appointment_id = " + appointmentID + ")"
				+ " and (employee_id = " + employeeID + ")");
	}
}
