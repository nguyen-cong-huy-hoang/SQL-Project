package vn.MovieManagement.enums.Movie;

public enum MovieTypeText {
    DESCRIPTION("Description"),
    LINK("Link"),
    DURATION("Duration");


    private final String column;
    MovieTypeText(String column) {
        this.column = column;
    }
    public String getColumn() {
        return this.column;
    }
}
