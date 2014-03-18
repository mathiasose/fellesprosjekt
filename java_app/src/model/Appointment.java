package model;

import java.util.List;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

public class Appointment {
	
	private int eventID;
	private User createdBy;
	private Date date;
	private String description, location;
	private Calendar startTime; 
	private int duration;
	private ArrayList<Invitation> participants;
	private int meetingRoom;
	
	private PropertyChangeSupport pcs;
	
	
	public Appointment ( String des, String loc, Date dat){
		
		
		description  = des;
		location = loc;
		date = dat;
		
		
		pcs = new PropertyChangeSupport(this);
		
	}
	


	public Appointment (){
		
	}
	
	
	public int getEventID() {
		return eventID;
	}



	public void setEventID(int eventID) {
		this.eventID = eventID;
	}



	public User getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}



	public ArrayList<Invitation> getParticipants() {
		return participants;
	}



	public void setParticipants(ArrayList<Invitation> participants) {
		this.participants = participants;
	}

	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}
	
	public String toString() {
		return description + " " + location + " " + date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date hei) {
		this.date = hei;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		
		/*
		String oldValue = this.description;
		pcs.firePropertyChange("description", oldValue, description);
		*/
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Calendar getStartTime() {
		return startTime;
	}

	public void setStartTime(Calendar startTime) {
		this.startTime = startTime;
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

}
