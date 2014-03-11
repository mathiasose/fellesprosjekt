package login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import kalender.Main;


public class LoginView extends JPanel implements KeyListener, ActionListener{
	
	private GridBagConstraints gbc;
	private JTextField loginName, loginPassword;
	private JLabel usernameLable, passwordLable;
	public JButton loginButton;
	public String emailVariabel; //emailen kalenderen tar utgangspunkt i
	public String passwordVariabel; //emailen kalenderen tar utgangspunkt i

	public LoginView(){
		usernameLable = new JLabel("Username: ");
		passwordLable = new JLabel("Password: ");
		
		loginName = new JTextField(20);
		loginPassword = new JPasswordField(20);
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(this);
		
		gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLable, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(loginName, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(passwordLable, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginPassword, gbc);
        
        
        gbc.gridx = 1;
        gbc.gridy = 3;
		add(loginButton, gbc);
	}
	
	public String getLoginName() {
		 return emailVariabel; //emailVariabelen vi bruker videre
	}

	public void setLoginName(JTextField loginName) {
		 emailVariabel = loginName.getText();
//		this.loginName = loginName;
	}

	public String getLoginPassword() {
		return passwordVariabel;
	}

	public void setLoginPassword(JTextField loginPassword) {
		passwordVariabel = loginPassword.getText();
	}


	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == loginButton){
			setLoginName(loginName);
			setLoginPassword(loginPassword);//un�dvendig trur eg - Kris
			
			System.out.println(getLoginName() + " "+ getLoginPassword());
			/*(USER.EMAIL = denne email) AND
			 * USER.PASSWORD = dette passord) s� slipper man inn
			 */
			Main.loginLink();
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
}
