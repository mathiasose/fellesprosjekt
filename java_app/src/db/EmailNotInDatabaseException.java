package db;

public class EmailNotInDatabaseException extends Exception {
	public EmailNotInDatabaseException() {
		super("Could not find the email in the database.");
	}
}
