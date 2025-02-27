package dev.hooboolhoo.DAO;

import dev.hooboolhoo.config.DataSource;
import dev.hooboolhoo.model.Game;

import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDataAccessor {

    public List<Game> getGameList(ServletContext context) {
        List<Game> gameList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection(context)) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GAME");

            while (rs.next()) {
                String author = rs.getString("author_id");
                String category = rs.getString("category");
                String title = rs.getString("title");
                String subtitle = rs.getString("subtitle");
                String gameThumbnail = rs.getString("game_thumbnail");
                int id = rs.getInt("id");

                Game game = new Game();
                game.setAuthor(author);
                game.setCategory(category);
                game.setTitle(title);
                game.setSubTitle(subtitle);
                game.setId(id);
                game.setGameThumbnail(gameThumbnail);

                gameList.add(game);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameList;
    }

    // 새로운 게임 삽입
    public boolean insertGame(ServletContext context, Game newGame) {
        try (Connection connection = DataSource.getConnection(context)) {
            String sql = "INSERT INTO GAME(author_id, category, title, subtitle, game_thumbnail) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, newGame.getAuthor());
            pstmt.setString(2, newGame.getCategory());
            pstmt.setString(3, newGame.getTitle());
            pstmt.setString(4, newGame.getSubTitle());
            pstmt.setString(5, newGame.getGameThumbnail());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
