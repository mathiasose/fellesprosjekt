package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Appointment;

class AppointmentPanel extends JPanel {
	private Appointment model;

	public AppointmentPanel(Appointment appointment) {
		super();
		setModel(appointment);
		
		setLayout(new BorderLayout());
		add(new JLabel(getModel().getDescription()), BorderLayout.NORTH);
		add(new JLabel(getModel().getLocation()), BorderLayout.CENTER);
		
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		setBackground(Color.pink);
		
		Dimension dim = new Dimension(getWidth(), getHeight());
		System.out.println(dim);
		this.setVisible(true);
	}

	public boolean equals(AppointmentPanel appointmentPanel) {
		return getModel().equals(appointmentPanel.getModel());
	}
	
	@Override
	public int getHeight() {
		double duration = (double)getModel().getDuration();
		if (duration == 0) {
			duration = 120;
		}
		return (int)(800*(duration/(24*60)));
	}

	public Appointment getModel() {
		return model;
	}

	private void setModel(Appointment model) {
		this.model = model;
	}
}