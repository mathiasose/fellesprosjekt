package kalender;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class KalenderView extends JTable{
	
	private JTable kalenderTable;
	private JButton logoutButton, addAppointmentButton, showOtherButton;
	private GridBagConstraints gbc;
	private JScrollPane pane;
	private JTextField ukeNummer;
	private JLabel ukeNummerLable;
	
	Object[][] avtaler = {
		    {"06:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"}, 
		    {"06:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"07:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"07:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"08:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"08:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"09:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"09:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"10:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"10:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"11:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"11:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"12:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"12:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"13:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"13:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"14:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"14:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"15:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"15:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"16:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"16:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"17:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"17:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"18:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"}
		    
		};
	
	String[] ukedager = {"Tid", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "L¿rdag", "S¿ndag"};

	
	public KalenderView(){
		kalenderTable = new JTable(avtaler, ukedager);
		kalenderTable.setGridColor(Color.BLACK);
		kalenderTable.setEnabled(false);
		
		pane = new JScrollPane(kalenderTable);
		
		ukeNummer = new JTextField(3);
		ukeNummer.setEditable(false);
		ukeNummer.setText("10");
		
		ukeNummerLable = new JLabel("Uke:");
		
		logoutButton = new JButton("Log out");
		addAppointmentButton = new JButton("Add Appointment");
		showOtherButton = new JButton("Show other");
		
		gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.WEST;
//		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(ukeNummerLable, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(ukeNummer, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		add(logoutButton, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth=3;
		add(pane, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(showOtherButton, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth=1;
		add(addAppointmentButton, gbc);
		
		
		
		
	}
	
	
	public static void main(String[] args) {
		KalenderView kalender = new KalenderView();
		JFrame frame = new JFrame("Kalender");
		//frame.getContentPane().add(new JScrollPane(kalenderTable),BorderLayout.CENTER);
		frame.add(kalender);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setSize(800, 500);
		frame.setVisible(true);
	}
	
	

}
