package dev.hooboolhoo.controller;

import java.io.IOException;
<<<<<<< Updated upstream
=======
import java.util.HashMap;
import java.util.Map;
>>>>>>> Stashed changes

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.hooboolhoo.config.DataSource;

@WebServlet("/hooboolhoo/*")
public class FrontController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FrontController() {
<<<<<<< Updated upstream
        super();
=======
        controllerMap.put("/signUp", new SignUpController());
        controllerMap.put("/createGame", new GameCreationController());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo();

        Controller controller = controllerMap.get(path);
        controller.execute(req, resp);
>>>>>>> Stashed changes
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
        
        DataSource.init(getServletContext());

        // 요청 URI에 따라 처리할 서블릿 선택
        if (uriParts.length > 1) {
            String command = uriParts[uriParts.length - 1];

<<<<<<< Updated upstream
            switch (command) {
                case "signup":

=======
>>>>>>> Stashed changes
                    break;
                case "login":

                    break;
                case "myPage":

                    break;
                case "game":
                    break;
                    
                case "createGame":
                	GameCreation.handleRequest(request, response, getServletContext());
                    break;
                    
                default:

                    break;
            }
        }
    }
}