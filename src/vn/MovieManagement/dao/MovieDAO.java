package vn.MovieManagement.dao;

import vn.MovieManagement.util.DBConnection;
import vn.MovieManagement.model.Movie;
import java.sql.*;
import java.util.ArrayList;

public class MovieDAO {
    enum SortField {
        NAME("Name"),
        DATE("Date"),
        DURATION("Duration");

        private final String column;
        SortField(String column) {
            this.column = column;
        }
        public String getColumn() {
            return column;
        }
    }
    public static void createTable() {
        String movies = "CREATE TABLE IF NOT EXISTS Movies (" +
                        "id INTEGER AUTOINCREMENT," +
                        "Name NCHAR(15) NOT NULL," +
                        "Description TEXT," +
                        "Link TEXT," +
                        "Duration TEXT," +
                        "Date DATE," +
                        "Code CHAR(10) PRIMARY KEY," +
                        "User_ID INTEGER," +
                        "FOREIGN KEY (User_ID) REFERENCES Users(id))";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(movies);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

     public static void addMovies(String name, String description, String link,
                                    String duration, String date, String code, int id, int User_ID) {

        String sql = "INSERT INTO Movies(id, Name, Description, Link, Duration, Date, Code, User_ID) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.setString(2, name);

            if (description == null || description.isEmpty()) {
                stmt.setNull(3, Types.VARCHAR);
            } else {
                stmt.setString(3, description);
            }
            if (link == null || link.isEmpty()) {
                stmt.setNull(4, Types.VARCHAR);
            } else {
                stmt.setString(4, link);
            }
            if (duration == null || duration.isEmpty()) {
                stmt.setNull(5, Types.VARCHAR);
            } else {
                stmt.setString(5, duration);
            }
            if (date == null || date.isEmpty()) {
                stmt.setNull(6, Types.DATE);
            } else {
                stmt.setDate(6, Date.valueOf(date)); 
            }

            stmt.setString(7, code);
            stmt.setInt(8,User_ID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Movie> find(String name, int UserID) {
        String sql = "SELECT * FROM MOVIES WHERE Name LIKE ? AND UserID = ?";
        ArrayList<Movie> mv = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + name + "%");
            stmt.setInt(2, UserID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Movie m = new Movie(
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("Link"),
                rs.getInt("AuthorNumber"),
                rs.getString("Duration"),
                rs.getString("Date"),
                rs.getString("Code"),
                rs.getInt("id"),
                rs.getInt("UserID")
                );
                mv.add(m);
            } 
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
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

    public static ArrayList<Movie> sort(int UserID, SortField field, boolean desc) {
        String sql = "SELECT * FROM Movies WHERE UserID = ? ORDER BY " + field.getColumn() + (desc ? " DESC" : " ASC");
        ArrayList<Movie> mv = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
           stmt.setInt(1, UserID);
           ResultSet rs = stmt.executeQuery();
           while(rs.next()) {
                Movie m = new Movie(
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("Link"),
                rs.getInt("AuthorNumber"),
                rs.getString("Duration"),
                rs.getString("Date"),
                rs.getString("Code"),
                rs.getInt("id"),
                rs.getInt("UserID")
                );
                mv.add(m);
           }
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }       
        return mv;
    }



}