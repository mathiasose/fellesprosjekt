package login;

//Man kan logge seg inn ved foreksempel putte inn : "m@thiaso.se" og "hunter2"

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import app.App;
import db.DBConnection;
import db.EmailNotInDatabaseException;

public class LoginView extends JPanel implements KeyListener, ActionListener {

	private JTextField loginEmailField, loginPasswordField;
	private JLabel loginEmailLabel, loginPasswordLabel;
	public JButton loginButton;

	public LoginView() {
		loginEmailLabel = new JLabel("Email: ");
		loginPasswordLabel = new JLabel("Password: ");

		loginEmailField = new JTextField(20);
		loginPasswordField = new JPasswordField(20);

		loginButton = new JButton("Login");
		loginButton.addActionListener(this);

		setLayout(new GridBagLayout());

		add(loginEmailLabel, GBC(0, 0));
		add(loginEmailField, GBC(1, 0));
		add(loginPasswordLabel, GBC(0, 2));
		add(loginPasswordField, GBC(1, 2));
		add(loginButton, GBC(1, 3));
	}

	private static GridBagConstraints GBC(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = x;
		gbc.gridy = y;
		return gbc;
	}

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
					App.loginLink();
//					printer ut avtalene som tilh�rer brukeren:
					System.out.println(getLoginEmail() + "'s Appointments: " + DBConnection.selectAppointments(getLoginEmail()));
//					System.out.println(DBConnection.selectAppointments(getLoginEmail()).getString(1));
					
				}
			} catch (SQLException e) {
				// kræsj
				e.printStackTrace();
			} catch (EmailNotInDatabaseException e) {
				System.out.println(e);
				JOptionPane.showMessageDialog(App.frame,
					    "Wrong user/password combination");
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

}
