package vn.MovieManagement.enums.Movie;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;



public enum MovieTypeInteger implements IMovieType{
    USER_ID("User_ID");

    private final String column;
    MovieTypeInteger(String column) {
        this.column = column;
    }

    public String getColumn() {
        return this.column;
    }

     public void bind(PreparedStatement stmt, int idx, Object value) throws SQLException {
        if(value == null && this == MovieTypeInteger.USER_ID) {
            throw new IllegalArgumentException("value of column can not be null");
        } else if (value == null) {
            stmt.setNull(idx, Types.INTEGER);
        } else {
            stmt.setInt(idx, (int) value);
        }
    }
}
