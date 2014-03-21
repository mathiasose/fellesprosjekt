package app;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class Frame extends JFrame {

	private JComponent currentView;

	public Frame() {
		super("Coolendar");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(1600, 800));
		this.setVisible(true);
	}

	public void setView(JComponent view) {
		if (this.currentView != null) {
			this.currentView.setVisible(false);
		}
		this.add(view);
		this.currentView = view;
		view.setVisible(true);
		this.validate();
	}

}
