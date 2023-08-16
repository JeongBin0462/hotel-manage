package hotelprojectfinal;

import java.util.Scanner;

class MemberHandler {
	private Customer[] customers;
	private int idx;
	private Scanner scanner;
	private int loggedInCustomerId;

	public MemberHandler(int num, Scanner scanner, Customer[] customers) {
		this.customers = customers;
		idx = 0;
		this.scanner = scanner;
	}

	public int getLoggedInCustomerId() {
		return loggedInCustomerId;
	}

	public void memberInsert() { // 회원가입용 메소드
		String name, phoneNumber, id, password;
		System.out.println("――――――――――――――――――――――――――");
		System.out.println("                   회원 가입");
		System.out.println("--------------------------");
		System.out.print("[이름]을 입력해주세요: ");
		name = scanner.nextLine();
		System.out.print("[휴대폰 번호]를 입력해주세요: ");
		phoneNumber = scanner.nextLine();
		System.out.print("[아이디]를 입력해주세요: ");
		id = scanner.nextLine();
		if (!isUniqueID(id)) // 아이디가 중복되었으면
		{
			System.out.println("[!] 이미 사용중인 아이디 입니다.");
			return;
		}
		System.out.print("[암호]를 입력해주세요: ");
		password = scanner.nextLine();
		customers[idx] = new Customer(name, phoneNumber, id, password);
		idx++;
		System.out.printf("\n%s님 환영합니다! 가입이 완료되었습니다.\n", name);
	}

	private boolean isUniqueID(String uid) { // 아이디의 중복 여부를 리턴
		if (idx == 0)
			return true;

		for (int i = 0; i < idx; i++) {
			if (customers[i].getId().equals(uid)) {
				return false;
			}
		}
		return true;
	}

	public void memberLogin() {
		System.out.println("――――――――――――――――――――――――――");
		System.out.println("                      로그인");
		System.out.println("--------------------------");
		System.out.print("[아이디] : ");
		String id = scanner.nextLine();
		System.out.print("[패스워드] : ");
		String pwd = scanner.nextLine();
		String msg = "[!] 존재하지 않는 아이디 입니다.";

		for (int i = 0; i < idx; i++) {
			if (customers[i].getId().equals(id)) {//
				if (customers[i].getPassword().equals(pwd)) {
					loggedInCustomerId = i;
					msg = "로그인 되었습니다.";
				} else {
					msg = "[!] 암호가 잘못되었습니다.";
				}
			}
		}
		System.out.println(msg);
	}

	public Customer[] getCustomer() {
		return customers;
	}
}