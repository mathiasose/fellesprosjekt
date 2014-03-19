package app;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.DBConnection;
import db.EmailNotInDatabaseException;
import login.UserSession;
import model.Appointment;

//la til action listener
public class CalendarView extends JComponent {
	private final DayPanel[] week = { new DayPanel("Mandag"),
			new DayPanel("Tirsdag"), new DayPanel("Onsdag"),
			new DayPanel("Torsdag"), new DayPanel("Fredag"),
			new DayPanel("Lørdag"), new DayPanel("Søndag") };
	private UserSession session;
	private int weekNo;

	public CalendarView(final UserSession session) {
		this.session = session;
		
		Calendar now = Calendar.getInstance();
		this.weekNo = now.get(Calendar.WEEK_OF_YEAR);
		
		JPanel topRow = new JPanel();
		JPanel midRow = new JPanel();
		JPanel botRow = new JPanel();
		
		this.setLayout(new BorderLayout());
		this.add(topRow, BorderLayout.NORTH);
		this.add(midRow, BorderLayout.CENTER);
		this.add(botRow, BorderLayout.SOUTH);
		
		/* top row */
		JPanel header = new JPanel();
		
		JLabel headerText = new JLabel("UKE "+ weekNo);
		headerText.setFont(new Font("Serif", Font.PLAIN, 32));
		JButton prevWeekButton = new JButton("<");
		JButton nextWeekButton = new JButton(">");
		JButton showAppointment = new JButton("ShowAppYOLOSWAG");
		
		header.add(prevWeekButton);
		header.add(headerText);
		header.add(nextWeekButton);
		header.add(showAppointment);
		
		JButton logoutButton = new JButton("Logg ut");
		
		topRow.setLayout(new BorderLayout());
		topRow.add(header, BorderLayout.WEST);
		topRow.add(logoutButton, BorderLayout.EAST);
		
		/* middle row */
		midRow.setLayout(new GridLayout(1, 7));
		for (DayPanel dp : week) {
			midRow.add(dp);
		}
		
		/* bottom row */
		JButton addAppButton = new JButton("Legg til avtale");
		JButton showOtherButton = new JButton("Vis andre");
		
		botRow.add(addAppButton);
		botRow.add(showOtherButton);
		
		
		showAppointment.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				session.getAppInstance().goToshowAppointment();
				
			}
			
		});
		
		addAppointments(session.getEmail(), weekNo);
	}



	private void addAppointments(String userEmail, int weekNo) {
		try {
			for (Appointment appointment : DBConnection.selectAppointments(userEmail, weekNo)) {
				addAppointment(appointment);
			}
		} catch (EmailNotInDatabaseException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	private void addAppointment(Appointment appointment) {
		@SuppressWarnings("deprecation")
		int day = appointment.getStartTime().getDay();
		if (day == 0) {
			day = 6;
		} else {
			day -= 1;
		}
		AppointmentPanel panel = new AppointmentPanel(appointment);
		// panel.setSize(new Dimension(this.getWidth(), 100));
		// panel.setMaximumSize(panel.getSize());
		week[day].add(panel);
	}
	
	public void setWeek(int weekNo) {
		this.weekNo = weekNo;
	}

//	public static void main(String[] args) {
//		JFrame frame = new JFrame("Kalender");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setSize(new Dimension(1600, 800));
//		// frame.pack();
//		frame.setVisible(true);
//
//		CalendarView mainCal = new CalendarView();
//		mainCal.initTest();
//		frame.add(mainCal);
//	}
//
//	@SuppressWarnings("deprecation")
//	private void initTest() {
//		Appointment testAppointment = new Appointment("1", "room", new Timestamp(2014, 3, 18, 12, 0, 0, 0));
//		testAppointment.setEventID(42);
//		testAppointment.setDuration(60);
//		Appointment testAppointment2 = new Appointment("2", "room", new Timestamp(2014, 3, 18, 16, 0, 0, 0));
//		testAppointment2.setEventID(43);
//		testAppointment2.setDuration(120);
//
//		addAppointment(testAppointment);
//		addAppointment(testAppointment2);
//	}

}
