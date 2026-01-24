package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import java.sql.*;

import org.w3c.dom.UserDataHandler;


public class UserDAO {

    private UserDAO(){};

    public static boolean createTable() {
        String users = "CREATE TABLE IF NOT EXISTS Users (" +
                       "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                       "username NCHAR(15) NOT NULL," +
                       "password CHAR(100))";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(users);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static int checkLogin(String user, String passWord) {
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

    public static boolean register(String user, String passWord) {
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

    public static boolean checkUserName(String user) {
        String sql = "SELECT id FROM Users WHERE username = ?";
    
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement psmt = conn.prepareStatement(sql)) { 
            psmt.setString(1, user); 
            ResultSet rs = psmt.executeQuery();
            if (rs.next()) {
                return true; 
            }
        } catch (SQLException e) {
            System.err.println("Loi checkUser: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}