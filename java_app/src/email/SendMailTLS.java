package email;

import java.net.PasswordAuthentication;
import java.sql.SQLException;
import java.util.Properties;

import sun.rmi.transport.Transport;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;

import db.DBConnection;

public class SendMailTLS {

	public static void main(String[] args) throws SQLException {

		final String username = "vaginaknuser@gmail.com";
		final String password = "5431offblast";

		System.out.println(DBConnection.selectParticipantEmails(1));

		for (int i = 0; i < DBConnection.selectParticipantEmails(1).size(); i++) {
			System.out.println(DBConnection.selectParticipantEmails(1).get(i));

		}

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		for (int i = 0; i < DBConnection.selectParticipantEmails(1).size(); i++) {
			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(
						"kristofferringstaddahl@gmail.com"));
				message.setRecipients(Message.RecipientType.TO, InternetAddress
						.parse(DBConnection.selectParticipantEmails(1).get(i)));
				message.setSubject("Appointment Invitation");
				message.setText("Dear, "
						+ DBConnection.selectParticipantEmails(1).get(i)
						+ "\n\n You have been invited to an icecream-party. woopwoop");

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		}

	}
}