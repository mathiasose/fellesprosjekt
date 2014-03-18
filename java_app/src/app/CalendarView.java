package app;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JComponent;
import javax.swing.JFrame;

import model.Appointment;

//la til action listener
public class CalendarView extends JComponent {
	private final DayPanel[] week = { new DayPanel("Mandag"),
			new DayPanel("Tirsdag"), new DayPanel("Onsdag"),
			new DayPanel("Torsdag"), new DayPanel("Fredag"),
			new DayPanel("Lørdag"), new DayPanel("Søndag") };

	public CalendarView() {
		setLayout(new GridLayout(1, 7));
		for (DayPanel dp : week) {
			add(dp);
		}
	}

	private void addAppointment(Appointment appointment) {
		@SuppressWarnings("deprecation")
		int day = appointment.getDate().getDay();
		if (day == 0) {
			day = 6;
		} else {
			day -= 1;
		}
		AppointmentPanel panel = new AppointmentPanel(appointment);
//		panel.setSize(new Dimension(this.getWidth(), 100));
//		panel.setMaximumSize(panel.getSize());
		week[day].add(panel);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Kalender");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(1600, 800));
//		frame.pack();
		frame.setVisible(true);

		CalendarView mainCal = new CalendarView();
		mainCal.initTest();
		frame.add(mainCal);
	}
	
	@SuppressWarnings("deprecation")
	private void initTest() {
		Appointment testAppointment = new Appointment("1", "room", new java.util.Date(2014, 3, 17, 16, 0));
		testAppointment.setEventID(42);
		testAppointment.setDuration(60);
		Appointment testAppointment2 = new Appointment("2", "room", new java.util.Date(2014, 3, 17, 12, 0));
		testAppointment2.setEventID(43);
		testAppointment2.setDuration(120);

		addAppointment(testAppointment);
		addAppointment(testAppointment2);
	}

}
