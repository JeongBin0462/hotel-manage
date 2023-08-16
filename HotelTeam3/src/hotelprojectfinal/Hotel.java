package hotelprojectfinal;


public class Hotel {
	private Room[] rooms;

	public Hotel() {
		this.rooms = new Room[80]; // 80개의 객실이 있음
		int roomNumber = 201;
		for (int i = 0; i < 80; i++) {
			String roomType = (roomNumber % 2 == 0) ? "더블" : "싱글";
			double price = (roomType.equals("더블")) ? 200000 : 120000;
			rooms[i] = new Room(roomNumber, roomType, price);
			roomNumber++;
			if (roomNumber % 100 == 21) {
				roomNumber += 80;
			}
		}
	}
	public Room getRoom(int roomNumber) {
		for (Room room : rooms) {
			if (room.getRoomNumber() == roomNumber) {
				return room;
			}
		}
		return null; // 찾지 못한 경우 null 반환
	}

	public Room[] getRooms() {
		return rooms;
	}

	public boolean roomRange(int roomNumber) {
		if (roomNumber < 201 || roomNumber > 520) {
			return false;
		}
		int floor = roomNumber / 100;
		int room = roomNumber % 100;
		return floor >= 2 && floor <= 5 && room >= 1 && room <= 20;
	}

}
