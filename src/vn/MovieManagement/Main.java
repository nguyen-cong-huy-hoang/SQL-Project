package vn.MovieManagement;

import vn.MovieManagement.dao.*;
import vn.MovieManagement.enums.Movie.*;
import vn.MovieManagement.model.*;
import vn.MovieManagement.service.IMovieManagement;
import vn.MovieManagement.service.MovieManagement;
import vn.MovieManagement.util.*;

public class Main {
    private static User user = null;
    private static IMovieManagement mv = new MovieManagement();

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

            int choice = InputUtils.inputInteger("Choose function: ", 0, 2);

            switch (choice) {
                case 1:
                    menuMovies();
                    break;
                case 2:
                    System.out.println("Feature Author Management coming soon...");
                    break;
                case 0:
                    System.out.println("Logging out...");
                    user = null;
                    main(null);
                    return;
            }
        }
    }

 
    private static void reloadMoviesData() {
        mv.clear(); 
        mv.add(MovieDAO.Select(user.getID())); 
    }

    private static void menuMovies() {
        clearScreen();
        reloadMoviesData(); 
        while (true) {
            System.out.println("\n====== MOVIE MENU ======");
            System.out.println("1. Print all");
            System.out.println("2. Add a new movie");
            System.out.println("3. Delete a movie");
            System.out.println("4. Find");
            System.out.println("5. Sort");
            System.out.println("6. Update");
            System.out.println("0. Exit to Main Menu");
            System.out.println("==========================\n");
            
            mv.print(); 
            int choose = InputUtils.inputInteger("Please choose an option: ", 0, 6);

            switch (choose) {
                case 1:
                    clearScreen();
                    reloadMoviesData(); 
                    break;
                case 2:
                    addMovie(); 
                    break;
                case 3:
                    int stt = InputUtils.inputIntegeroptional("Enter STT of the movie to delete (Press Enter to cancel): ");
                    if (stt != -1) {
                        int dbId = mv.getIdFromStt(stt);
                        if (dbId != -1) {
                            MovieDAO.remove(dbId); 
                            reloadMoviesData();    
                            System.out.println(">> Deleted successfully!");
                        } else {
                            System.out.println(">> Error: Invalid STT!");
                        }
                    }
                    break;
                case 4:
                    clearScreen();
                    String name = InputUtils.inputStringOptional("Enter movie name to find: ");
                    if (name != null) {
                        mv.clear();
                        mv.add(MovieDAO.find(name, user.getID())); 
                    }
                    break; 
                case 5:
                    sortMovie();
                    break;
                case 6:
                    updateMovie(); 
                    break;
                case 0:
                    clearScreen();
                    return; 
            }
        }
    }

    private static void addMovie() {
        System.out.println("--- ADD MOVIE (Press Enter to skip optional fields) ---");
        String name = InputUtils.inputString("Name (Required): ");
        clearPreviousLine();
        
        String description = InputUtils.inputStringOptional("Description: ");
        clearPreviousLine();
        
        String link = InputUtils.inputStringOptional("Link: ");
        clearPreviousLine();
        
        String duration = InputUtils.inputStringOptional("Duration: ");
        clearPreviousLine();
        
        String date = InputUtils.inputStringToDate("Date (yyyy-mm-dd): ");
        clearPreviousLine();
        
        String code = InputUtils.inputStringOptional("Code: ");
        
        if (!MovieDAO.addMovies(name, description, link, duration, date, code, user.getID())) {
            System.out.println(">> Adding a new movie failed!");
        } else {
            System.out.println(">> Added successfully!");
        }
        reloadMoviesData(); 
    }

    private static void sortMovie() {
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
        switch (option) {
            case 1:
                mv.add(MovieDAO.sort(user.getID(), MovieSortField.NAME, desc));
                break;
            case 2:
                mv.add(MovieDAO.sort(user.getID(), MovieSortField.DURATION, desc));
                break;
            case 3:
                mv.add(MovieDAO.sort(user.getID(), MovieSortField.DATE, desc));
                break;
        }
    }

    private static void updateMovie() {
        if (mv.Size() == 0) {
            System.out.println(">> No movies to update!");
            return;
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

        if (success) {
            System.out.println(">> Updated successfully!");
        } else {
            System.out.println(">> Update failed!");
        }
        
        reloadMoviesData(); 
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void clearPreviousLine() {
        System.out.print("\033[1A\033[2K");
        System.out.flush();
    }
}