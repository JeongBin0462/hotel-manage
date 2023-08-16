package hotelprojectfinal;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CustomerMenu {
	private Hotel hotel;
	private Scanner scanner;
	private Customer[] customers;
	private MemberHandler handler;
	private int loggedInCustomerId;
	private PrintCalendar printCalendar;

	public CustomerMenu(Hotel hotel, Scanner scanner, Customer[] customers, PrintCalendar printCalendar) {
		this.hotel = hotel;
		this.scanner = scanner;
		this.customers = customers;
		this.handler = new MemberHandler(100, scanner, customers);
		this.loggedInCustomerId = -1;
		this.printCalendar = printCalendar;
	}

	private boolean checkLogin() {
		if (loggedInCustomerId == -1) {
			System.out.println("[!] 로그인이 필요한 기능입니다.");
			return false;
		}
		return true;
	}

	public void showRoomStatus() {
	    System.out.println("----------- 객실 현황 -----------");
	    for (Room room : hotel.getRooms()) {
	        System.out.println("Room " + room.getRoomNumber() + " : " + room.getReservationStatus());
	    }
	    System.out.println("-------------------------------");
	}

	public void bookRoom() {
		if (!checkLogin()) {
			return;
		}
		showRoomStatus();

		System.out.println("--------------------------");
		System.out.print("예약하실 객실 번호를 입력하세요 : ");
		int roomNumber = scanner.nextInt();
		Room room = hotel.getRoom(roomNumber);
		if (room == null) {
			System.out.println("[!] 유효하지 않은 방 번호입니다.");
			return;
		}
		System.out.println("입실 날짜 선택: ");
		LocalDate roomIn = printCalendar.choiceCalendar();
		System.out.println("퇴실 날짜 선택: ");
		LocalDate roomOut = printCalendar.choiceCalendar();

		if (!room.isRoomAvailable(roomIn, roomOut)) {
			System.out.println("[!] 이미 예약된 객실입니다.");
			return;
		}

		boolean condition = true;
		while (condition)
			if (roomIn.compareTo(roomOut) <= 0) {
				condition = false;
			} else {
				System.out.println("퇴실날짜는 입실날짜 이후여야 합니다.");
				roomOut = printCalendar.choiceCalendar();
			}

		Calendar newReservation = new Calendar(customers[loggedInCustomerId].getName(), roomIn, roomOut);

		boolean conflict = room.getReservations().stream()
				.anyMatch(reservation -> (newReservation.getCheckInDate().isBefore(reservation.getCheckOutDate())
						&& newReservation.getCheckOutDate().isAfter(reservation.getCheckInDate())));

		if (conflict) {
			System.out.println("[!] 이미 예약된 날짜입니다.");
		} else {
			room.getReservations().add(newReservation);
			double price = room.getPrice();
			if (customers[loggedInCustomerId].getLevel().equals("[VIP]")) {
				price = price * 0.9; // VIP는 10% 할인
			}
			System.out.println("--------------------------");
			System.out.println("선택한 객실의 가격은 " + (int) price + "원입니다.\n결제하시겠습니까?\n[1] 예 \n[2] 아니오");
			int paymentChoice = scanner.nextInt();

			if (paymentChoice == 1) {
				room.setBooked(true);
				room.setCustomerId(loggedInCustomerId);
				customers[loggedInCustomerId].addBookedRoomNumber(roomNumber);
				System.out.println("--------------------------");
				System.out.println(roomNumber + "번 객실 결제가 완료되었습니다.\n[결제 금액] " + (int) price + "원");
				System.out.println("--------------------------");
				System.out.println(roomNumber + "번 객실 예약이 완료되었습니다.");
				System.out.println("--------------------------");
				customers[loggedInCustomerId].addTotalPayment(price);
			} else if (paymentChoice == 2) {
				cancelBooking();
			} else {
				System.out.println("[!] 잘못된 선택입니다. 예약이 취소되었습니다.");
				System.out.println("――――――――――――――――――――――――――");
				cancelBooking();
			}
		}
		System.out.println("총 결제 금액: " + (int) customers[loggedInCustomerId].getTotalPayment() + "원");
	}

	public void cancelBooking() {
		if (!checkLogin()) {
			return;
		}

		Customer currentCustomer = customers[loggedInCustomerId];
		List<Integer> bookedRoomNumbers = currentCustomer.getBookedRoomNumbers();
		if (bookedRoomNumbers.isEmpty()) {
			System.out.println("[!] 예약된 객실이 없습니다.");
		} else {
			System.out.println("--------------------------");
			System.out.println("현재 예약된 방 번호 : " + bookedRoomNumbers);
			System.out.println("취소하실 객실 번호를 입력하세요.");
			System.out.println("--------------------------");
			int roomNumberToCancel = scanner.nextInt();

			if (bookedRoomNumbers.contains(roomNumberToCancel)) {
				Room roomToCancel = hotel.getRoom(roomNumberToCancel);
				List<Calendar> reservations = roomToCancel.getReservations();
				Calendar reservationToRemove = reservations.stream()
						.filter(reservation -> reservation.getName().equals(customers[loggedInCustomerId].getName()))
						.findFirst().orElse(null);
				if (reservationToRemove != null) {
					reservations.remove(reservationToRemove);
					System.out.println(roomNumberToCancel + "번 객실 예약이 취소되었습니다.");
					double refundAmount = roomToCancel.getPrice();
					if (customers[loggedInCustomerId].getLevel().equals("[VIP]")) {
					    refundAmount = refundAmount * 0.9; // VIP는 10% 할인
					}
					customers[loggedInCustomerId].subtractTotalPayment(refundAmount);
					System.out.println("취소된 객실의 환불 금액: " + (int) refundAmount + "원");
					System.out.println("현재까지 총 결제 금액: " + (int) customers[loggedInCustomerId].getTotalPayment() + "원");
				} else {
					System.out.println("[!] 입력하신 번호의 객실은 예약되어있지 않습니다.");

				}
				roomToCancel.setBooked(false);
				roomToCancel.setCustomerId(-1);
				currentCustomer.removeBookedRoomNumber(roomNumberToCancel);
				System.out.println("――――――――――――――――――――――――――");
			} else {
				System.out.println("[!] 입력하신 번호의 객실은 예약되어 있지 않습니다.");
			}
		}
	}

	public void showMenu() {
		// 고객 메뉴를 처리하는 코드 작성
		boolean exit = false;

		while (!exit) {
			System.out.println("――――――――――――――――――――――――――");
			System.out.println("                      메 뉴");
			System.out.println("--------------------------");
			System.out.println("[1] 회원가입");
			System.out.println("[2] 로그인");
			System.out.println("[3] 객실상태확인");
			System.out.println("[4] 객실예약하기");
			System.out.println("[5] 내 정보 확인하기");
			System.out.println("[6] 예약취소");
			System.out.println("[7] 로그아웃");
			System.out.println("[0] 뒤로가기");
			System.out.println("――――――――――――――――――――――――――");
			System.out.print("원하시는 메뉴 번호를 입력해주세요: ");
			int choice = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기

			switch (choice) {
			case 1:
				handler.memberInsert();
				break;
			case 2:
				 if (loggedInCustomerId != -1) {
				        System.out.println("[!] 이미 로그인된 상태입니다.");
				    } else {
				        handler.memberLogin();
				        loggedInCustomerId = handler.getLoggedInCustomerId();
				    }
				    break;
			case 3:
				showRoomStatus();
				break;
			case 4:
				bookRoom();
				break;
			case 5:
				if (!checkLogin()) {
					return;
				}
				System.out.println("――――――――――――――――――――――――――");
				System.out.println("                     내 정보");
				System.out.println("--------------------------");
				Customer currentCustomer = customers[loggedInCustomerId];
				System.out.println(currentCustomer.formatted());
				break;
			case 6:
				cancelBooking();
				break;
			case 7:
				loggedInCustomerId = -1;
				System.out.println("로그아웃되었습니다.");
				break;
			case 0:
				loggedInCustomerId = -1;
				System.out.println("로그아웃되었습니다.");
				exit = true;
				break;
			default:
				System.out.println("[!] 잘못된 입력입니다.");
			}
		}
	}
}