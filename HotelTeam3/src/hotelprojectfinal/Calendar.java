package hotelprojectfinal;

import java.time.LocalDate;

public class Calendar {
	private String name;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;

	public Calendar(String name, LocalDate checkInDate, LocalDate checkOutDate) {
		this.name = name;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
}
