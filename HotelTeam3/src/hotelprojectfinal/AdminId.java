package hotelprojectfinal;

public class AdminId {
    public static boolean AdminMake(String adminId, String adminPassword) {
        // 관리자 아이디와 비밀번호 확인하는 코드
        // 예시로 아이디: "5조", 비밀번호: "5555"로 설정
        return "5조".equals(adminId) && "5555".equals(adminPassword);
    }
}