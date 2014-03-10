package kalender;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import addAppointment.AddAppointmentView;
import login.LoginView;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import addAppointment.AddAppointmentView;
import login.LoginView;


public class Main{
	
	static LoginView login;
	static KalenderView kalender;
	static AddAppointmentView addAppointmentView; //kris
	static JFrame frame;
	
	public static void main(String[] args) {
		frame = new JFrame("AppNavn");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		kalender = new KalenderView();
		addAppointmentView = new AddAppointmentView();
		login = new LoginView();
			
		frame.add(login);

		frame.setPreferredSize(new Dimension(800, 500));
		frame.pack();
		frame.setVisible(true);	
	}
	
	public static void loginLink(){
		login.setVisible(false);
		frame.add(kalender);
		kalender.setVisible(true);
		System.out.println("WELCOME!");
	}
	
	//log out fra kalender view
	public static void logoutLink(){
		kalender.setVisible(false);
		login.setVisible(true);
		System.out.println("GOOD BYE!");
	}
	
//Tar deg til AddAppointment Viewet
	public static void addApointmentLink(){
		kalender.setVisible(false);
		frame.add(addAppointmentView);
		addAppointmentView.setVisible(true);
		System.out.println("PLEASE INPUT INFORMATION ABOUT YOUR APPOINTMENT!");
	}	
	
//TRENGER FUNKSJONALITET HER ETTERHVERT
	public static void showOtherLink(){
//		JOptionPane.showMessageDialog(frame,
//		    "We will implement this as soon as we can! SORRY >_<",
//		    "Comming this Spring",
//		    JOptionPane.PLAIN_MESSAGE);
		Object[] showSomeonesAppointments = {"Just yours", "exampleUser1", "Everybody"};
		String showOtherVariabel = (String)JOptionPane.showInputDialog(
		                    frame,
		                    "Select your acquaintance to show their appointments",
		                    "Show others calenders",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null, showSomeonesAppointments,
		                    "Vet ikke hvorfor eller hva denne strengen er godt for :/");

		System.out.println(showOtherVariabel + "s appointments are showing!");
		//HER M� VI VISE VALGTE DELTAGERES APPOINTMENTS!
	}	

//Cancle tilbake til KalenderView
	public static void cancleLink(){
		addAppointmentView.setVisible(false);
		frame.add(kalender);
		kalender.setVisible(true);
		System.out.println("APPOINTMENT MAKING WAS CANCELED");
	}	
	
}
