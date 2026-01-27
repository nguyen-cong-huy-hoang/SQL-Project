package vn.MovieManagement.enums.Movie;

public enum MovieTypeInteger {
    USER_ID("User_ID"),
    ID("id");

    private final String column;
    MovieTypeInteger(String column) {
        this.column = column;
    }

    public String getColumn() {
        return this.column;
    }
}
