package appointment;

public class Room {

	private int roomNumber;
	private int capacity;
	private boolean availability;

	public Room(int numb, int cap, boolean available) {

		roomNumber = numb;
		capacity = cap;
		availability = true;

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
