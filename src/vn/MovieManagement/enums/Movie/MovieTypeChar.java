package vn.MovieManagement.enums.Movie;

import java.sql.PreparedStatement;

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

    public void bind(PreparedStatement stmt, int idx, Object value) {
        if(value == null) {
            
        }
    }
}
