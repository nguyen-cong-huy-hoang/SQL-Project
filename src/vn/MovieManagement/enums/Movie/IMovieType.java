package vn.MovieManagement.enums.Movie;

import java.sql.PreparedStatement;
import java.sql.SQLException;
public interface IMovieType {
    String getColumn();
    void bind(PreparedStatement stmt, int idx, Object value) throws SQLException;
}
