package dev.hooboolhoo.controller;

import dev.hooboolhoo.DAO.DataAccessor;
import dev.hooboolhoo.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class LoginController implements Controller {
	private List<User> userList;
    
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        String userId = request.getParameter("id");
        String userPassword = request.getParameter("password");
        DataAccessor dataAccessor = new DataAccessor();
        
        userList = dataAccessor.getUserList(request.getServletContext());

        // 사용자 목록을 가져오기
        List<User> userList = dataAccessor.getUserList(request.getServletContext());

        // 사용자 목록을 순회하면서 ID와 비밀번호를 확인하기
        for (User user : userList) {
            if (user.getId().equals(userId)) {
                // 아이디가 존재할 경우, 비밀번호 확인하기
                if (user.getPassword().equals(userPassword)) {
                    // 로그인 성공
                    out.println("<script>alert('" + user.getNickname() + "님, 환영합니다.'); location.href=\"main.html\";</script>");
                    out.flush();
                    out.close();
                    return;  // 로그인 성공 후 종료하기
                } else {
                    // 비밀번호 틀림
                    out.println("<script>alert('비밀번호가 틀렸습니다. 다시 로그인해주세요.'); location.href=\"index.html\";</script>");
                    out.flush();
                    out.close();
                    return;  // 비밀번호 틀리면 종료
                }
            }
        }

        // 아이디가 존재하지 않는 경우
        out.println("<script>alert('존재하지 않는 아이디입니다. 다시 로그인해주세요.'); location.href=\"index.html\";</script>");
        out.flush();
        out.close();
    }
}
