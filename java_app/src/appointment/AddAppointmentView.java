package appointment;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import db.DBConnection;
import db.EmailNotInDatabaseException;
import email.EmailValidator;
import model.Appointment;
import model.Room;
import app.App;
import appointment.GhostText;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddAppointmentView extends JPanel implements ActionListener, PropertyChangeListener {
	
	public JFormattedTextField appointmentDate;
	public JTextField appointmentLocation;
	public JTextArea appointmentDescription;
	public JTextField participantEmail;
	public JComboBox startTime, duration;
	public JComboBox room;
	public JButton cancelAppointment, saveAppointment;
	public static JButton addParticipant;
	public JList participantList;
	public GhostText ghostText;
	public DefaultListModel listModel;
	private Appointment model = null;
	
	
	
	
	String[] time = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
	String[] dur = {"1","2","3","4"};
	ArrayList<Integer> rooms = DBConnection.selectAllRoomIDs(); 
	Object[] room_ = rooms.toArray();  
	
	public AddAppointmentView(){
		
		GridBagConstraints c; 
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		
		
		appointmentDate = new JFormattedTextField(createFormatter("## '.' ## '.' ####"));
		//ghostText = new GhostText(appointmentDate, "DD.MM.YYYY");
		
		appointmentLocation = new JTextField(30);
		//ghostText = new GhostText(appointmentLocation, "Location");
		
		appointmentDescription = new JTextArea(1, 3);
		//ghostText = new GhostText(appointmentDescription, "Description");
		
		participantEmail = new JTextField(20);
		ghostText = new GhostText(participantEmail, "Participant email");
		
		startTime = new JComboBox(time);
		startTime.setEditable(true);
		startTime.setSelectedItem("Select start time");
	
		
		duration = new JComboBox(dur);
		duration.setEditable(true);
		duration.setSelectedItem("Duration in hours");
		
		room = new JComboBox(room_);
		room.setEditable(true);
		room.setSelectedItem("Rooms available");
		
		listModel = new DefaultListModel();
		participantList = new JList(listModel); 
		
		cancelAppointment = new JButton("Cancel");
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
		
		//cancle appointmentbutton
		c.insets = new Insets(7,7,7,7); 
		c.ipady = 0;      //make this component default
		c.gridy = 4;
		c.gridwidth = 1;
		add(cancelAppointment,c);
		
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

				EmailValidator emailV = new EmailValidator();
				
				String participantEM = participantEmail.getText();
				boolean valid = emailV.validate(participantEM);
				
				if (valid == false || listModel.contains(participantEM) ){
					System.out.println("not a valid email");
				}else {
					
					listModel.addElement(participantEmail.getText());
					
				}
			}
		});
		
		saveAppointment.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(model.toString());
			
				String start_time = null;
				SimpleDateFormat sdfT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				start_time = sdfT.format(model.getStartTime().getTime());
				
				String date = null;
				SimpleDateFormat sdfD = new SimpleDateFormat("dd-MM-yyyy");
				date = sdfD.format(model.getDate());
				
				String duration = null;
				duration = Integer.toString(model.getDuration());
				
				String location = model.getLocation();
				
				String descrip = model.getDescription();
				
				String canceled = "0";
				
				
				System.out.println(listModel.toString() + "LM");
				
				int room_idi = model.getMeetingRoom();				
				String room_id = Integer.toString(room_idi);


				
				
				
				System.out.println(start_time + " " + duration);
				
				int appointmentIDi = DBConnection.insertAppointment(start_time, duration, location, canceled);
				String appointmentID = Integer.toString(appointmentIDi); 
				
				System.out.println(appointmentID + " appointmentid");
								
				DBConnection.insertReservation(appointmentID, room_id);
				
				for (int y =0; y < listModel.getSize(); y++){
					Object participantEmailObj = listModel.getElementAt(y);
					String participantEmail = participantEmailObj.toString();
					
					try {
						int employeeIdi = DBConnection.selectEmployeeId(participantEmail);
						String employeeId = Integer.toString(employeeIdi);
						DBConnection.insertInvitation(employeeId, appointmentID, "0", "0"); 
						
					} catch (EmailNotInDatabaseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
				
				
				//vinduet lukkes og du tas tilbake til kalender
				
				
			}
		});
		
		cancelAppointment.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				App.cancelLink();
				//System.exit(0); 
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
		
		appointmentDescription.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				
				//System.out.println(appointmentDescription.getText());
				model.setDescription(appointmentDescription.getText());
				
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				//hei
			}
			
		});
		
		appointmentLocation.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				model.setLocation(appointmentLocation.getText());
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		appointmentDate.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				SimpleDateFormat ft = new SimpleDateFormat("dd . MM . yyyy");
				
				String input= appointmentDate.getText();
				System.out.println(input);
				java.util.Date t;
				Date hei = null; 
				
				
				try{
					t = ft.parse(input);
					System.out.println(t);
					hei = t; 
				} catch (ParseException e1){
					System.out.println("unparseable" + ft);
				}
				
				
				model.setDate(hei); 
				System.out.println(model.getDate());
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		room.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Object roomid = room.getSelectedItem();
				String room_IDS = roomid.toString();
				int room_ID = Integer.parseInt(room_IDS);
				
				model.setMeetingRoom(room_ID);
				
			}
			
		});
		
		startTime.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {


				Object selTime = startTime.getSelectedItem();
				String selectedTime = selTime.toString();
				
				int timeI = Integer.parseInt(selectedTime);
				
				Calendar c1 = Calendar.getInstance();
				Date date = model.getDate();
			
				System.out.println(date);

			
					if(date != null){
						c1.set(Calendar.YEAR, date.getYear()+1900);
						c1.set(Calendar.MONTH, date.getMonth());
						c1.set(Calendar.DAY_OF_MONTH, date.getDate());
						c1.set(Calendar.HOUR_OF_DAY, timeI);
						c1.set(Calendar.MINUTE, 00);
					}
					c1.set(Calendar.HOUR_OF_DAY, timeI);
					c1.set(Calendar.MINUTE, 00);
					
			
				

				System.out.println(c1.getTime());
				model.setStartTime(c1);
				
				
				
			}
			
		});
		
		duration.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Object durationH = duration.getSelectedItem();
				int durationHours = Integer.parseInt((String) durationH);
				System.out.println(durationHours);
				model.setDuration(durationHours);
			}
			
		});
		
		
	}
	
	public void setModel(Appointment a){
		this.model = a;
		appointmentDescription.setText(a.getDescription());
		appointmentLocation.setText(a.getLocation());
		appointmentDate.setValue(a.getDate());
		System.out.println("halla2");
		
		this.model.addPropertyChangeListener(this);
	}
	
	public Appointment getModel(){
		return model;
	}
	
	public static void main (String[] args){
		AddAppointmentView addAppointment = new AddAppointmentView();
		JFrame frame = new JFrame("hallaa");
		frame.getContentPane().add(addAppointment);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Appointment model = new Appointment("Description", "Location", null);
		
		
		//tanken var � lage rommene her, ogs� legge det til i dropdownmenyen
		// midlertidig l�sning til db er oppe.
		//meeen vi klarer det ikke. 
		
		/*Room rom1 = new Room(112,20,true);
		Room rom2 = new Room(113,10,true);
		Room rom3 = new Room(114,30,true);*/
		
		addAppointment.setModel(model);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//gj�r s�nn at man bare kan skrive tall i Date-feltet
	//med formatter f�r man ikke ghostText, s� m� ta et valg
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
		// TODO Auto-generated method stub
		String changedProperty = evt.getPropertyName();
		
		if (changedProperty == "description"){
			appointmentDescription.setText(model.getDescription());	
			}	

		if (changedProperty == "location"){
			appointmentLocation.setText(model.getLocation());		
			}
		
		if (changedProperty == "date"){
			appointmentDate.setValue(model.getDate());		
			}
		
	}
	


}
