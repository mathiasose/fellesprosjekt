package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

public class Appointment {

	private int eventID;
	// private User createdBy;
	private int createdByID;

	private String description, location;
	private int duration;
	private HashSet<String> participants;
	private int meetingRoom;

	private Timestamp startTime;

	private PropertyChangeSupport pcs;

	public Appointment(String des, String loc, Timestamp dat) {

		description = des;
		location = loc;
		startTime = dat;

		pcs = new PropertyChangeSupport(this);
	}

	public Appointment() {

	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	// public User getCreatedBy() {
	// return createdBy;
	// }
	//
	// public void setCreatedBy(User createdBy) {
	// this.createdBy = createdBy;
	// }

	public HashSet<String> getParticipants() {
		System.out.println(participants + "appointment,model");
		return participants;
	}

	public void setParticipants(HashSet<String> participants2) {
		this.participants = participants2;
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public String toString() {
		return description + " " + location + " " + startTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;

		/*
		 * String oldValue = this.description;
		 * pcs.firePropertyChange("description", oldValue, description);
		 */
	}

	public String getLocation() {
		if (meetingRoom != 0) {
			return "Room " + meetingRoom;
		}
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getMeetingRoom() {
		return meetingRoom;
	}

	public void setMeetingRoom(int meetingRoom) {
		this.meetingRoom = meetingRoom;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setAppointmentTime(Timestamp appointmentTime) {
		this.startTime = appointmentTime;

	}

	public boolean equals(Appointment that) {
		return this.getEventID() == that.getEventID();

	}

	public void setCreatedByID(int id) {
		this.createdByID = id;
	}

	public int getCreatedByID() {
		return createdByID;
	}
}
