package vn.MovieManagement.enums.Movie;

public enum MovieTypeInteger implements IMovieType{
    USER_ID("User_ID");

    private final String column;
    MovieTypeInteger(String column) {
        this.column = column;
    }

    public String getColumn() {
        return this.column;
    }
}
