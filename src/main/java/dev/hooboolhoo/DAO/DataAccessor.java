package dev.hooboolhoo.DAO;

import dev.hooboolhoo.config.DataSource;
import dev.hooboolhoo.model.User;

import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessor {

    // 사용자 목록을 가져오는 메서드
    public List<User> getUserList(ServletContext context) {
        List<User> userList = new ArrayList<>();

        try (Connection connection = DataSource.getConnection(context)) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM user");

            while (rs.next()) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                String nickname = rs.getString("nickname");
                User user = new User(id, password, nickname);
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    // 회원가입을 처리하는 메서드
    public boolean signUp(ServletContext context, User newUser) {
        try (Connection connection = DataSource.getConnection(context)) {
            String query = "INSERT INTO USER(id, password, nickname) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, newUser.getId());
            pstmt.setString(2, newUser.getPassword());
            pstmt.setString(3, newUser.getNickname());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // 오류가 발생하면 false 반환
        }

        return true;  // 회원가입 성공 시 true 반환
    }

    // 사용자 ID로 사용자 정보를 가져오는 메서드
    public User getUserById(ServletContext context, String userId) {
        User user = null;

        try (Connection connection = DataSource.getConnection(context)) {
            String query = "SELECT id, password, nickname FROM user WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("id");
                String password = rs.getString("password");
                String nickname = rs.getString("nickname");
                user = new User(id, password, nickname);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
