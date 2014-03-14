package login;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import db.DBConnection;
import db.EmailNotInDatabaseException;
import kalender.Main;

public class LoginView extends JPanel implements KeyListener, ActionListener {

	private GridBagConstraints gbc;
	private JTextField loginEmailField, loginPasswordField;
	private JLabel loginEmailLabel, loginPasswordLabel;
	public JButton loginButton;

	public String getLoginEmail() {
		return loginEmailField.getText();
	}

	public String getLoginPassword() {
		return loginPasswordField.getText();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == loginButton) {
			System.out.println(getLoginEmail() + " " + getLoginPassword());

			try {
				if (DBConnection.tryLogin(getLoginEmail(), getLoginPassword())) {
					Main.loginLink();
				} else {
					System.out
					.println("her bør det komme en melding om at login var feil");
				}
			} catch (SQLException e) {
				// kræsj
				e.printStackTrace();
			} catch (EmailNotInDatabaseException e) {
				System.out.println(e);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public LoginView() {
		loginEmailLabel = new JLabel("Email: ");
		loginPasswordLabel = new JLabel("Password: ");

		loginEmailField = new JTextField(20);
		loginPasswordField = new JPasswordField(20);

		loginButton = new JButton("Login");
		loginButton.addActionListener(this);

		gbc = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(loginEmailLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		add(loginEmailField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		add(loginPasswordLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		add(loginPasswordField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		add(loginButton, gbc);
	}

}
