package vn.MovieManagement.enums.Author;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public enum AuthorTypeInteger implements IAuthorType{
    USER_ID("User_ID"),
    AGE("Age");
    private final String column;
    AuthorTypeInteger(String column) {
        this.column = column;
    }

    public String getColumn () {
        return this.column;
    }

    public void bind(PreparedStatement stmt, int idx, Object value) throws SQLException {
        if (value == null) {
            stmt.setNull(idx, Types.INTEGER);
        } else {
            stmt.setInt(idx, (int)value);
        }
    }
    
}
