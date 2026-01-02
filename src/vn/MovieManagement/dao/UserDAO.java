package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import java.sql.*;

public class UserDAO {

    public int checkLogin(String user, String passWord) {
        String sql = "SELECT id FROM users where username = ? AND password = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, user);
            psmt.setString(2, passWord);
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id"); 
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void register(String user, String passWord) {
        String sql = "INSERT INTO Users(username, password) VALUES(?, ?)";
        try(Connection conn = DBConnection.getConnection(); 
        PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, user);
            psmt.setString(2, passWord);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
