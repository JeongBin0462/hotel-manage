package hotelprojectfinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Room {
	private int roomNumber;
	private boolean isBooked;
	private String roomType;
	private double price;
	private int customerId;
	private List<Calendar> reservations;

	public Room() {
		this.reservations = new ArrayList<>();
	}

	public String getReservationStatus() {
		if (!isBooked) {
			return "예약 가능";
		} else {
			// 예약이 존재하면 가장 빠른 예약의 날짜를 가져와서 표시합니다.
			Calendar earliestReservation = this.reservations.get(0);
			for (Calendar reservation : this.reservations) {
				if (reservation.getCheckInDate().isBefore(earliestReservation.getCheckInDate())) {
					earliestReservation = reservation;
				}
			}
			return "예약 불가 (" + earliestReservation.getCheckInDate() + "부터 " + earliestReservation.getCheckOutDate()
					+ "까지)";
		}
	}

	public List<Calendar> getReservations() {
		return this.reservations;
	}

	public Room(int roomNumber, String roomType, double price) {
		this.reservations = new ArrayList<>();
		this.roomNumber = roomNumber;
		this.isBooked = false;
		this.roomType = roomType;
		this.price = price;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean booked) {
		isBooked = booked;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public boolean isRoomAvailable(LocalDate checkIn, LocalDate checkOut) {
		List<Calendar> reservations = this.getReservations();
		if (reservations != null) {
			for (Calendar reservation : reservations) {
				if ((checkIn.isBefore(reservation.getCheckOutDate())
						&& checkOut.isAfter(reservation.getCheckInDate()))) {
					return false; // 기간이 겹치는 예약이 있는 경우
				}
			}
		}
		return true; // 모든 예약과 기간이 겹치지 않는 경우, 또는 예약이 없는 경우
	}

}
