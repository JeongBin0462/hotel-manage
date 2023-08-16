package hotelprojectfinal;

import java.util.List;
import java.util.Scanner;

public class ManagerMenu {
	private Hotel hotel;
	private Scanner scanner;
	private Customer[] customers;
	private PrintCalendar printCalendar;
	// private MemberHandler m1;

	public ManagerMenu(Hotel hotel, Scanner scanner, Customer[] customers, PrintCalendar printCalendar) {
		this.hotel = hotel;
		this.scanner = scanner;
		this.customers = customers;
		this.printCalendar = printCalendar;
	}

	public void showRoomStatus() {
		Room[] rooms = hotel.getRooms();
		for (Room room : rooms) {
			String status = room.isBooked() ? "[예약 불가]" : "[예약 가능]";
			if (room.isBooked()) {
				int customerId = room.getCustomerId();
				String customerName = customers[customerId].getName();
				status += " [예약 고객: " + customerName + "]";
			}
			System.out.println("――――――――――――――――――――――――――");
			System.out.println("                    객실 목록");
			System.out.println("--------------------------");
			System.out.println("[Room] " + room.getRoomNumber() + " (" + room.getRoomType() + ") " + status);
			System.out.println("――――――――――――――――――――――――――");
		}
	}

	public void printCustomer() {
		for (Customer customer : customers) {
			if (customer != null) {
				System.out.println(customer.formatted());
				System.out.println("--------------------------");
			}
		}
	}

	public void printCustomerList() {
		for (Customer customer : customers) {
			if (customer != null) {
				System.out.printf("[이름] %s    (%s)", customer.getName(), customer.getLevel());
			}
		}
	}

	public void checkRoom() {
		System.out.print("조회할 방번호를 입력하세요: ");
		int inputRoomNum = scanner.nextInt();
		for (Customer customer : customers) {
			if (customer != null) {
				List<Integer> list = customer.getBookedRoomNumbers();
				List<Calendar> addReservation = printCalendar.getGuestList();

				for (int i = 0; i < list.size(); i++) {
					int value = list.get(i);
					if (value == inputRoomNum) {
						System.out.println("――――――――――――――――――――――――――");
						System.out.printf("    %d방에 예약된 고객 정보\n", inputRoomNum);
						System.out.println("--------------------------");
						System.out.println(customer.formatted());
						for (Calendar calendar : addReservation) {
							if (list == customer.getBookedRoomNumbers()
									&& customer.getName().equals(calendar.getName())) {
								System.out.println("입실날짜: " + calendar.getCheckInDate());
								System.out.println("퇴실날짜: " + calendar.getCheckOutDate());
								break;
							}
						}
						break;
					} else {
						System.out.println("[!] 해당 객실은 예약정보가 없습니다.");
					}

				}
			}
		}
	}

	public void checkCustomer() {
		System.out.println("――――――――――――――――――――――――――");
		System.out.print("조회할 고객의 이름 또는 전화번호를 입력하세요: ");
		String input = scanner.nextLine();
		for (Customer customer : customers) {
			if (customer != null && (customer.getName().equals(input) || customer.getPhoneNumber().equals(input))) {
				System.out.println(customer.formatted());
				System.out.println("예약한 방 번호: " + customer.getBookedRoomNumbers());

				List<Calendar> addReservation = printCalendar.getGuestList();
				for (Calendar calendar : addReservation) {
					if (calendar != null && calendar.getName().equals(customer.getName()))
						;

					System.out.println("입실날짜: " + calendar.getCheckInDate());
					System.out.println("퇴실날짜: " + calendar.getCheckOutDate());
					break;
				}
			}
		}
	}

	public void changeCondition() {
		System.out.print("상태를 변경할 방 번호를 입력하세요: ");
		int roomNumber = scanner.nextInt();
		Room room = hotel.getRoom(roomNumber);
		if (room != null) {
			boolean currentStatus = room.isBooked();
			room.setBooked(!currentStatus);
			System.out.println(roomNumber + "번 방의 상태가 변경되었습니다.");
		} else {
			System.out.println("[!] 잘못된 방 번호입니다.");
		}
	}

	public void changeLevel() {
		System.out.println("등급을 변경할 고객의 이름을 입력하세요");
		String name = scanner.nextLine();
		System.out.println("새 등급을 입력하세요");
		String newLevel = scanner.nextLine();
		for (Customer customer : customers) {
			if (customer != null && customer.getName().equals(name)) {
				customer.setLevel(newLevel);
				System.out.println("--------------------------");
				System.out.println(name + " 고객의 등급이 " + newLevel + "로 변경되었습니다.");
			}
		}
	}

	public void showMenu() {
		// 관리자 메뉴를 처리하는 코드 작성
		boolean exit = false;
		while (!exit) {
			System.out.println("――――――――――――――――――――――――――");
			System.out.println("                      메 뉴");
			System.out.println("--------------------------");
			System.out.println("[1] 현재 객실 상태 조회");
			System.out.println("[2] 방번호 검색 조회");
			System.out.println("[3] 고객 정보 검색 조회");
			System.out.println("[4] 객실 상태 변경");
			System.out.println("[5] 고객 등급 설정");
			System.out.println("[6] 뒤로 가기");
			System.out.println("――――――――――――――――――――――――――");
			System.out.println("원하시는 메뉴 번호를 입력하세요");
			int choice = scanner.nextInt();
			scanner.nextLine(); // 버퍼 비우기

			switch (choice) {
			case 1:
				showRoomStatus();
				break;
			case 2:
				checkRoom(); // 방 번호로 예약고객 정보 조회
				break;
			case 3:
				checkCustomer();
				System.out.println("――――――――――――――――――――――――――");
				break;
			case 4:
				System.out.println("――――――――――――――――――――――――――");
				System.out.println("                객실 상태 변경");
				System.out.println("--------------------------");
				changeCondition();// 객실 상태 변경 처리(이미 예약돼있는 방 예약취소가능,청소가 되어있는지 안되어있는지 변경가능)
				System.out.println("――――――――――――――――――――――――――");
				break;
			case 5:
				System.out.println("――――――――――――――――――――――――――");
				System.out.println("                고객 등급 설정");
				System.out.println("--------------------------");
				printCustomerList();
				changeLevel();// 고객 등급 설정 처리(회원가입시 String타입 "일반"으로 설정되어있음, 변경가능)
				break;
			case 6:
				exit = true;
				break;
			default:
				System.out.println("[!] 잘못된 입력입니다.");
			}
		}
	}
}