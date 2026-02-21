package vn.MovieManagement;

import vn.MovieManagement.dao.*;
import vn.MovieManagement.enums.Author.AuthorSortField;
import vn.MovieManagement.enums.Author.AuthorTypeChar;
import vn.MovieManagement.enums.Author.AuthorTypeInteger;
import vn.MovieManagement.enums.Author.AuthorTypeText;
import vn.MovieManagement.enums.Movie.*;
import vn.MovieManagement.model.*;
import vn.MovieManagement.service.AuthorManagement;
import vn.MovieManagement.service.IAuthorManagement;
import vn.MovieManagement.service.IMovieManagement;
import vn.MovieManagement.service.MovieManagement;
import vn.MovieManagement.util.*;

public class Main {
    private static User user = null;
    private static IMovieManagement mv = new MovieManagement();
    private static IAuthorManagement au = new AuthorManagement();
    public static void main(String[] args) {
        DatabaseInitializer.init();

        while (user == null) {
            System.out.println("\n====== WELCOME TO MOVIE APP ======");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit");

            int choice = InputUtils.inputInteger("Please choose an option: ", 0, 2);

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    System.exit(0);
            }
        }
        clearScreen();
        System.out.println("\nLogin successfully! Welcome, " + user.getUser()); 
        showMainMenu();
    }

    private static void login() {
        int failCount = 0;
        while (true) {
            clearScreen();
            System.out.println("---------- LOGIN ----------");

            String name = InputUtils.inputString("Username: ");
            String pass = InputUtils.inputString("Password: ");

            int userId = UserDAO.checkLogin(name, pass);
            if (userId != -1) {
                user = new User(name, pass, userId);
                return;
            } else {
                System.out.println(">> Error: Incorrect username or password!");
                failCount++;

                if (failCount >= 3) {
                    String ans = InputUtils.inputString("Failed 3 times. Do you want to Register instead? (y/n): ");
                    if (ans.equalsIgnoreCase("y")) {
                        register();
                        return;
                    }
                    failCount = 0;
                }
            }
        }
    }

    private static void register() {
        int failCount = 0;
        while (true) {
            clearScreen();
            System.out.println("---------- REGISTER ----------");

            String name = InputUtils.inputString("New Username: ");
            String pass = InputUtils.inputString("New Password: ");

            boolean success = UserDAO.register(name, pass);

            if (success) {
                System.out.println(">> Register successful! Logging you in...");
                int userId = UserDAO.checkLogin(name, pass);
                user = new User(name, pass, userId);
                return;
            } else {
                System.out.println(">> Error: Username already exists!");
                failCount++;

                if (failCount >= 3) {
                    String ans = InputUtils.inputString("Username taken often. Do you want to Login? (y/n): ");
                    if (ans.equalsIgnoreCase("y")) {
                        login();
                        return;
                    }
                    failCount = 0;
                }
            }
        }
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n====== MAIN MENU ======");
            System.out.println("1. Movie Management");
            System.out.println("2. Author Management");
            System.out.println("0. Logout");

            int choice = InputUtils.inputInteger("Please choose an option: ", 0, 2);

            switch (choice) {
                case 1:
                    menuMovies();
                    break;
                case 2:
                    menuAuthors();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    clearScreen();
                    user = null;
                    main(null);
                    return;
            }
        }
    }


