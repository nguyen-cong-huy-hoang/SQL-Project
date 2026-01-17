package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import java.sql.*;

import javax.naming.spi.DirStateFactory.Result;

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

    public boolean register(String user, String passWord) {
        if(checkUserName(user)) return false;
        String sql = "INSERT INTO Users(username, password) VALUES(?, ?)";
        try(Connection conn = DBConnection.getConnection(); 
        PreparedStatement psmt = conn.prepareStatement(sql)) {
            psmt.setString(1, user);
            psmt.setString(2, passWord);
            psmt.executeUpdate();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkUserName(String user) {
        String sql = "SELECT id FROM Users WHERE username = user";
        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return true;
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }
}
