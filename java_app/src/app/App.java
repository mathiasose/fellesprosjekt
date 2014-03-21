package app;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Appointment;
import appointment.AppointmentView;
import appointment.EditAppointmentView;
import authentication.LoginView;
import authentication.UserSession;
import calendar.CalendarView;
import db.DBConnection;

public class App {
	public static final boolean DEBUG = true;

	public static final String DB_ERROR_MSG = "Could not talk to database. Are you sure you're connected to the internet?";
	public static final String AUTH_ERROR_MSG = "Wrong user/password combination";

	private Frame frame;

	private UserSession session;

	private CalendarView calendarView;
	private AppointmentView newAppointmentView;
	private EditAppointmentView appointmentView;
	private LoginView loginView;

	public App() {
		frame = new Frame();

		testConnection();

		session = new UserSession(this);

		loginView = new LoginView(session);
		frame.setView(loginView);
	}

	private void testConnection() {
		try {
			DBConnection.connect();
		} catch (SQLException e) {
			if (DEBUG) {
				e.printStackTrace();
			}
			showMessageDialog(DB_ERROR_MSG);
		}
	}

	public void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

	public static void main(String[] args) {
		new App();
	}

	public void goToCalendar() {
		calendarView = new CalendarView(session);
		frame.setView(calendarView);
	}

	public void logoutAndGoToLogin() {
		session.end();
		frame.setView(loginView);
	}

	public void goToAddApointment() {
		newAppointmentView = new AppointmentView(session);
		frame.setView(newAppointmentView);
	}

	public void goToshowAppointment(Appointment model) {
		try {
			appointmentView = new EditAppointmentView(session, model);
			frame.setView(appointmentView);
		} catch (SQLException e) {
			showMessageDialog(DB_ERROR_MSG);
		}

	}

	public void cancelAddAppointmentGoToCalendar() {
		goToCalendar();
	}

	public void validate() {
		frame.validate();
	}
}
