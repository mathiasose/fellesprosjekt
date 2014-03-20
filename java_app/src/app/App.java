package app;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Appointment;
import calendar.CalendarView;
import db.DBConnection;
import appointment.NewAppointmentView;
import appointment.AppointmentView;
import authentication.LoginView;
import authentication.UserSession;

public class App {
	public static final boolean DEBUG = true;

	public static final String DB_ERROR_MSG = "Could not talk to database. Are you sure you're connected to the internet?";
	public static final String AUTH_ERROR_MSG = "Wrong user/password combination";

	private Frame frame;

	private UserSession session;

	private CalendarView calendarView;
	private NewAppointmentView newAppointmentView;
	private AppointmentView appointmentView;
	private LoginView loginView;
	private Appointment model;

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
		newAppointmentView = new NewAppointmentView(session);
		frame.setView(newAppointmentView);
	}

	public void goToshowAppointment(Appointment model) {
		try {
			appointmentView = new AppointmentView(session, model);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame.setView(appointmentView);
	}

	// TRENGER FUNKSJONALITET HER ETTERHVERT
	public void goToshowOther() {
		// JOptionPane.showMessageDialog(frame,
		// "We will implement this as soon as we can! SORRY >_<",
		// "Comming this Spring",
		// JOptionPane.PLAIN_MESSAGE);
		Object[] showSomeonesAppointments = { "Just yours", "exampleUser1",
				"Everybody" };
		String showOtherVariabel = (String) JOptionPane.showInputDialog(frame,
				"Select your acquaintance to show their appointments",
				"Show others calenders", JOptionPane.PLAIN_MESSAGE, null,
				showSomeonesAppointments,
				"Vet ikke hvorfor eller hva denne strengen er godt for :/");

		System.out.println(showOtherVariabel + "s appointments are showing!");
		// HER M� VI VISE VALGTE DELTAGERES APPOINTMENTS!
	}

	public void cancelAddAppointmentGoToCalendar() {
		goToCalendar();
	}

	public void validate() {
		frame.validate();
	}
}
