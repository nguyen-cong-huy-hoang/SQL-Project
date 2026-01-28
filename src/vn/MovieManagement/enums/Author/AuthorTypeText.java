package vn.MovieManagement.enums.Author;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;


public enum AuthorTypeText implements IAuthorType {
    DESCRIPTION("Description");

    private final String column;
    AuthorTypeText(String column) {
        this.column = column;
    }

    public String getColumn () {
        return this.column;
    }
    
    public void bind(PreparedStatement stmt, int idx, Object value) throws SQLException {
        if (value == null) {
            stmt.setNull(idx, Types.VARCHAR);
        } else {
            stmt.setString(idx, (String)value);
        }
    }
}