package hotelprojectfinal;

import java.util.Scanner;

public class HotelManagementSystem {
	public static void main(String[] args) {
		Hotel hotel = new Hotel();
		Scanner scanner = new Scanner(System.in);
		Customer[] customers = new Customer[100];
		PrintCalendar printCalendar = new PrintCalendar();
		CustomerMenu customerMenu = new CustomerMenu(hotel, scanner, customers, printCalendar);
		ManagerMenu managerMenu = new ManagerMenu(hotel, scanner, customers, printCalendar);
		boolean exit = false;
		while (!exit) {
			System.out.println("――――――――――――――――――――――――――");
			System.out.println("                      메 뉴");
			System.out.println("--------------------------");
			System.out.println("[1] 고객 메뉴");
			System.out.println("[2] 관리자 메뉴");
			System.out.println("[3] 종료");
			System.out.println("――――――――――――――――――――――――――");
			System.out.println("원하시는 메뉴 번호를 입력해주세요");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				customerMenu.showMenu();
				break;
			case 2:
				System.out.print("[아이디] : ");
				String adminId = scanner.nextLine();
				System.out.print("[패스워드] : ");
				String adminPassword = scanner.nextLine();

				if (AdminId.AdminMake(adminId, adminPassword)) {
					managerMenu.showMenu();
				} else {
					System.out.println("[!] 잘못된 아이디 또는 비밀번호입니다.");
				}
				break;
			case 3:
				exit = true;
				break;
			default:
				System.out.println("[!] 잘못된 입력입니다.");
			}
		}
	}
}
