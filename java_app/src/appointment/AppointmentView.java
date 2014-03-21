package appointment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import model.Appointment;
import app.App;
import authentication.UserSession;
import db.DBConnection;
import db.EmailNotInDatabaseException;
import email.EmailValidator;

public class AppointmentView extends JPanel implements ActionListener,
		PropertyChangeListener {

	public JFormattedTextField appointmentDate;
	public JTextField appointmentLocation;
	public JTextArea appointmentDescription;
	public JTextField participantEmail;
	public JComboBox startHour, startMin, duration;
	public JComboBox room;
	public JButton cancelAppointment, saveAppointment;
	public static JButton addParticipant, deleteParticipant;
	public JList participantList;
	public GhostText ghostText;
	public DefaultListModel listModel;
	private Appointment model = new Appointment();

	static final String[] hours = { "00", "01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17",
			"18", "19", "20", "21", "22", "23" };
	static final String[] minutes = { "00", "01", "02", "03", "04", "05", "06",
			"07", "08", "09", "10", "11", "12", "33", "14", "15", "16", "17",
			"18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28",
			"29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39",
			"40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
			"51", "52", "53", "54", "55", "56", "57", "68", "59" };
	String[] dur = { "30", "60", "90", "120" };
	ArrayList<String> rooms;
	ArrayList<String> roomsnumb = new ArrayList<String>();

	private UserSession session;

	public AppointmentView(final UserSession session) {
		this.session = session;
		System.out.println("dette er rooms: " + rooms);

		try {
			rooms = DBConnection.selectAllRoomIDs();

			ArrayList<String> roomscap = new ArrayList<String>();

			System.out.println(rooms);

			for (int i = 0; i < rooms.size(); i = i + 2) {
				roomsnumb.add(rooms.get(i));
			}

			for (int i = 1; i < rooms.size(); i = i + 2) {
				roomscap.add(rooms.get(i));
			}

		} catch (SQLException e3) {
			session.appDialog(App.DB_ERROR_MSG);
		}

		initView();
		initHandlers(session);

	}

	private void initHandlers(final UserSession session) {
		addParticipant.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				EmailValidator emailV = new EmailValidator();

				String participantEM = participantEmail.getText();
				boolean valid = emailV.validate(participantEM);

				if (valid == false || listModel.contains(participantEM)) {
					System.out.println("not a valid email");
				} else {

					listModel.addElement(participantEmail.getText());

				}
			}
		});

		saveAppointment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Setters to model
				model.setDescription(appointmentDescription.getText());

				model.setLocation(appointmentLocation.getText());

				Object durationH = duration.getSelectedItem();
				int durationHours = Integer.parseInt((String) durationH);
				model.setDuration(durationHours);

				Object roomid = room.getSelectedItem();
				String room_IDS = roomid.toString();
				int room_ID = Integer.parseInt(room_IDS);

				model.setMeetingRoom(room_ID);

				String start_time = null;

				String dateText = appointmentDate.getText();
				String hourText = (String) startHour.getSelectedItem();
				String minText = (String) startMin.getSelectedItem();

				String dateString = dateText + " " + hourText + ":" + minText
						+ ":00";

				String halla = null;
				DateFormat inputdf = new SimpleDateFormat(
						"dd . MM . yyyy HH:mm:ss");
				try {
					Date date = inputdf.parse(dateString);
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					halla = sdf.format(date);
					model.setAppointmentTime(Timestamp.valueOf(halla));
					start_time = halla;
				} catch (ParseException e2) {
					session.appDialog(App.DB_ERROR_MSG);
				}

				// n� settes ikke model lenger

				String duration = null;
				duration = Integer.toString(model.getDuration());

				String location = model.getLocation();

				String descrip = model.getDescription();

				String canceled = "0";

				System.out.println(listModel.toString() + "LM");

				int room_idi = model.getMeetingRoom();
//				String room_id = Integer.toString(room_idi);

				System.out.println(start_time + " " + duration);

				int appointmentIDi = 0;
				try {
					appointmentIDi = DBConnection.insertAppointment(start_time,
							duration, location, descrip, canceled);
				} catch (SQLException e1) {
					session.appDialog(App.AUTH_ERROR_MSG);
				}
