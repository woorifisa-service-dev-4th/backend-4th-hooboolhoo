package dev.hooboolhoo.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        if (!checkValidation(req.getParameter("id"))) {
            out.println("<script>alert('잘못된 이메일 형식입니다.'); location.href=\"signUp.html\";</script>");
            out.flush();
            out.close();
            return;
        }

        String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/hooboolhoo";
        String USER = "root";
        String PASS = "021326cc";

        try {
            Class.forName(DRIVER_NAME);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery("select id, nickname from user");

            while(rs.next()) {
                if (rs.getString("id").equals(req.getParameter("id"))) {
                    out.println("<script>alert('이미 가입한 이메일입니다.'); location.href=\"index.html\";</script>");
                    out.flush();
                    out.close();
                    return;
                } else if (rs.getString("nickname").equals(req.getParameter("nickname"))){
                    out.println("<script>alert('이미 사용중인 닉네임입니다. 다시 선택해주세요.'); location.href=\"signUp.html\";</script>");
                    out.flush();
                    out.close();
                    return;
                }
            }

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO USER(id,password,nickname) VALUES (?,?,?)");

            pstmt.setString(1,req.getParameter("id"));
            pstmt.setString(2,req.getParameter("password"));
            pstmt.setString(3,req.getParameter("nickname"));
            pstmt.executeUpdate();

            out.println("<script>alert('회원가입 완료!'); location.href=\"index.html\";</script>");
            out.flush();
            out.close();
            connection.close();

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean checkValidation(String id) {
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\." +
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(id);

         if (matcher.matches()) { // 이메일 형식이 아닐 경우
            return true;
        } else {
            return false;
        }
    }
}
