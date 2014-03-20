package authentication;

import java.sql.SQLException;

import app.App;
import db.DBConnection;
import db.EmailNotInDatabaseException;

public class UserSession {

	private String email;
	private boolean authenticated = false;
	private App appInstance;

	public UserSession(App app) {
		this.appInstance = app;
	}

	public String getEmail() {
		return email;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public boolean authenticate(String loginEmail, String loginPassword)
			throws EmailNotInDatabaseException, SQLException {
		if (DBConnection.tryLogin(loginEmail, loginPassword)) {
			this.email = loginEmail;
			this.authenticated = true;
			return true;
		}
		return false;
	}

	public void end() {
		this.email = null;
		this.authenticated = false;
	}

	public App getAppInstance() {
		return appInstance;
	}

	public void appDialog(String msg) {
		appInstance.showMessageDialog(msg);
	}
}
