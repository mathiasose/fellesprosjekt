package fellesprosjekt;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import fellesprosjekt.GhostText;

import java.sql.*;

public class AddAppointmentView extends JPanel implements ActionListener {
	
	public JTextField appointmentDate;
	public JTextField appointmentLocation;
	public JTextField appointmentDescription;
	public JTextField participantEmail;
	public JComboBox startTime, duration, room;
	public JButton deleteAppointment, saveAppointment;
	public static JButton addParticipant;
	public JList participantList;
	public GhostText ghostText;
	public DefaultListModel listModel;
	
	
	
	String[] time = {"0000","0100","0200","0300","0400","0500","0600","0700","0800","0900","2200","2300"};
	String[] dur = {"1","2","3","4"};
	String[] rooms = {"112", "113", "114"};
	String[] participant = {"tore","ken","jenny","mathias",};
	
	public AddAppointmentView(){
		
		GridBagConstraints c; 
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		
		appointmentDate = new JTextField();
		ghostText = new GhostText(appointmentDate, "DD.MM.YYYY");
		
		appointmentLocation = new JTextField(20);
		ghostText = new GhostText(appointmentLocation, "Location");
		
		appointmentDescription = new JTextField(50);
		ghostText = new GhostText(appointmentDescription, "Description");
		
		participantEmail = new JTextField(20);
		ghostText = new GhostText(participantEmail, "Participant email");
		
		startTime = new JComboBox(time);
		startTime.setEditable(true);
		startTime.setSelectedItem("Select start time");
	
		
		duration = new JComboBox(dur);
		duration.setEditable(true);
		duration.setSelectedItem("Duration in hours");
		
		room = new JComboBox(rooms);
		room.setEditable(true);
		room.setSelectedItem("Rooms available");
		
		listModel = new DefaultListModel();
		participantList = new JList(listModel); 
		
		deleteAppointment = new JButton("Delete");
		saveAppointment = new JButton("Save");
		addParticipant = new JButton("Add Participant");
		
		
		
		//layout
		//date-Jtextfield
		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(7,7,0,7); 
		c.weightx =0.5;
		c.gridx=0;
		c.gridy=0;
		add(appointmentDate,c);
		
		//starttime combobox
		c.weightx = 0.5;
		c.gridx++;
		add(startTime,c);
		
		//duration combobox
		c.weightx = 0.5;
		c.gridx++;
		add(duration,c);
		
		//"where" jTextfield
		c.gridx=0;
		c.gridy++;
		c.gridwidth = 2;
		add(appointmentLocation,c);
		
		//room combobox
		c.gridx=2;	
		c.gridwidth = 1;
		add(room,c);
		
		//appointmentdescription
		c.ipady = 40;      //make this component tall
		c.weightx = 0.0;
		c.gridwidth = 3;
		c.gridheight = 2;
		c.gridx=0;
		c.gridy++;
		add(appointmentDescription,c);
		
		//delete appointmentbutton
		c.insets = new Insets(7,7,7,7); 
		c.ipady = 0;      //make this component default
		c.gridy = 4;
		c.gridwidth = 1;
		add(deleteAppointment,c);
		
		//save appointmentbutton
		c.gridx=2;
		add(saveAppointment,c);	

			
		//jscrollpane containing participants
		c.insets = new Insets(7,7,0,7);
		c.gridx=3;
		c.gridy=0;
		c.ipady = 40;
		c.gridheight = 3;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(new JScrollPane(participantList), c); 
		
		//addparticipantemail jtextfield
		c.ipady = 0;
		c.gridheight=1;
		c.gridy=3;
		add(participantEmail,c);
		
		//addparticipant button
		c.gridy=4;
		add(addParticipant,c);
		
		//actionlisteners
		
		addParticipant.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				listModel.addElement(participantEmail.getText());
			}
		});
		
		participantEmail.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				System.out.println( participantEmail.getText());
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
	}
	
	public static void main (String[] args){
		AddAppointmentView addAppointment = new AddAppointmentView();
		JFrame frame = new JFrame("hallaa");
		frame.getContentPane().add(addAppointment);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	


}
