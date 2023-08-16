package hotelprojectfinal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrintCalendar {
	private Scanner scanner = new Scanner(System.in);
	private List<Calendar> addReservation;
	private LocalDate now = LocalDate.now();

	public PrintCalendar() {
		addReservation = new ArrayList<>();
	}

	public void addReservetion(String name, LocalDate checkInDate, LocalDate checkOutDate) {
		Calendar reservation = new Calendar(name, checkInDate, checkOutDate);
		addReservation.add(reservation);
	}

	public List<Calendar> getGuestList() {
		return addReservation;
	}

	public void printCalendar(int year, int month) {
		LocalDate lastDate = LocalDate.of(year, month, 1).withDayOfMonth(LocalDate.of(year, month, 1).lengthOfMonth()); // 달의
																														// 마지막
																														// 날
		int lastDateMonth = lastDate.getDayOfMonth();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd"); // 00일
		String formatted = formatter.format(LocalDate.of(year, month, 1));
	
		System.out.println("――――――――――――――――――――――――――");
		System.out.println(year + "년 " + month + "월"); // 1~2줄 출력
		System.out.println(" 일   월    화    수   목    금    토");

		int daySpace = LocalDate.of(year, month, 1).getDayOfWeek().getValue(); // 요일 공백
		for (int i = 0; i < daySpace; i++) {
			if (daySpace == 7) {
				break;
			}
			System.out.print("   ");
		}
		
		// 결과
		for (int i = 0; i < lastDateMonth; i++) {
			LocalDate tomorrow = LocalDate.of(year, month, 1).plusDays(i); // 다음날
			formatted = formatter.format(tomorrow); // 다음날 형변환
			int week = tomorrow.getDayOfWeek().getValue(); // 요일
			System.out.print(formatted + " ");
			if (week == 6) {
				System.out.println("");
			}
		}
	}

	public LocalDate choiceCalendar() {
		boolean condition = true;
		
		int year = now.getYear();
		int month = now.getMonthValue();
		int day = now.getDayOfMonth();

		printCalendar(year, month);
		System.out.println();
		System.out.println("--------------------------");
		System.out.println("◀이전 달                       다음 달▶");

		while (condition) {
			System.out.println("――――――――――――――――――――――――――");
			System.out.println("[1] 이전 달 출력");
			System.out.println("[2] 다음 달 출력");
			System.out.println("[3] 날짜 선택");
			System.out.println("[4] 취소");
			int choice = scanner.nextInt();
			scanner.nextLine();
			switch (choice) {
			case 1:
				// 지난달
				month--;
				if (month == 0) {
					year--;
					month = 12;
				}
				printCalendar(year, month);
				System.out.println("");
				System.out.println("--------------------------");
				System.out.println("◀이전 달                 다음 달▶");
				break;
			case 2:
				// 다음달
				month++;
				if (month == 13) {
					year++;
					month = 1;
				}
				printCalendar(year, month);
				System.out.println("");
				System.out.println("--------------------------");
				System.out.println("◀이전 달                 다음 달▶");
				break;
			case 3:
				// 날짜선택
				System.out.println("");
				System.out.println(month + "월 몇일?");
				day = scanner.nextInt();
				scanner.nextLine();
				condition = false;
				break;
			case 4:
				condition = false;
				break;
			default:
				System.out.println("[!] 잘못된 입력입니다.");
				break;
			}
		}
		return LocalDate.of(year, month, day);
	}
}
	
	/*
	public static void main(String[] args) {
		PrintCalendar test = new PrintCalendar();
		LocalDate first = test.choiceCalendar();
		LocalDate second = test.choiceCalendar();
		test.addReservetion("dd", first, second);

		List<Calendar> addReservation = test.getGuestList();
		for (Calendar calendar : addReservation) {
			System.out.println("Name: " + calendar.getName());
			System.out.println("Check-in Date: " + calendar.getCheckInDate());
			System.out.println("Check-out Date: " + calendar.getCheckOutDate());
			System.out.println();
		}
	}
}
// 날짜선택
 * public void choiceDate() {
 * 
 * System.out.println("입실 날짜?\n"); printCalendar.choiceCalendar(); int
 * choiceMonth = printCalendar; int choiceDay = Calendar.getChoiceDay(); String
 * inRoom = String.format("%04d/%02d/%02d", LocalDate.now().getYear(),
 * choiceMonth, choiceDay); LocalDate inRoomDate = LocalDate.parse(inRoom,
 * DateTimeFormatter.ofPattern("yyyy/MM/dd"));
 * 
 * LocalDate outRoomDate = LocalDate.now(); boolean condition = true;
 * 
 * while (condition) { System.out.println("퇴실 날짜?\n");
 * PrintCalendar.choiceCalendar(); choiceMonth = Calendar.getChoiceMonth();
 * choiceDay = Calendar.getChoiceDay(); String outRoom =
 * String.format("%04d/%02d/%02d", LocalDate.now().getYear(), choiceMonth,
 * choiceDay); outRoomDate = LocalDate.parse(outRoom,
 * DateTimeFormatter.ofPattern("yyyy/MM/dd"));
 * 
 * if (inRoomDate.isAfter(outRoomDate)) {
 * System.out.println("퇴실날짜는 입실날짜 이후여야 합니다."); } else { condition = false; } }
 * 
 * System.out.println("입실: " + inRoomDate); System.out.println("퇴실: " +
 * outRoomDate); } }
 */