package vn.MovieManagement.enums.Movie;

import java.sql.PreparedStatement;

public interface IMovieType {
    String getColumn();
    void bind(PreparedStatement stmt, int idx, Object value);
}
