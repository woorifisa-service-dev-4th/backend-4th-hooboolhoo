package dev.hooboolhoo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hooboolhoo")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Map<String, Controller> controllerMap = new HashMap<>();

    public FrontController() {
        controllerMap.put("/login", new LoginController());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        Controller controller = controllerMap.get(path);
        controller.execute(req, resp);
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

                    break;
            } 
        }
    }
}