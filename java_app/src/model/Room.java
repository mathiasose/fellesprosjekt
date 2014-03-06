<<<<<<< HEAD:java_app/src/addAppointment/Room.java
package addAppointment;

=======
package model;
>>>>>>> 98fe4bf7cc6c5a3737559e5cb7427ee852673f99:java_app/src/model/Room.java

public class Room {

	private int roomID;
	private int roomNumber;
	private int capacity;
	private boolean availability;
	
	public Room( int id, int numb, int cap, boolean available){
		
		roomID = id;
		roomNumber = numb;
		capacity = cap;
		availability = true;
		
	}
	
	
	
	
	public int getRoomID() {
		return roomID;
	}




	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}




	public boolean isAvailability() {
		return availability;
	}




	public void setAvailability(boolean availability) {
		this.availability = availability;
	}




	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	
	
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
}

