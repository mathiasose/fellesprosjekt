package kalender;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

//la til action listener
public class KalenderView extends JTable implements ActionListener{
	
	private JTable kalenderTable;
	private JButton logoutButton, addAppointmentButton, showOtherButton;
	private GridBagConstraints gbc;
	private JScrollPane pane;
//	private JTextField ukeNummer;
	private JLabel ukeNummerLable, ukeNummer;
	GregorianCalendar gc = new GregorianCalendar();
	
	static String[][] avtaler = {
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
		    {"12:00", "Avtale", " ", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"12:30", "Avtale", "Avtale", "@", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"13:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"13:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"14:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"14:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"15:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", " "},
		    {"15:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"16:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"16:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"17:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"17:30", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"},
		    {"18:00", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale", "Avtale"}
		    
		};
	
	String[] ukedager = {"Tid", "Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lï¿½rdag", "Sï¿½ndag"};

	public KalenderView(){
		
		kalenderTable = new JTable(avtaler, ukedager);
		kalenderTable.setGridColor(Color.BLACK);
		//kalenderTable.setEnabled(false);
		kalenderTable.setSize(800,500);
		
		
//		Her gjï¿½r vi "alt" til knapper, vi vil heller bare gjï¿½re utvalgte avtaler til knapper, altsï¿½ de som er avtaler og ikke "tomme felter"/avtale med tekst slik det var uten button colloumn

//Tester litt:
		boolean tirsdagsAvtale = true;
		boolean klokkaTi = true;
		
		pane = new JScrollPane(kalenderTable);
//		ButtonColumn buttonColumnMandag = new ButtonColumn(kalenderTable, showAppointment, 1);
		
		
//		if (tirsdagsAvtale = true){
//		JButton buttonTirsdagKlokkaTi = new JButton();
		//kalenderTable.add(buttonTirsdagKlokkaTi, 1);
					
		buttonToTheTable(avtaler, 3, 5);

	
//					buttonTirsdagKlokkaTi);
//			
//		}
		
		
//		ButtonColumn buttonColumnTirsdag = new ButtonColumn(kalenderTable, showAppointment, 2);
//		ButtonColumn buttonColumnOnsdag = new ButtonColumn(kalenderTable, showAppointment, 3);
//		ButtonColumn buttonColumnTorsdag = new ButtonColumn(kalenderTable, showAppointment, 4);
//		ButtonColumn buttonColumnFredag = new ButtonColumn(kalenderTable, showAppointment, 5);
//		ButtonColumn buttonColumnLordag = new ButtonColumn(kalenderTable, showAppointment, 6);
//		ButtonColumn buttonColumnSondag = new ButtonColumn(kalenderTable, showAppointment, 7);
		
		//Gir feil, vet ikke hva det er godt fo anyways:
//		ukeNummer = new JTextField(3);
//		ukeNummer.setEditable(false);
//		ukeNummer.setText("10");
		
		ukeNummerLable = new JLabel("Uke:");
		ukeNummer = new JLabel(""+gc.get(Calendar.WEEK_OF_YEAR));
		
		logoutButton = new JButton("Log out");
		addAppointmentButton = new JButton("Add Appointment");
		showOtherButton = new JButton("Show other");
		
		
		addAppointmentButton.addActionListener(this);
		logoutButton.addActionListener(this);
		showOtherButton.addActionListener(this);
		
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
		frame.setSize(1000, 700);
		frame.setVisible(true);
//KRISTOFFER START		
	    
		for(int i = 0; i < avtaler.length; i = i+1) {
				for(int j = 1; j < avtaler[i].length; j = j+1) {
					
//					System.out.println(avtaler[i][j].toString());
		
//					gir feil :/
					String sjekk = avtaler[i][j].toString();
					
//					String tag = "Avtale";
		            JButton knapp = new JButton(sjekk);
		            
		            
					if (sjekk.equals("@")){
						System.out.println("fant alfakroel :D  "+sjekk);
//						
						
						
//						avtaler[i][j] = new JButton(avtaler[i][j]); 
						
						
				
						
//						^-^
//						JButton buttonVariable = (JButton)avtaler[i][j]; 
//						add(buttonVariable);
						
//						javax.swing.JButton[,0,0,0x0,invalid,alignmentX=0.0,alignmentY=0.5,border=javax.swing.plaf.BorderUIResource$CompoundBorderUIResource@3ccccd19,flags=296,maximumSize=,minimumSize=,preferredSize=,defaultIcon=,disabledIcon=,disabledSelectedIcon=,margin=javax.swing.plaf.InsetsUIResource[top=2,left=14,bottom=2,right=14],paintBorder=true,paintFocus=true,pressedIcon=,rolloverEnabled=true,rolloverIcon=,rolloverSelectedIcon=,selectedIcon=,text=3,defaultCapable=true]
//						avtaler[i][j] = nEw JButton(String.valueOf(j));
						
						
//						makeButton(avtaler[i][j]);
					}
				}
				
		}
	}
//	Hvordan kan man gjøre et object eventuelt en streng til en knapp ?????????
	public static void makeButton(String obj){
//		JButton buttonLizm = (JButton) obj;

	}
	
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == addAppointmentButton){
			Main.addApointmentLink();
		}
		if(event.getSource() == logoutButton){
			Main.logoutLink();
		}
		if(event.getSource() == showOtherButton)
			Main.showOtherLink();
	}

//	skjer ikke noe synlig her...
	private void buttonToTheTable(String[][] matrise, int row, int coloumn) {
		System.out.println(matrise[row][coloumn]);
		String obj = matrise[row][coloumn];
		
		JButton appointmentTestButton = new JButton("TestAvtale");		
//		matrise[row][coloumn] = appointmentTestButton;

//		JButton buttonTo = new JButton();
//		add(buttonTo);
	}
	

//KRISTOFFER END
	
	
	
	
	
	
	
//	Action showAppointment = new AbstractAction();
//	{
//	    public void actionPerformed(ActionEvent e)	    {
//	    	JTable kalenderTable = (JTable)e.getSource();
//	        int modelRow = Integer.valueOf( e.getActionCommand() );
//	        System.out.println("EDIT APPOINTMENT, SOON!");
	    	
//Gav feilmelding, tredje neste linje:
//	        JTable kalenderTable = (JTable)e.getSource();
//	        int modelRow = Integer.valueOf( e.getActionCommand() );
//	        ((DefaultTableModel)kalenderTable.getModel()).removeRow(modelRow);
////	    }
//	};
}