private static void menuMovies() {
        clearScreen();
        reloadMoviesData(); 
        String message = ""; 
        
        while (true) {
            clearScreen(); 
            System.out.println("\n====== MOVIE MENU ======");
            System.out.println("1. Print all");
            System.out.println("2. Add a new movie");
            System.out.println("3. Delete a movie");
            System.out.println("4. Find");
            System.out.println("5. Sort");
            System.out.println("6. Update");
            System.out.println("0. Exit to Main Menu");
            System.out.println("==========================\n");
            
            System.out.println(message + "\n \n"); 
            mv.print(); 
            
            int choose = InputUtils.inputInteger("Please choose an option: ", 0, 6);

            switch (choose) {
                case 1:
                    message = ""; 
                    reloadMoviesData(); 
                    break;
                case 2:
                    message = addMovie(); 
                    break;
                case 3:
                    message = deleteMovie(); 
                    break;
                case 4:
                    message = findMovie();   
                    break;
                case 5:
                    message = sortMovie();
                    break;
                case 6:
                    clearScreen();
                    reloadMoviesData();
                    mv.print();
                    message = updateMovie(); 
                    break;
                case 0:
                    clearScreen();
                    return; 
            }
        }
    }

    private static String addMovie() {
        clearScreen();
        System.out.println("--- ADD MOVIE (Press Enter to skip optional fields) ---");
        String name = InputUtils.inputString("Name (Required): ");
        clearScreen();
        
        String description = InputUtils.inputStringOptional("Description: ");
        clearScreen();
        
        String link = InputUtils.inputStringOptional("Link: ");
        clearScreen();
        
        String duration = InputUtils.inputStringOptional("Duration: ");
        clearScreen();
        
        String date = InputUtils.inputStringToDate("Date (yyyy-mm-dd): ");
        clearScreen();
        
        String code = InputUtils.inputStringOptional("Code: ");
        boolean check = MovieDAO.addMovies(name, description, link, duration, date, code, user.getID());
        reloadMoviesData();
        if (!check) {
            return ">> Adding a new movie failed!";
        } else {
            return ">> Added successfully!";
        }
    }

    private static String deleteMovie() {
        int stt = InputUtils.inputIntegeroptional("Enter STT of the movie to delete (Press Enter to cancel): ");
        if (stt != -1) {
            int dbId = mv.getIdFromStt(stt);
            if (dbId != -1) {
                MovieDAO.remove(dbId); 
                reloadMoviesData();    
                return ">> Deleted successfully!";
            } else {
                return ">> Error: Invalid STT!";
            }
        }
        return "";
    }

    private static String findMovie() {
        clearScreen();
        String name = InputUtils.inputStringOptional("Enter movie name to find: ");
        if (name != null) {
            mv.clear();
            mv.add(MovieDAO.find(name, user.getID())); 
            return ">> Search results!";
        }
        return "";
    }

    private static String sortMovie() {
        clearScreen();
        System.out.println("1. Name");
        System.out.println("2. Duration");
        System.out.println("3. Date");
        int option = InputUtils.inputInteger("Sort by: ", 1, 3);
        
        String ans = InputUtils.inputString("Sort in ascending order? (y/n) : ");
        boolean desc = true; 
        if (ans.equalsIgnoreCase("y")) {
            desc = false; 
        }

        mv.clear(); 
        String orderText = desc ? "descending" : "ascending";

        switch (option) {
            case 1:
                mv.add(MovieDAO.sort(user.getID(), MovieSortField.NAME, desc));
                return ">> Sort by name in " + orderText + " order!";
            case 2:
                mv.add(MovieDAO.sort(user.getID(), MovieSortField.DURATION, desc));
                return ">> Sort by duration in " + orderText + " order!";
            case 3:
                mv.add(MovieDAO.sort(user.getID(), MovieSortField.DATE, desc));
                return ">> Sort by date in " + orderText + " order!";
            default:
                return ">> Sort failed!";
        }
    }

    private static String updateMovie() {
        if (mv.Size() == 0) {
            return ">> No movies to update!";
        }

        int stt = InputUtils.inputInteger("Select the STT of the movie to edit: ", 1, mv.Size());
        int dbId = mv.getIdFromStt(stt);

        System.out.println("1. Name\n2. Duration\n3. Code\n4. Date\n5. Link\n6. Description");
        int choose = InputUtils.inputInteger("Select the column to change: ", 1, 6);

        boolean success = false;
        switch (choose) {
            case 1:
                String name = InputUtils.inputString("Enter new name: ");
                success = MovieDAO.update(user.getID(), dbId, MovieTypeChar.NAME, name);
                break;
            case 2:
                String duration = InputUtils.inputStringOptional("Enter new duration: ");
                success = MovieDAO.update(user.getID(), dbId, MovieTypeText.DURATION, duration);
                break;
            case 3:
                String code = InputUtils.inputStringOptional("Enter new code: ");
                success = MovieDAO.update(user.getID(), dbId, MovieTypeChar.CODE, code);
                break;
            case 4:
                String date = InputUtils.inputStringToDate("Enter new date (yyyy-mm-dd): ");
                success = MovieDAO.update(user.getID(), dbId, MovieTypeDate.DATE, date);
                break;
            case 5:
                String link = InputUtils.inputStringOptional("Enter new link: ");
                success = MovieDAO.update(user.getID(), dbId, MovieTypeText.LINK, link);
                break;
            case 6:
                String description = InputUtils.inputStringOptional("Enter new description: ");
                success = MovieDAO.update(user.getID(), dbId, MovieTypeText.DESCRIPTION, description);
                break;
        }

        reloadMoviesData(); 
        if (success) {
            return ">> Updated successfully!";
        } else {
            return ">> Update failed!";
        }
    }

    private static void menuAuthors() {
        clearScreen();
        reloadAuthorsData(); 
        String message  = "";
        while (true) {
            clearScreen();
            System.out.println("\n====== AUTHOR MENU ======");
            System.out.println("1. Print all");
            System.out.println("2. Add a new author");
            System.out.println("3. Delete an author");
            System.out.println("4. Find");
            System.out.println("5. Sort");
            System.out.println("6. Update");
            System.out.println("0. Exit to Main Menu");
            System.out.println("==========================\n");
            
            System.out.println(message + "\n \n");
            au.print(); 
            int choose = InputUtils.inputInteger("Please choose an option: ", 0, 6);

            switch (choose) {
                case 1:
                    message = "";
                    reloadAuthorsData();
                    break;
                case 2:
                    message = addAuthor();
                    break;
                case 3:
                    message = deleteAuthor();
                    break;
                case 4:
                    message = findAuthor();
                    break;
                case 5:
                    message = sortAuthor();
                    break;
                case 6:
                    clearScreen();
                    reloadAuthorsData();
                    au.print();
                    message = updateAuthor();
                    break;
                case 0:
                    return; 
            }
        }
    }

    private static String addAuthor() {
        clearScreen();
        System.out.println("--- ADD AUTHOR (Press Enter to skip optional fields) ---");
        String name = InputUtils.inputString("Name (Required): ");
        clearScreen();
        
        String description = InputUtils.inputStringOptional("Description: ");
        clearScreen();
        
        int age = InputUtils.inputInteger("Age (Enter -1 to skip): "); 
        clearScreen();
        
        String country = InputUtils.inputString("Country (Required): ");
        clearScreen();
        boolean check = AuthorDAO.addAuthor(name, description, age, country, user.getID());
        reloadAuthorsData();
        if (!check) {
            return ">> Adding a new author failed!";
        } else {
            return ">> Added successfully!";
        }
    }

    private static String deleteAuthor() {
        int stt = InputUtils.inputIntegeroptional("Enter STT of the author to delete (Press Enter to cancel): ");
        if (stt != -1) {
            int dbId = au.getIdFromStt(stt); 
                if (dbId != -1) {
                    AuthorDAO.remove(dbId); 
                    reloadAuthorsData();    
                    return">> Deleted successfully!";
            } else {
                return ">> Error: Invalid STT!";
            }
        }
        return "";
    }

    private static String findAuthor() {
        clearScreen();
        String name = InputUtils.inputStringOptional("Enter author name to find: ");
        if (name != null) {
            au.clear();
            au.add(AuthorDAO.find(name, user.getID())); 
            return ">> search results!";
        }
        return "";
    }

    private static String sortAuthor() {
        clearScreen();
        System.out.println("1. Name");
        System.out.println("2. Age");
        System.out.println("3. Country");
        int option = InputUtils.inputInteger("Sort by: ", 1, 3);
        
        String ans = InputUtils.inputString("Sort in ascending order? (y/n) : ");
        boolean desc = true; 
        if (ans.equalsIgnoreCase("y")) {
            desc = false; 
        }
        au.clear(); 
        String orderText = desc ? "descending" : "ascending"; 

        switch (option) {
            case 1:
                au.add(AuthorDAO.sort(user.getID(), AuthorSortField.NAME, desc));   
                return ">> Sort by name in " + orderText + " order!";
            case 2:
                au.add(AuthorDAO.sort(user.getID(), AuthorSortField.AGE, desc));       
                return ">> Sort by age in " + orderText + " order!";
            case 3:
                au.add(AuthorDAO.sort(user.getID(), AuthorSortField.COUNTRY, desc));       
                return ">> Sort by country in " + orderText + " order!";
            default:
                return ">> Sort failed!"; 
        }
    }

    private static String updateAuthor() {
        if (au.Size() == 0) {
            return ">> No authors to update!";
        }

        int stt = InputUtils.inputInteger("Select the STT of the author to edit: ", 1, au.Size());
        int dbId = au.getIdFromStt(stt);

        System.out.println("1. Name\n2. Description\n3. Age\n4. Country");
        int choose = InputUtils.inputInteger("Select the column to change: ", 1, 4);

        boolean success = false;
        switch (choose) {
            case 1:
                String name = InputUtils.inputString("Enter new name: ");
                success = AuthorDAO.update(user.getID(), dbId, AuthorTypeChar.NAME, name);
                break;
            case 2:
                String description = InputUtils.inputStringOptional("Enter new description: ");
                success = AuthorDAO.update(user.getID(), dbId, AuthorTypeText.DESCRIPTION, description);
                break;
            case 3:
                int age = InputUtils.inputInteger("Enter new age: ");
                success = AuthorDAO.update(user.getID(), dbId, AuthorTypeInteger.AGE, age);
                break;
            case 4:
                String country = InputUtils.inputString("Enter new country: ");
                success = AuthorDAO.update(user.getID(), dbId, AuthorTypeChar.COUNTRY, country);
                break;
        }
        reloadAuthorsData(); 
        if (success) {
            return ">> Updated successfully!";
        } else {
            return ">> Update failed!";
        }
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearPreviousLine() {
        System.out.print("\033[1A\033[2K");
        System.out.flush();
    }

    private static void reloadAuthorsData() {
        au.clear(); 
        au.add(AuthorDAO.Select(user.getID())); 
    }
    private static void reloadMoviesData() {
        mv.clear(); 
        mv.add(MovieDAO.Select(user.getID())); 
    }
}