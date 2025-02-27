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
        // 요청 경로와 해당 처리 클래스를 매핑
        controllerMap.put("/login", new LoginController());  // 로그인 경로 추가
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 요청 경로에 맞는 controller 찾기
        String path = req.getPathInfo();

        // 경로에 해당하는 controller가 있으면 실행
        Controller controller = controllerMap.get(path);
        if (controller != null) {
            controller.execute(req, resp);  // 로그인 컨트롤러 실행
        } else {
            // 컨트롤러가 없으면 에러 처리
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 URI 확인
        String uri = request.getRequestURI();
        String[] uriParts = uri.split("/");

        if (uriParts.length > 1) {
            String command = uriParts[uriParts.length - 1];
            LoginController loginController = new LoginController();

            // 요청에 맞는 작업 처리
            switch (command) {
                case "login":
                    // 로그인 요청에 대해 로그인 컨트롤러 실행
                    loginController.execute(request, response);
                    break;

                case "signup":
                    // 회원가입 처리 (추가할 경우)
                    break;

                case "myPage":
                    // 마이페이지 처리 (추가할 경우)
                    break;

                case "game":
                    // 게임 처리 (추가할 경우)
                    break;

                default:
                    // 잘못된 요청에 대해 404 응답
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Page not found");
                    break;
            }
        }
    }
}