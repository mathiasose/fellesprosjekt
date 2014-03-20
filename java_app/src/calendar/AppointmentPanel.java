package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Timestamp;

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
//		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		String description = model.getDescription();
		if (description == null) {
			description = "???";
		}

		JLabel comp = new JLabel(description);
		comp.setFont(new Font("SansSerif", Font.PLAIN, 20));
		this.add(comp);

		String location = model.getLocation();
		if (location == null) {
			location = "???";
		}
		JLabel comp2 = new JLabel(location);
		comp2.setFont(new Font("SansSerif", Font.PLAIN, 20));
		comp2.setMinimumSize(new Dimension(0, 0));
		this.add(comp2);

		Timestamp startTime = model.getStartTime();
		String timespan = String.format("%02d", startTime.getHours()) + ":"
				+ String.format("%02d", startTime.getMinutes());
		JLabel comp3 = new JLabel(timespan);
		comp3.setFont(new Font("SansSerif", Font.PLAIN, 20));
		comp3.setMinimumSize(new Dimension(0, 0));
		this.add(comp3);

		this.add(new JLabel(model.getDuration() + "min"));

		this.validate();

		this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		this.setBackground(Color.pink);
		this.setVisible(true);
		
		addMouseListener(new MouseAdapter(){
			private Color background; 
			
			public void mousePressed(MouseEvent e){
				background = getBackground();
				setBackground(Color.RED);
				repaint();
				int eventID = getModel().getEventID();
				System.out.println(eventID);
				session.getAppInstance().goToshowAppointment(model);
			}
			
			public void mouseReleased(MouseEvent e){
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
		if (duration == 0) {
			duration = 2 * 120;
		}
		return (int) (800 * (duration / (24 * 60)));
	}

	public Appointment getModel() {
		return model;
	}

	private void setModel(Appointment model) {
		this.model = model;
	}

}