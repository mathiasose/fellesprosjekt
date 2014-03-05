package kalender;

import java.awt.Dimension;

import javax.swing.JFrame;

import login.LoginView;


public class Main{
	
	static LoginView login;
	static KalenderView kalender;
	static JFrame frame;
	
	public static void main(String[] args) {
		frame = new JFrame("AppNavn");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		login = new LoginView();
		kalender = new KalenderView();
		
		frame.add(login);
		frame.setPreferredSize(new Dimension(800, 500));
		frame.pack();
		frame.setVisible(true);
		
	
	
	}
	
	public static boolean checksucsess(){
		if(login.sucess = true){
			login.setVisible(false);
			frame.add(kalender);
		}
		return login.sucess;
	}
	
	
}
