package dev.hooboolhoo.controller;

import dev.hooboolhoo.model.Choice;
import dev.hooboolhoo.model.Game;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/gameList")
public class GameListServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        // DB Connection
        String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/hooboolhoo";
        String USER = "root";
        String PASS = "021326cc";
        Game newGame;

        int id;
        String author;
        String title;
        String subtitle;
        String leftChoice;
        int leftChoiceCount;
        String rightChoice;
        int rightChoiceCount;

        Choice left;
        Choice right;
        List<Choice> choices;

        List<Game> currentGames = new ArrayList<Game>();

        try {
            Class.forName(DRIVER_NAME);
            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT g.*, \n" +
                    "       COUNT(CASE WHEN c.choice_value = 0 THEN 1 END) AS leftValueCount,\n" +
                    "       COUNT(CASE WHEN c.choice_value = 1 THEN 1 END) AS rightValueCount\n" +
                    "FROM game g\n" +
                    "LEFT JOIN choice c ON g.id = c.game_id\n" +
                    "GROUP BY g.id;");

            while (rs.next()) {
                id = rs.getInt("id");
                author = rs.getString("author_id");
                title = rs.getString("title");
                subtitle = rs.getString("subtitle");
                leftChoice = rs.getString("leftValue");
                leftChoiceCount = rs.getInt("leftValueCount");
                rightChoice = rs.getString("rightValue");
                rightChoiceCount = rs.getInt("rightValueCount");

                newGame = new Game();

                left = new Choice(leftChoice, leftChoiceCount);
                right = new Choice(rightChoice, rightChoiceCount);

                choices = new ArrayList<>();
                choices.add(left);
                choices.add(right);

                newGame.setId(id);
                newGame.setAuthor(author);
                newGame.setTitle(title);
                newGame.setSubTitle(subtitle);
                newGame.setChoices(choices);

                currentGames.add(newGame);
            }

            connection.close();

        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        req.setAttribute("gameList", currentGames);
        String url = "gameList.jsp";
        RequestDispatcher dispatcher = req.getRequestDispatcher(url);
        dispatcher.forward(req, resp);

        resp.setStatus(200);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
    }
}
