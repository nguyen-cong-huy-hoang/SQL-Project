package vn.MovieManagement.dao;

import vn.MovieManagement.util.*;
import vn.MovieManagement.model.Movie;
import java.sql.*;
import java.util.ArrayList;
import vn.MovieManagement.enums.Movie.IMovieType;
import vn.MovieManagement.enums.Movie.MovieSortField;

public class MovieDAO {
    
    public static void createTable() {
        String movies = "CREATE TABLE IF NOT EXISTS Movies (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Name NCHAR(30) NOT NULL," +
                        "Description TEXT," +
                        "Link TEXT," +
                        "Duration TEXT," +
                        "Date DATE," +
                        "Code CHAR(15)," +
                        "User_ID INTEGER NOT NULL," +
                        "FOREIGN KEY (User_ID) REFERENCES Users(id))";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(movies);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Movie> Select(int User_ID) {
        String sql = "SELECT * FROM Movies WHERE User_ID = ?";
        ArrayList<Movie> mv = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, User_ID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date d = rs.getDate("Date");
                String dateStr = (d == null) ? null : d.toString();

                Movie m = new Movie(
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("Link"),
                rs.getString("Duration"),
                dateStr,
                rs.getString("Code"),
                rs.getInt("id"),
                rs.getInt("User_ID")
                );
                mv.add(m);
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mv;
    }

     public static boolean addMovies(String name, String description, String link,
                                    String duration, String date, String code, int User_ID) {

        if(StringFormat.stringLimit(30, name) == false || 
            StringFormat.stringLimit(15, code) == false) return false;
        String sql = "INSERT INTO Movies(Name, Description, Link, Duration, Date, Code, User_ID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            if (description == null || description.isEmpty()) {
                stmt.setNull(2, Types.VARCHAR);
            } else {
                stmt.setString(2, description);
            }
            if (link == null || link.isEmpty()) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, link);
            }
            if (duration == null || duration.isEmpty()) {
                stmt.setNull(4, Types.VARCHAR);
            } else {
                stmt.setString(4, duration);
            }
            if (date == null || date.isEmpty()) {
                stmt.setNull(5, Types.DATE);
            } else {
                stmt.setDate(5, StringFormat.toSqlDate(date)); 
            }
            stmt.setString(6, code);
            stmt.setInt(7,User_ID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Movie> find(String name, int UserID) {
        String sql = "SELECT * FROM MOVIES WHERE Name LIKE ? AND User_ID = ?";
        ArrayList<Movie> mv = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            stmt.setInt(2, UserID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Date d = rs.getDate("Date");
                String dateStr = (d == null) ? null : d.toString();

                Movie m = new Movie(
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("Link"),
                rs.getString("Duration"),
                dateStr,
                rs.getString("Code"),
                rs.getInt("id"),
                rs.getInt("User_ID")
                );
                mv.add(m);
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return mv;
    }

    public static void remove(int id) {
        String sql = "DELETE FROM Movies WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, id);
           stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clear(int User_ID) {
        String sql = "DELETE FROM Movies WHERE User_ID = ?";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) { 
            stmt.setInt(1, User_ID);
            stmt.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }


    public static ArrayList<Movie> sort(int UserID, MovieSortField field, boolean desc) {
        String sql = "SELECT * FROM Movies WHERE User_ID = ? ORDER BY " + field.getColumn() + (desc ? " DESC" : " ASC");
        ArrayList<Movie> mv = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, UserID);
           ResultSet rs = stmt.executeQuery();
           while(rs.next()) {
                Date d = rs.getDate("Date");
                String dateStr = (d == null) ? null : d.toString();

                Movie m = new Movie(
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("Link"),
                rs.getString("Duration"),
                dateStr,
                rs.getString("Code"),
                rs.getInt("id"),
                rs.getInt("User_ID")
                );
                mv.add(m);
           }
        } catch(SQLException e) {
            e.printStackTrace();
        }       
        return mv;
    }

    public static boolean update (int userId, int id, IMovieType field, String value) {
        String sql = "UPDATE Movies SET " 
                + field.getColumn()
                + " = ? WHERE User_ID = ? AND id = ?";

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            field.bind(stmt, 1, value);
            stmt.setInt(2, userId);
            stmt.setInt(3, id);

            stmt.executeUpdate();

        } catch (SQLException | IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}