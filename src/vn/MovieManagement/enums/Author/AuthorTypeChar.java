package vn.MovieManagement.enums.Author;

import java.sql.PreparedStatement;
import java.sql.SQLException;


import vn.MovieManagement.util.StringFormat;

public enum AuthorTypeChar implements IAuthorType{

    NAME("Name"),
    COUNTRY("Country");

    private final String column;
    AuthorTypeChar(String column) {
        this.column = column;
    }

    public String getColumn () {
        return this.column;
    }

    public void bind(PreparedStatement stmt, int idx, Object value) throws SQLException {
        if (value == null) {
            throw new IllegalArgumentException("value of column can not be null");
        } else {
            String v = (String) value;
            if (!StringFormat.stringLimit(30, v))
                throw new IllegalArgumentException("Too long");
            stmt.setString(idx, v);
        }
    }
    
}
