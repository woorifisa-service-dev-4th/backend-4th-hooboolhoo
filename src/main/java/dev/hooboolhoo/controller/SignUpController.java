package dev.hooboolhoo.controller;

import dev.hooboolhoo.DAO.DataAccessor;
import dev.hooboolhoo.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SignUpController implements Controller {
    private List<User> userList;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataAccessor dataAccessor = new DataAccessor();
//        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8;");
        PrintWriter out = response.getWriter();


        userList = dataAccessor.getUserList(request.getServletContext());

        for (User user : userList) {
            if (user.getId().equals(request.getParameter("id"))) {
                out.println("<script>alert('이미 가입한 이메일입니다.'); location.href=\""+ request.getContextPath() + "/index.html\";</script>");
                out.flush();
                out.close();
                return;
            } else if (user.getNickname().equals(request.getParameter("nickname"))) {
                out.println("<script>alert('이미 사용중인 닉네임입니다. 다시 선택해주세요.'); location.href=\""+ request.getContextPath() + "/index.html\";</script>");
                out.flush();
                out.close();
                return;
            }
        }

        User newUser = new User(request.getParameter("id"), request.getParameter("password"), request.getParameter("nickname"));

        if (dataAccessor.signUp(request.getServletContext(), newUser)) {
            out.println("<script>alert('회원가입 완료!'); location.href=\""+ request.getContextPath() + "/index.html\";</script>");
            out.flush();
            out.close();
        }

    }
}
