package calendar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

class WeekDayPanel extends JPanel {
	private JLabel title;
	private JPanel appointments;

	public WeekDayPanel(String dayName) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(Color.BLUE));
		add(new JLabel(dayName));
		appointments = new JPanel();
		appointments.setLayout(new BoxLayout(appointments, BoxLayout.Y_AXIS));
		appointments.setBackground(Color.WHITE);
		add(appointments);
	}

	public Component add(AppointmentPanel appointmentPanel) {
		for (Component c : this.getComponents()) {
			if (c instanceof AppointmentPanel) {
				AppointmentPanel existingAppointmentPanel = (AppointmentPanel) c;
				if (existingAppointmentPanel.equals(appointmentPanel)) {
					return null;
				}
			}
		}
		return appointments.add(appointmentPanel);
	}

	public void removeAllAppointments() {
		appointments.removeAll();
	}
}