package authentication;

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
	private JButton loginButton;
	private UserSession session;

	public LoginView(UserSession session) {
		this.session = session;

		loginEmailLabel = new JLabel("Email: ");
		loginPasswordLabel = new JLabel("Password: ");

		loginEmailField = new JTextField(20);
		loginPasswordField = new JPasswordField(20);

		loginButton = new JButton("Login");
		loginButton.addActionListener(this);

		setLayout(new GridBagLayout());
		this.setVisible(true);

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
			try {
				if (session.authenticate(getLoginEmail(), getLoginPassword())) {
					session.getAppInstance().goToCalendar();
				} else {
					session.getAppInstance().showMessageDialog(
							"Wrong user/password combination");
				}
			} catch (EmailNotInDatabaseException e) {
				session.getAppInstance().showMessageDialog(
						"Wrong user/password combination");
			} catch (SQLException e) {
				session.getAppInstance()
						.showMessageDialog(
								"Could not talk to database. Are you sure you're connected to the internet?");
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
