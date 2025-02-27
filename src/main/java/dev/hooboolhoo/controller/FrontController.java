package dev.hooboolhoo.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/*")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FrontController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        String[] uriParts = uri.split("/");
        
        
        try {
        	
        	 String DB_URL = "jdbc:mysql://192.168.0.216:3306/";
        	String DATABASE_NAME = "hooboolhoo";
        	String USER = "root";
        	 String PASSWORD = "021326cc";
        	 String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
        	 Class.forName(DRIVER_NAME);
        	Connection connection = DriverManager.getConnection(DB_URL + DATABASE_NAME,USER,PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
        	e.printStackTrace();
        }

        // 요청 URI에 따라 처리할 서블릿 선택
        if (uriParts.length > 1) {
            String command = uriParts[uriParts.length - 1];

            switch (command) {
                case "signup":

                    break;
                case "login":

                    break;
                case "myPage":

                    break;
                case "game":
                    break;
                default:
                    response.sendRedirect("/index.jsp");
                    break;
            }
        }
    }
}