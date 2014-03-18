package app;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

	@Override
	public Component add(Component comp) {
		for (Component c : this.getComponents()) {
			if (c instanceof AppointmentPanel
					&& comp instanceof AppointmentPanel) {
				AppointmentPanel ap1 = (AppointmentPanel) c;
				AppointmentPanel ap2 = (AppointmentPanel) comp;
				if (ap1.equals(ap2)) {
					return null;
				}
			}
		}

		return super.add(comp);

	}

}