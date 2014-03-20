package calendar;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

class DayPanel extends JPanel {
	private JLabel title;

	public DayPanel(String dayName) {
		super();
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		add(new JLabel(dayName));
	}

	public Component add(AppointmentPanel appointmentPanel) {
		System.out.println(appointmentPanel.getModel());
		for (Component c : this.getComponents()) {
			if (c instanceof AppointmentPanel) {
				AppointmentPanel existingAppointmentPanel = (AppointmentPanel) c;
				if (existingAppointmentPanel.equals(appointmentPanel)) {
					return null;
				}
			}
		}
		return super.add(appointmentPanel);
	}
}