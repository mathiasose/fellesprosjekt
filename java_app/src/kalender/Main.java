package kalender;

import java.awt.Dimension;

import javax.swing.JFrame;

import login.LoginView;

import java.awt.Dimension;

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
		
		login = new LoginView();
		kalender = new KalenderView();
		addAppointmentView = new AddAppointmentView();

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
		System.out.println("PEASE INPUT INFORMATION ABOUT YOUR APPOINTMENT!");
	}	
	
//TRENGER FUNKSJONALITET HER ETTERHVERT
	public static void showOtherLink(){
		System.out.println("THIS METHOD IS YET TO BE IMPLEMENTED! SORRY >_<");
	}	

	
}
