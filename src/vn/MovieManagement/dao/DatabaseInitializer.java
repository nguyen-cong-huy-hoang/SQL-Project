package vn.MovieManagement.dao;

public class DatabaseInitializer {

    private DatabaseInitializer(){};

    public static void init() {
        UserDAO.createTable();
        AuthorDAO.createTable();
        MovieDAO.createTable();
    }

}
