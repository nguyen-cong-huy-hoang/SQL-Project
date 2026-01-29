package vn.MovieManagement.enums.Movie;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import vn.MovieManagement.util.StringFormat;

public enum MovieTypeChar implements IMovieType{
    NAME("Name"),
    CODE("Code");

    private final String column;
    MovieTypeChar(String column) {
        this.column = column;
    }

    public String getColumn() {
        return this.column;
    }

    public void bind(PreparedStatement stmt, int idx, Object value) throws SQLException {
        if(value == null && this == MovieTypeChar.NAME) {
            throw new IllegalArgumentException("value of column can not be null");
        } else if(value == null) {
            stmt.setNull(idx, Types.VARCHAR);
        } else {
            String v = (String) value;
            if (!StringFormat.stringLimit(15, v))
                throw new IllegalArgumentException("Too long");
            stmt.setString(idx, v);
        }
    }
}
