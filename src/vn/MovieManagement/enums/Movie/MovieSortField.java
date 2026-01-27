package vn.MovieManagement.enums.Movie;

public enum MovieSortField {
        NAME("Name"),
        DATE("Date"),
        DURATION("Duration");

        private final String column;
        MovieSortField(String column) {
            this.column = column;
        }
        public String getColumn() {
            return column;
        }
    }