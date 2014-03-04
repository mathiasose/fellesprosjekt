package fellesprosjekt;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddAppointmentView extends JPanel {
	
	public JTextField appointmentDate, appointmentLocation, appointmentDescription, participantEmail;
	public JComboBox startTime, duration, room;
	public JButton deleteAppointment, saveAppointment, addParticipant;
	
	String[] time = {"0000","0100","0200","0300","0400","0500","0600","0700","0800","0900","2200","2300"};
	String[] dur = {"1","2","3","4"};
	String[] rooms = {"112", "113", "114"};
	
	public AddAppointmentView(){
		
		GridBagConstraints c; 
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		appointmentDate = new JTextField(10);
		appointmentLocation = new JTextField(20);
		appointmentDescription = new JTextField(50);
		participantEmail = new JTextField(20);
		
		startTime = new JComboBox(time);
		startTime.setEditable(true);
		startTime.setSelectedItem("Select start time");
		
		duration = new JComboBox(dur);
		duration.setEditable(true);
		duration.setSelectedItem("Duration in hours");
		
		room = new JComboBox(rooms);
		room.setEditable(true);
		room.setSelectedItem("Rooms available");
		
		deleteAppointment = new JButton("delete");
		saveAppointment = new JButton("save");
		addParticipant = new JButton("add");
		
		//layout
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(7,7,0,7); 
		c.weightx =0.5;
		c.gridx=0;
		c.gridy=0;
		add(appointmentDate,c);
		
		c.weightx = 0.5;
		c.gridx++;
		add(startTime,c);
		
		c.weightx = 0.5;
		c.gridx++;
		add(duration,c);
		
		c.gridx=0;
		c.gridy++;
		c.gridwidth = 2;
		add(appointmentLocation,c);
		
		c.gridx=2;	
		c.gridwidth = 1;
		add(room,c);
		
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridx=0;
		c.gridy++;
		add(appointmentDescription,c);
		
		c.insets = new Insets(7,7,7,7); 
		c.ipady = 0;      //make this component default
		c.gridy++;
		c.gridwidth = 1;
		add(deleteAppointment,c);
		
		c.gridx=2;
		add(saveAppointment,c);	
		
		
		
		
	}
	
	public static void main (String[] args){
		AddAppointmentView addAppointment = new AddAppointmentView();
		JFrame frame = new JFrame("hallaa");
		frame.getContentPane().add(addAppointment);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
