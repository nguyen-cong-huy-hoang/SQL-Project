package vn.MovieManagement.enums.Movie;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


public enum MovieTypeText implements IMovieType{
    DESCRIPTION("Description"),
    LINK("Link"),
    DURATION("Duration");


    private final String column;
    MovieTypeText(String column) {
        this.column = column;
    }
    public String getColumn() {
        return this.column;
    }

    public void bind(PreparedStatement stmt, int idx, Object value) throws SQLException {
        if(value == null) {
            stmt.setNull(idx, Types.VARCHAR);
        } else {
            stmt.setString(idx, (String) value);
        }
    }
}
