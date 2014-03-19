package login;

import java.sql.SQLException;

import javax.swing.JOptionPane;

import app.App;
import db.DBConnection;
import db.EmailNotInDatabaseException;

public class UserSession {
	private String user;
	private boolean authenticated = false;
	private App appInstance;

	public UserSession(App app) {
		this.appInstance = app;
	}

	public String getUser() {
		return user;
	}
	
	public boolean isAuthenticated() {
		return authenticated;
	}

	public boolean authenticate(String loginEmail, String loginPassword) throws EmailNotInDatabaseException {
		try {
			if (DBConnection.tryLogin(loginEmail, loginPassword)) {
				this.user = loginEmail;
				this.authenticated = true;
				return true;
			}
		} catch (SQLException e) {
			// kræsj
			e.printStackTrace();
		}
		return false;
	}

	public void end() {
		this.user = null;
		this.authenticated = false;
	}
	
	public App getAppInstance() {
		return appInstance;
	}
}
