package appointment;

import java.awt.event.ActionListener;

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
		
		DBConnection.selectAppointmentInfo(1);
		
		System.out.println(DBConnection.selectAppointmentInfo(3));
		
		
		super.appointmentLocation.setText("Location");
		super.appointmentLocation.setEditable(false);
		
		super.appointmentDescription.setText("Description");
		super.appointmentDescription.setEditable(false);
		
		//super.startTime.setEditable(false);
		super.startTime.setEnabled(false);
		
		super.appointmentDate.setText("18.03.2014");
		super.appointmentDate.setEditable(false);
		
		
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
