package dev.hooboolhoo.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.hooboolhoo.config.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/hooboolhoo/createGame")
public class GameCreationServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String title = req.getParameter("title");
        System.out.println(title);
        String subtitle = req.getParameter("subtitle");
        System.out.println(subtitle);
        String category = req.getParameter("category");
        System.out.println(category);
        String leftChoice = req.getParameter("left-choice");
        System.out.println(leftChoice);
        String rightChoice = req.getParameter("right-choice");
        System.out.println(rightChoice);
        String author = "dummy@naver.com"; // TODO

        try (Connection conn = DataSource.getConnection()){
 
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO GAME(author_id, category, title, subtitle, leftValue, rightValue) VALUES (?,?,?,?,?,?)");

            pstmt.setString(1,author);
            pstmt.setString(2,category);
            pstmt.setString(3,title);
            pstmt.setString(4,subtitle);
            pstmt.setString(5,leftChoice);
            pstmt.setString(6,rightChoice);

            pstmt.executeUpdate();
            conn.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        resp.sendRedirect("gameList");
    }
}
