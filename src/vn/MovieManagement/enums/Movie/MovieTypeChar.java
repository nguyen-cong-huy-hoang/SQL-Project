package vn.MovieManagement.enums.Movie;

public enum MovieTypeChar {
    NAME("Name"),
    CODE("Code");

    private final String column;
    MovieTypeChar(String column) {
        this.column = column;
    }

    public String getColumn() {
        return this.column;
    }


}