//				String appointmentID = Integer.toString(appointmentIDi);

				System.out.println(appointmentIDi + " appointmentid");

				try {
					DBConnection.insertReservation(appointmentIDi, room_idi);
				} catch (SQLException e1) {

					// TODO Auto-generated catch block
					e1.printStackTrace();

					session.appDialog(App.DB_ERROR_MSG);

				}
				try {
					DBConnection.insertInvitation(session.getEmployeeID(), appointmentIDi, true, false, true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (int y = 0; y < listModel.getSize(); y++) {
					Object participantEmailObj = listModel.getElementAt(y);
					String participantEmail = participantEmailObj.toString();

					try {
						int employeeIdi = DBConnection
								.selectEmployeeId(participantEmail);
//						String employeeId = Integer.toString(employeeIdi);
						DBConnection.insertInvitation(employeeIdi,
								appointmentIDi, false, false);

					} catch (EmailNotInDatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						session.appDialog(App.AUTH_ERROR_MSG);
					}

				}

				session.getAppInstance().goToCalendar();
				// vinduet lukkes og du tas tilbake til kalender

			}
		});

		cancelAppointment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				session.getAppInstance().cancelAddAppointmentGoToCalendar();
				// System.exit(0);
			}
		});

		deleteParticipant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = participantList.getSelectedIndex();
				listModel.remove(index);

			}

		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initView() {
		GridBagConstraints c;
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();

		appointmentDate = new JFormattedTextField(
				createFormatter("## '.' ## '.' ####"));
		// ghostText = new GhostText(appointmentDate, "DD.MM.YYYY");

		appointmentLocation = new JTextField(30);
		// ghostText = new GhostText(appointmentLocation, "Location");

		appointmentDescription = new JTextArea(1, 3);
		// ghostText = new GhostText(appointmentDescription, "Description");

		participantEmail = new JTextField(20);
		ghostText = new GhostText(participantEmail, "Participant email");

		startHour = new JComboBox(hours);
		startHour.setEditable(true);
		startHour.setSelectedItem("Hour");

		startMin = new JComboBox(minutes);
		startMin.setEditable(true);
		startMin.setSelectedItem("Minute");

		duration = new JComboBox(dur);
		duration.setEditable(true);
		duration.setSelectedItem("Duration");

		room = new JComboBox(roomsnumb.toArray());
		room.setEditable(true);
		room.setSelectedItem("Rooms available");

		listModel = new DefaultListModel();
		participantList = new JList(listModel);

		cancelAppointment = new JButton("Cancel");
		saveAppointment = new JButton("Save");
		addParticipant = new JButton("Add Participant");
		deleteParticipant = new JButton("Remove Participant");

		// layout

		// date-Jtextfield
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(7, 7, 0, 7);
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		add(appointmentDate, c);

		// starthour combobox
		c.weightx = 0.25;
		c.gridx++;
		add(startHour, c);

		// startmin combobox
		c.weightx = 0.25;
		c.gridx++;
		add(startMin, c);

		// duration combobox
		c.weightx = 0.5;
		c.gridx++;
		add(duration, c);

		// "where" jTextfield
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		add(appointmentLocation, c);

		// room combobox
		c.gridx = 2;
		c.gridwidth = 1;
		add(room, c);

		// appointmentdescription
		c.ipady = 40; // make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.gridx = 0;
		c.gridy++;
		add(appointmentDescription, c);

		// cancle appointmentbutton
		c.insets = new Insets(7, 7, 7, 7);
		c.ipady = 0; // make this component default
		c.gridy = 4;
		c.gridwidth = 1;
		add(cancelAppointment, c);

		// save appointmentbutton
		c.gridx = 2;
		add(saveAppointment, c);

		// jscrollpane containing participants
		c.insets = new Insets(7, 7, 0, 7);
		c.gridx = 3;
		c.gridy = 1;
		c.ipady = 40;
		c.gridheight = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(new JScrollPane(participantList), c);

		// addparticipantemail jtextfield
		c.ipady = 0;
		c.gridheight = 1;
		c.gridy = 3;
		add(participantEmail, c);

		// addparticipant button
		c.gridy = 4;
		add(addParticipant, c);

		// removeparticipant button

		c.gridy++;
		add(deleteParticipant, c);
	}

	public void setModel(Appointment a) {
		this.model = a;
		appointmentDescription.setText(a.getDescription());
		appointmentLocation.setText(a.getLocation());
		appointmentDate.setValue(a.getStartTime());
		System.out.println("halla2");
	}

	public Appointment getModel() {
		return model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	// gj�r s�nn at man bare kan skrive tall i Date-feltet
	// med formatter f�r man ikke ghostText, s� m� ta et valg
	protected MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(s);
		} catch (java.text.ParseException exc) {
			System.err.println("formatter is bad: " + exc.getMessage());
			System.exit(-1);
		}
		return formatter;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String changedProperty = evt.getPropertyName();

		if (changedProperty == "description") {
			appointmentDescription.setText(model.getDescription());
		}

		if (changedProperty == "location") {
			appointmentLocation.setText(model.getLocation());
		}

		if (changedProperty == "date") {
			appointmentDate.setValue(model.getStartTime());
		}

	}

}
