package hotelprojectfinal;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	private String name;
	private String phoneNumber;
	private String id;
	private String password;
	private String level;
	private List<Integer> bookedRoomNumbers;
	private double totalPayment;
//	private List<Calendar> addReservation;

	public Customer(String name, String phoneNumber, String id, String password) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.id = id;
		this.password = password;
		this.level = "일반";
		this.bookedRoomNumbers = new ArrayList<>();
	}

	public void subtractTotalPayment(double amount) {
		this.totalPayment -= amount;
	}

	public void addTotalPayment(double price) {
		this.totalPayment += price;
	}

	public double getTotalPayment() {
		return this.totalPayment;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String formatted() {
		return "회원이름: " + name + "\n휴대폰 번호: " + phoneNumber + "\n아이디: " + id + "\n비밀번호: " + password;
	}

	public void addBookedRoomNumber(int roomNumber) {
		bookedRoomNumbers.add(roomNumber);
	}

	public void removeBookedRoomNumber(int roomNumber) {
		bookedRoomNumbers.remove(Integer.valueOf(roomNumber));
	}

	public List<Integer> getBookedRoomNumbers() {
		return bookedRoomNumbers;
	}

	public boolean hasBookedRoomNumber(int roomNumber) {
		return bookedRoomNumbers.contains(roomNumber);
	}
}
