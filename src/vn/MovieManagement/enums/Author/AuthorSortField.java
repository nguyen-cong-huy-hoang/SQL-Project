package vn.MovieManagement.enums.Author;



public enum AuthorSortField {
    AGE("Age"),
    NAME("Name"),
    COUNTRY("Country");

    private final String column;
    AuthorSortField(String column) {
        this.column = column;
    }

    public String getColumn() {
        return this.column;
    }
}
