package dev.hooboolhoo.controller;

import dev.hooboolhoo.model.Choice;
import dev.hooboolhoo.model.Game;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        // DB Connection
        String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/hooboolhoo";
        String USER = "root";
        String PASS = "1234";

        String user_id = "";
        String user_password = "";
        String user_nickname = "";

        try {
            Class.forName(DRIVER_NAME);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from user where id = \"" + req.getParameter("id") + "\""); // TODO: Prevent SQL Injection

            if (rs.next()) {
                user_id = rs.getString("id");
                user_password = rs.getString("password");
                System.out.println("");
                user_nickname = rs.getString("nickname");
            } else {
                out.println("<script>alert('존재하지 않는 아이디입니다. 다시 로그인해주세요.'); location.href=\"index.html\";</script>");
                out.flush();
                out.close();
            }

            connection.close();

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (user_password.equals(req.getParameter("password"))) {
            // TODO: Login Logic
            out.println("<script>alert('"+ user_nickname + "님, 환영합니다.'); location.href=\"main.html\";</script>");
            out.flush();
            out.close();
        } else {
            out.println("<script>alert('비밀번호가 틀렸습니다. 다시 로그인해주세요.'); location.href=\"index.html\";</script>");
            out.flush();
            out.close();
        }

    }
}
