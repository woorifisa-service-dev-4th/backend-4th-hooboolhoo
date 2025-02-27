package dev.hooboolhoo.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.hooboolhoo.config.DataSource;

public class GameCreation {
    public static void handleRequest(HttpServletRequest request, HttpServletResponse response, ServletContext context) throws IOException {
        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String subtitle = request.getParameter("subtitle");
        String category = request.getParameter("category");
        String leftChoice = request.getParameter("left-choice");
        String rightChoice = request.getParameter("right-choice");
        String author = "dummy@naver.com";

        try (Connection conn = DataSource.getConnection()) {
            String sql = "INSERT INTO GAME(author_id, category, title, subtitle, leftValue, rightValue) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, author);
                pstmt.setString(2, category);
                pstmt.setString(3, title);
                pstmt.setString(4, subtitle);
                pstmt.setString(5, leftChoice);
                pstmt.setString(6, rightChoice);
                
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    response.sendRedirect("gameList");
                } else {
                    response.getWriter().write("게임 생성 실패");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("데이터베이스 오류 발생");
        }
    }
}
