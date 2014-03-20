package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import authentication.UserSession;
import model.Appointment;

public class AppointmentPanel extends JPanel {
	private Appointment model;
	public int eventID = 0;
	protected UserSession session;

	public AppointmentPanel(Appointment appointment, final UserSession session) {
		this.setModel(appointment);
		this.session = session;

		// this.setLayout(new BorderLayout());
		// this.setLayout(new GridLayout(0, 1));
		// this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JLabel descriptionLabel = new JLabel(
				model.getDescription() == null ? "???" : model.getDescription());
		// comp.setFont(new Font("SansSerif", Font.PLAIN, 20));

		JLabel locationLabel = new JLabel(model.getLocation() == null ? "???"
				: model.getLocation());
		// comp2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		// comp2.setMinimumSize(new Dimension(0, 0));

		String startTime = "Start: "
				+ new SimpleDateFormat("hh:mm").format(model.getStartTime());
		JLabel startTimeLabel = new JLabel(startTime);
		// comp3.setFont(new Font("SansSerif", Font.PLAIN, 20));
		// comp3.setMinimumSize(new Dimension(0, 0));

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

		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.setBackground(Color.pink);
		this.setVisible(true);

		addMouseListener(new MouseAdapter() {
			private Color background;

			public void mousePressed(MouseEvent e) {
				background = getBackground();
				setBackground(Color.RED);
				repaint();
				int eventID = getModel().getEventID();
				System.out.println(eventID);
				session.getAppInstance().goToshowAppointment(model);
			}

			public void mouseReleased(MouseEvent e) {
				setBackground(background);
			}

		});
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