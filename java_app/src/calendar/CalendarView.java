package calendar;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.App;
import authentication.UserSession;
import db.DBConnection;
import db.EmailNotInDatabaseException;
import model.Appointment;

public class CalendarView extends JComponent {
	private final WeekDayPanel[] week = { new WeekDayPanel("Mandag"),
			new WeekDayPanel("Tirsdag"), new WeekDayPanel("Onsdag"),
			new WeekDayPanel("Torsdag"), new WeekDayPanel("Fredag"),
			new WeekDayPanel("Lørdag"), new WeekDayPanel("Søndag") };
	private UserSession session;
	private int weekNo;
	private HashSet<String> showUsers = new HashSet<String>();
	private JLabel headerText;
	private JPanel header, topRow, midRow, botRow;
	private JButton prevWeekButton, nextWeekButton, showAppointment,
			logoutButton, addAppointmentButton, showOtherButton;

	public CalendarView(final UserSession session) {
		this.session = session;
		this.weekNo = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);

		initView();
		initListeners();

		showUsers.add(session.getEmail());
		populateView();
	}

	private void initListeners() {
		showAppointment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}

		});

		addAppointmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				session.getAppInstance().goToAddApointment();
			}
		});

		prevWeekButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				weekNo--;
				if (weekNo <= 0) {
					weekNo = 52;
				}
				populateView();
				updateHeader();
			}
		});

		nextWeekButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				weekNo++;
				if (weekNo > 52) {
					weekNo = 1;
				}
				populateView();
				updateHeader();
			}
		});

		showOtherButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				showUsers.add("test@epost.com");
				System.out.println(showUsers);
				populateView();
			}
		});

	}

	private void initView() {
		topRow = new JPanel();
		midRow = new JPanel();
		botRow = new JPanel();

		this.setLayout(new BorderLayout());
		this.add(topRow, BorderLayout.NORTH);
		this.add(midRow, BorderLayout.CENTER);
		this.add(botRow, BorderLayout.SOUTH);

		/* top */
		header = new JPanel();

		prevWeekButton = new JButton("<");
		nextWeekButton = new JButton(">");
		showAppointment = new JButton("ShowAppYOLOSWAG");

		logoutButton = new JButton("Logg ut");

		updateHeader();

		topRow.setLayout(new BorderLayout());
		topRow.add(header, BorderLayout.WEST);
		topRow.add(logoutButton, BorderLayout.EAST);

		/* middle */
		midRow.setLayout(new GridLayout(1, 7));
		for (WeekDayPanel dp : week) {
			midRow.add(dp);
		}

		/* bottom */
		addAppointmentButton = new JButton("Legg til avtale");
		showOtherButton = new JButton("Vis andre");

		botRow.add(addAppointmentButton);
		botRow.add(showOtherButton);
	}

	private void updateHeader() {
		headerText = new JLabel("UKE " + weekNo);
		headerText.setFont(new Font("Serif", Font.PLAIN, 32));

		header.removeAll();
		session.validate();

		header.add(prevWeekButton);
		header.add(headerText);
		header.add(nextWeekButton);
		header.add(showAppointment);

		session.validate();
	}

	private void populateView() {
		clearAppointments();
		for (String email : showUsers) {
			addAppointments(email, weekNo);
		}
		session.validate();
	}

	private void addAppointments(String userEmail, int weekNo) {
		try {
			for (Appointment appointment : DBConnection.selectAppointments(
					userEmail, weekNo)) {
				addAppointment(appointment);
			}
		} catch (EmailNotInDatabaseException e) {
			session.appDialog("Fant ikke den brukeren");
		} catch (SQLException e) {
			if (App.DEBUG) {
				e.printStackTrace();
			}
			session.appDialog(App.DB_ERROR_MSG);
		}
	}

	private void clearAppointments() {
		for (WeekDayPanel day : week) {
			day.removeAllAppointments();
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
		AppointmentPanel panel = new AppointmentPanel(appointment, session);
		// panel.setSize(new Dimension(this.getWidth(), 100));
		// panel.setMaximumSize(panel.getSize());
		week[day].add(panel);
	}

	public void setWeek(int weekNo) {
		this.weekNo = weekNo;
	}

}
