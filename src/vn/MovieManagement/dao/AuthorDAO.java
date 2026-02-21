package vn.MovieManagement.dao;

import vn.MovieManagement.enums.Author.*;
import vn.MovieManagement.model.Movie;
import vn.MovieManagement.model.author;
import vn.MovieManagement.util.DBConnection;
import vn.MovieManagement.util.StringFormat;


import java.sql.*;
import java.util.ArrayList;

public class AuthorDAO {
    private AuthorDAO(){};
    
    public static boolean createTable() {
        String authors = "CREATE TABLE IF NOT EXISTS Authors (" +
                         "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                         "Name NCHAR(30) NOT NULL," +
                         "Description TEXT," +
                         "Age INTEGER," +
                         "Country NCHAR(30) NOT NULL," +
                         "User_ID INTEGER," +
                         "FOREIGN KEY (User_ID) REFERENCES Users(id))";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(authors);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<author> Select(int User_ID) {
        String sql = "SELECT * FROM Authors WHERE User_ID = ?";
        ArrayList<author> au = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, User_ID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                author a = new author(
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getInt("age"),
                rs.getString("country"),
                rs.getInt("id"),
                rs.getInt("User_ID")
                );
                au.add(a);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return au;    
    }

    public static boolean addAuthor(String name, String description,
                                    int age, String country, int User_ID) {

        if(StringFormat.stringLimit(30, name) == false || 
            StringFormat.stringLimit(30, country) == false) return false;
        String sql = "INSERT INTO Authors(Name, Description, Age, Country, User_ID) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            if (description == null || description.isEmpty()) {
                stmt.setNull(2, Types.VARCHAR);
            } else {
                stmt.setString(2, description);
            }

            // Age
            if (age <= 0) {
                stmt.setNull(3, Types.INTEGER);
            } else {
                stmt.setInt(3, age);
            }

            stmt.setString(4, country);
            stmt.setInt(5, User_ID);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static ArrayList<author> find(String name, int User_ID) {
        String sql = "SELECT * FROM Authors WHERE Name LIKE ? AND User_ID = ?";
        ArrayList<author> au = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            stmt.setInt(2, User_ID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                author a = new author(
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getInt("age"),
                rs.getString("country"),
                rs.getInt("id"),
                rs.getInt("User_ID")
                );
                au.add(a);
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return au;
    }

    public static void remove(int id) {
        String sql = "DELETE FROM Authors WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, id);
           stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clear(int User_ID) {
        String sql = "DELETE FROM Authors WHERE User_ID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, User_ID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }

    public static ArrayList<author> sort(int UserID, AuthorSortField field, boolean desc) {
        String sql = "SELECT * FROM Authors WHERE User_ID = ? ORDER BY " + field.getColumn() + (desc ? " DESC" : " ASC");
        ArrayList<author> au = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, UserID);
           ResultSet rs = stmt.executeQuery();
           while(rs.next()) {
                author a = new author(
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getInt("age"),
                rs.getString("country"),
                rs.getInt("id"),
                rs.getInt("User_ID")
                );
                au.add(a);
           }
        } catch(SQLException e) {
            e.printStackTrace();
        }       
        return au;
    }


    public static boolean update( int userId, int id, IAuthorType field, Object value ) {
        String sql = "UPDATE Authors SET " + field.getColumn()
               + " = ? WHERE User_ID = ? AND id = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {  
            field.bind(stmt, 1, value);
            stmt.setInt(2, userId);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }


}
