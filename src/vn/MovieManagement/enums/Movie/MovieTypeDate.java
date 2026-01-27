package vn.MovieManagement.enums.Movie;

public enum MovieTypeDate {
    DATE("Date");    

    private final String column;
    MovieTypeDate(String column) {
        this.column = column;
    }
    public String getColumn() {
        return this.column;
    }
}
