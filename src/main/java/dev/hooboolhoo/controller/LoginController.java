package dev.hooboolhoo.controller;

import java.sql.*;

public class LoginController {

    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/hooboolhoo";
    private static final String USER = "root";
    private static final String PASS = "021326cc";

    public boolean authenticate(String userId, String password) {
        String user_password = "";
        String user_nickname = "";

        try {
            // 1. DB 연결
            Class.forName(DRIVER_NAME);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);

            // 2. 사용자 정보 조회
            String query = "SELECT password, nickname FROM user WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user_password = rs.getString("password");
                user_nickname = rs.getString("nickname");
            }

            connection.close();

            // 3. 비밀번호 비교
            if (user_password.equals(password)) {
                System.out.println(user_nickname + "님, 환영합니다.");  // 로그인 성공 메시지 출력
                return true;
            } else {
                System.out.println("비밀번호가 틀렸습니다.");
                return false;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
