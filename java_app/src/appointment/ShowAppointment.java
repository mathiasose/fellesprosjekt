package appointment;

import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.sql.Timestamp;

import javax.swing.JFrame;
import javax.swing.JPanel;

import db.DBConnection;
import db.EmailNotInDatabaseException;

public class ShowAppointment extends AddAppointmentView {
	
	public ShowAppointment(){
		System.out.println("halla!");
		
		try {
			System.out.println(DBConnection.selectAppointments("kensivalie@gmail.com"));
		} catch (EmailNotInDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int dbID = 5;
		
		DBConnection.selectAppointmentInfo(1);
		
		System.out.println(DBConnection.selectAppointmentInfo(dbID));
		
		String Description = DBConnection.selectAppointmentInfo(dbID).getDescription();
		String Location = DBConnection.selectAppointmentInfo(dbID).getLocation();
		int Duration = DBConnection.selectAppointmentInfo(dbID).getDuration();
		
		
		
		System.out.println(Duration + "dur");
		

		Timestamp date = DBConnection.selectAppointmentInfo(dbID).getStartTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		String Date = sdf.format(date);
		
		System.out.println(Date);
		
		
		
		super.appointmentLocation.setText(Location);
		super.appointmentLocation.setEditable(false);
		
		super.appointmentDescription.setText(Description);
		super.appointmentDescription.setEditable(false);
		
		//super.startTime.setEditable(false);
		super.startHour.setEnabled(false);
		
		super.appointmentDate.setText(Date);		
		super.appointmentDate.setEditable(false);
		
		//super.duration.setSelectedItem(duration);
		
		super.participantEmail.setEditable(false);
		
		
	}
	
	
	
	public static void main(String[] args) {
		ShowAppointment sa = new ShowAppointment();
		JFrame frame = new JFrame ("showapp");
		frame.getContentPane().add(sa);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
}
