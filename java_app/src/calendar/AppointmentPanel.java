package calendar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import authentication.UserSession;
import model.Appointment;

public class AppointmentPanel extends JPanel {
	private Appointment model;
	public int eventID = 0;
	protected UserSession session;

	public AppointmentPanel(Appointment appointment, final UserSession session) {
		setModel(appointment);
		this.session = session;

		setLayout(new BorderLayout());
		add(new JLabel(getModel().getDescription()), BorderLayout.NORTH);
		add(new JLabel(getModel().getLocation()), BorderLayout.CENTER);
		

		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		setBackground(Color.pink);

		Dimension dim = new Dimension(getWidth(), getHeight());
		System.out.println(dim);
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
			duration = 120;
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