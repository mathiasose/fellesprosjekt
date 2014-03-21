package calendar;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Appointment;
import app.App;
import authentication.UserSession;
import db.DBConnection;

public class AppointmentPanel extends JPanel {
	private Appointment model;
	public int eventID = 0;
	protected UserSession session;

	public AppointmentPanel(Appointment appointment, final UserSession session) {
		this.setModel(appointment);
		this.session = session;

		JLabel descriptionLabel = new JLabel(
				model.getDescription() == null ? "???" : model.getDescription());
		// comp.setFont(new Font("SansSerif", Font.PLAIN, 20));

		JLabel locationLabel = new JLabel(model.getLocation() == null ? "???"
				: model.getLocation());
		// comp2.setFont(new Font("SansSerif", Font.PLAIN, 20));

		String startTime = "Start: "
				+ new SimpleDateFormat("hh:mm").format(model.getStartTime());
		JLabel startTimeLabel = new JLabel(startTime);
		// comp3.setFont(new Font("SansSerif", Font.PLAIN, 20));

		Timestamp endTimeStamp = new Timestamp(0);
		endTimeStamp.setTime(model.getStartTime().getTime()
				+ model.getDuration() * 60 * 1000);
		String endTime = "End: "
				+ new SimpleDateFormat("hh:mm").format(endTimeStamp);
		JLabel endTimeLabel = new JLabel(endTime);

		this.add(descriptionLabel);
		this.add(locationLabel);
		this.add(startTimeLabel);
		this.add(endTimeLabel);
		this.validate();

		Color statusColor = getStatusColor(session);
		this.setBorder(BorderFactory.createLineBorder(statusColor));
		this.setBackground(statusColor);
		this.setVisible(true);

		addMouseListener(new MouseAdapter() {
			private Color background;

			public void mousePressed(MouseEvent e) {
				background = getBackground();
				setBackground(Color.RED);
				repaint();
				session.getAppInstance().goToShowAppointment(model);
			}

			public void mouseReleased(MouseEvent e) {
				setBackground(background);
			}

		});
	}

	private Color getStatusColor(final UserSession session) {
		try {
			ArrayList<Boolean> status = DBConnection
					.selectAttendingStatus(model.getEventID());
			for (Boolean b : status) {
				if (Boolean.FALSE.equals(b)) {
					return Color.red;
				}
			}
			for (Boolean b : status) {
				if (b == null) {
					return Color.yellow;
				}
			}
			return Color.green;
		} catch (SQLException e1) {
			session.appDialog(App.DB_ERROR_MSG);
		}
		return Color.lightGray;
	}

	public boolean equals(AppointmentPanel appointmentPanel) {
		return getModel().equals(appointmentPanel.getModel());
	}

	@Override
	public int getHeight() {
		double duration = (double) getModel().getDuration();
		duration = 240d; // until alignment gets figured out
		return (int) (800 * (duration / (24 * 60)));
	}

	public Appointment getModel() {
		return model;
	}

	private void setModel(Appointment model) {
		this.model = model;
	}

}