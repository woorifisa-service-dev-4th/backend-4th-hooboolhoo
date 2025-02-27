package dev.hooboolhoo.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.hooboolhoo.DAO.GameDataAccessor;
import dev.hooboolhoo.model.Game;

public class GameCreationController implements Controller {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 폼 데이터 가져오기
        String title = request.getParameter("title");
        String subtitle = request.getParameter("subtitle");
        String category = request.getParameter("category");
        String author = "dummy@naver.com"; 
        String gameThumbnail = request.getParameter("game-thumbnail");

        // 새 게임 객체 생성
        Game newGame = new Game();
        newGame.setTitle(title);
        newGame.setSubTitle(subtitle);
        newGame.setCategory(category);
        newGame.setAuthor(author);
        newGame.setGameThumbnail(gameThumbnail);

        // 게임 삽입 처리
        GameDataAccessor gamedataAccessor = new GameDataAccessor();
        boolean isSuccess = gamedataAccessor.insertGame(request.getServletContext(), newGame);

        if (isSuccess) {
            response.sendRedirect("gameList");  // 게임 목록 페이지로 리다이렉트
        } else {
            response.getWriter().println("<script>alert('게임 생성 실패'); location.href=\"gameCreation.jsp\";</script>");
        }
    }
}
