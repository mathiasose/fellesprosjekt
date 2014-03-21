package appointment;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

import javax.swing.JButton;

import model.Appointment;
import app.App;
import authentication.UserSession;
import db.DBConnection;
import db.EmailNotInDatabaseException;

public class EditAppointmentView extends AppointmentView {

	public EditAppointmentView(final UserSession session, Appointment model) 
			throws SQLException {

		super(session);

		final int dbID = model.getEventID();
		int avtalenr = 0;
		String Description = null;
		String Location = null;
		int Duration = 0;
		Timestamp timeS = null;
		final String userEmail = session.getEmail();
		int createdbyID = -1;

		JButton acceptInvitation, declineInvitation, saveChanges, deleteAppointment;

		Timestamp weekNol = model.getStartTime();
		Calendar tempCal = Calendar.getInstance();
		tempCal.setTime(weekNol);
		int weekNo = tempCal.get(Calendar.WEEK_OF_YEAR);

		System.out.println("weekno: " + weekNo);

		ArrayList<Appointment> selectAppointments;
		Description = model.getDescription();
		Location = model.getLocation();
		Duration = model.getDuration();
		timeS = model.getStartTime();
		HashSet<String> participants = model.getParticipants();
		System.out.println("participants: " + participants);
		createdbyID = model.getCreatedByID();

		int room = model.getMeetingRoom();

		super.appointmentLocation.setText(Location);

		super.appointmentDescription.setText(Description);

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

		super.room.setEnabled(false);
		
		if (participants != null) {
			int i = 0;
			for (String participant : participants) {
				super.listModel.add(i++, participant);
			}
		}

		super.duration.setSelectedItem(duration);

		super.participantEmail.setVisible(false);
		super.saveAppointment.setVisible(false);
		super.addParticipant.setVisible(false);
		super.deleteParticipant.setVisible(false);

		acceptInvitation = new JButton("Accept Invitaion");
		declineInvitation = new JButton("Decline Invitation");
		saveChanges = new JButton("Save");
		deleteAppointment = new JButton("Delete appointment");

		GridBagConstraints c = new GridBagConstraints();

		if (session.getEmployeeID() != createdbyID) {
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(7, 7, 0, 7);
			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = 4;
			add(acceptInvitation, c);

			c.gridx++;
			add(declineInvitation, c);

		} else{
			
			c.fill = GridBagConstraints.HORIZONTAL;
			c.insets = new Insets(22, 7, 0, 7);
			c.weightx = 0.5;
			c.gridx = 2;
			c.gridy = 4;
			add(saveChanges, c);
			
			c.gridy++;
			add(deleteAppointment,c);
			
			super.appointmentLocation.setEditable(true);
			super.appointmentDescription.setEditable(true);
			super.startHour.setEnabled(true);
			super.startMin.setEnabled(true);
			super.appointmentDate.setEditable(true);
			super.duration.setEnabled(true);
			super.room.setEnabled(true);
			
			
			super.participantEmail.setVisible(true);
			super.addParticipant.setVisible(true);
			super.deleteParticipant.setVisible(true);
			
			
			
			
			//final String uDuration = super.duration.getSelectedItem().toString();
						
			saveChanges.addActionListener(new ActionListener(){
				
				

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						final Timestamp uAppTime = getAppTime();
						final String uLocation = getuLocation(); 
						//final String uDuration = getuDuration();
						final String uDescription = getuDescription();
						DBConnection.updateAppointment(dbID, uAppTime, "60", uLocation, uDescription, "0");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}

				
			});
			
			deleteAppointment.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						DBConnection.deleteAppointment(dbID);
						session.getAppInstance().goToCalendar();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			});

		}

		acceptInvitation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					DBConnection.updateInvitationStatus(dbID,
							session.getEmployeeID(), true);
					System.out.println("true");
					session.getAppInstance().goToCalendar();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});

		declineInvitation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					DBConnection.updateInvitationStatus(dbID,
							session.getEmployeeID(), false);
					System.out.println("false");
					session.getAppInstance().goToCalendar();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}

		});

	}
	
	private Timestamp getAppTime() {
		return getUpdateTime(session, model);	
	}

	private String getuLocation() {
		return super.appointmentLocation.getText();
	}
	private String getuDescription() {
		return super.appointmentDescription.getText();

	}
	private String getuDuration() {
		return super.duration.getSelectedItem().toString();
	}
	

	private Timestamp getUpdateTime(final UserSession session, Appointment model) {
		String dateText = super.appointmentDate.getText();
		String hourText = (String) super.startHour.getSelectedItem();
		String minText = (String) super.startMin.getSelectedItem();
		final String dateString = dateText + " " + hourText + ":" + minText
				+ ":00";
		String halla = null;
		DateFormat inputdf = new SimpleDateFormat("dd . MM . yyyy HH:mm:ss");
		try {
			Date date = inputdf.parse(dateString);
			SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			halla = sdf3.format(date);
			model.setAppointmentTime(Timestamp.valueOf(halla));
		} catch (ParseException e2) {
			session.appDialog(App.DB_ERROR_MSG);
		}

		final Timestamp uAppTime = model.getStartTime();
		System.out.println("timestampnew: " + uAppTime);
		return uAppTime;
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
