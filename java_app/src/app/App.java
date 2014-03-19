package app;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import appointment.AddAppointmentView;
import login.LoginView;
import login.UserSession;

public class App {
	
	private UserSession session;
	private JFrame frame;
	private CalendarView kalender;
	private AddAppointmentView addAppointmentView;
	private LoginView login;

	public App() {
		session = new UserSession(this);
		
		frame = new JFrame("Coolendar");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		kalender = new CalendarView(session);
		addAppointmentView = new AddAppointmentView(session);
		login = new LoginView(session);

		frame.add(login);

		frame.setSize(new Dimension(1600, 800));
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new App();
	}

	public void goToCalendar() {
		login.setVisible(false);
		frame.add(kalender);
		kalender.setVisible(true);
		System.out.println("WELCOME!");
	}

	// log out fra kalender view
	public void logoutAndGoToLogin() {
		session.end();
		kalender.setVisible(false);
		login.setVisible(true);
		System.out.println("GOOD BYE!");
	}

	// Tar deg til AddAppointment Viewet
	public void goToAddApointment() {
		kalender.setVisible(false);
		frame.add(addAppointmentView);
		addAppointmentView.setVisible(true);
		System.out.println("PLEASE INPUT INFORMATION ABOUT YOUR APPOINTMENT!");
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

	// Cancel tilbake til KalenderView
	public void cancelAddAppointment() {
		addAppointmentView.setVisible(false);
		frame.add(kalender);
		kalender.setVisible(true);
		System.out.println("APPOINTMENT MAKING WAS CANCELED");
	}

	public void showMessageDialog(String msg) {
		JOptionPane.showMessageDialog(frame, msg);
	}

}
