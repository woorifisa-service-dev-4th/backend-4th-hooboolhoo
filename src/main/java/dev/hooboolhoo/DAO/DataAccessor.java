package dev.hooboolhoo.DAO;

import dev.hooboolhoo.config.DataSource;
import dev.hooboolhoo.model.User;

import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccessor {
    public List<User> getUserList(ServletContext context) {
        List<User>userList = new ArrayList<User>();

        try(Connection connection = DataSource.getConnection(context)) {
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery("select * from user");

            String id;
            String password;
            String nickname;
            User user;

            while(rs.next()) {
                id = rs.getString("id");
                password = rs.getString("password");
                nickname = rs.getString("nickname");
                user = new User(id, password, nickname);
                userList.add(user);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public boolean signUp(ServletContext context, User newUser) {
        try (Connection connection = DataSource.getConnection(context)){
            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO USER(id,password,nickname) VALUES (?,?,?)");

            pstmt.setString(1,newUser.getId());
            pstmt.setString(2,newUser.getPassword());
            pstmt.setString(3,newUser.getNickname());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
