package login;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginView extends JPanel{
	
	private GridBagConstraints gbc;
	private JTextField loginName, loginPassword;
	private JLabel usernameLable, passwordLable;
	private JButton loginButton;
	
	public LoginView(){
		
		usernameLable = new JLabel("Username: ");
		passwordLable = new JLabel("Password: ");
		
		loginName = new JTextField(20);
		loginPassword = new JPasswordField(20);
		
		loginButton = new JButton("Login");
		
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
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		LoginView panel = new LoginView();
		frame.add(panel);
		frame.setPreferredSize(new Dimension(800, 500));
		frame.pack();
		frame.setVisible(true);
	}

	
	
}
