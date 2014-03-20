package appointment;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.swing.JFrame; 
import javax.swing.JPanel;

import model.Appointment;
import authentication.UserSession;
import db.DBConnection;
import db.EmailNotInDatabaseException;

public class AppointmentView extends NewAppointmentView {

	public AppointmentView(UserSession session, Appointment model) throws SQLException {

		super(session);
		
		
		int dbID = model.getEventID();
		int avtalenr = 0;
		String Description = null;
		String Location = null;
		int Duration = 0;
		Timestamp timeS = null;
		String userEmail = "m@thiaso.se";
		System.out.println("førweekno");
		Timestamp weekNol = model.getStartTime();
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(weekNol);
		int weekNo = tempCal.get(Calendar.WEEK_OF_YEAR); 

		System.out.println("weekno: " + weekNo);
		

		try {
			System.out.println(DBConnection.selectAppointments(userEmail, weekNo)
					+ "halla");
		} catch (EmailNotInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			for (int i = 0; i < DBConnection.selectAppointments(userEmail, weekNo)
					.size(); i++) {
				if (DBConnection.selectAppointments(userEmail, weekNo).get(i)
						.getEventID() == dbID) {
					avtalenr = i;
				}
			}

			Description = DBConnection.selectAppointments(userEmail, weekNo)
					.get(avtalenr).getDescription();
			Location = DBConnection.selectAppointments(userEmail, weekNo).get(avtalenr)
					.getLocation();
			Duration = DBConnection.selectAppointments(userEmail, weekNo).get(avtalenr)
					.getDuration();
			timeS = DBConnection.selectAppointments(userEmail, weekNo).get(avtalenr)
					.getStartTime();
			int room = DBConnection.selectAppointments(userEmail, weekNo).get(avtalenr)
					.getMeetingRoom();

			super.appointmentLocation.setText(Location);

			super.appointmentDescription.setText(Description);

		} catch (EmailNotInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(Duration + "dur");

		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		String Date = sdf.format(timeS);

		SimpleDateFormat sdf1 = new SimpleDateFormat("HH");
		String startH = sdf1.format(timeS);

		SimpleDateFormat sdf2 = new SimpleDateFormat("mm");
		String startM = sdf2.format(timeS);

		System.out.println(startH);

		super.appointmentLocation.setEditable(false);

		super.appointmentDescription.setEditable(false);

		// super.startTime.setEditable(false);
		super.startHour.setEnabled(false);
		super.startHour.setSelectedItem(startH);

		super.startMin.setEnabled(false);
		super.startMin.setSelectedItem(startM);

		super.appointmentDate.setText(Date);
		super.appointmentDate.setEditable(false);

		super.duration.setEnabled(false);
		super.duration.setSelectedItem(Duration);

		// super.duration.setSelectedItem(duration);

		super.participantEmail.setVisible(false);

		super.saveAppointment.setText("Accept Invitation");
		super.saveAppointment.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("accept");

			}

		});

		super.addParticipant.setText("Decline Invitation");
		super.addParticipant.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("decline");

			}

		});

	}

	// public static void main(String[] args) {
	// ShowAppointment sa = new ShowAppointment();
	// JFrame frame = new JFrame ("showapp");
	// frame.getContentPane().add(sa);
	// frame.pack();
	// frame.setVisible(true);
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//
	// }

}
