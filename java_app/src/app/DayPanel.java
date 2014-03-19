package app;

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
		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		title = new JLabel(dayName);
		add(title);
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