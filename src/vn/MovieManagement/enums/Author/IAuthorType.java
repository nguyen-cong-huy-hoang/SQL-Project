package vn.MovieManagement.enums.Author;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface IAuthorType {
    String getColumn();
    void bind(PreparedStatement stmt, int index, Object value) throws SQLException;
} 
