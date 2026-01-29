package vn.MovieManagement.enums.Movie;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import vn.MovieManagement.util.StringFormat;

public enum MovieTypeDate implements IMovieType{
    DATE("Date");    

    private final String column;
    MovieTypeDate(String column) {
        this.column = column;
    }


    public String getColumn() {
        return this.column;
    }

     public void bind(PreparedStatement stmt, int idx, Object value) throws SQLException {
        if(value == null) {
            stmt.setNull(idx, Types.DATE);
        } else {
            String v = (String) value;
            if(StringFormat.isValidDate(v) == false) {
                throw new IllegalArgumentException("Invalid date");
            }
            stmt.setDate(idx, StringFormat.toSqlDate(v));
        }
    }
}
