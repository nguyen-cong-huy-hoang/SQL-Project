package vn.MovieManagement;

import static org.mockito.ArgumentMatchers.isNull;

import java.util.ArrayList;

import vn.MovieManagement.dao.*;
import vn.MovieManagement.enums.Movie.MovieSortField;
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

        System.out.println("\nLogin successfully! Welcome, " + user.getUser());
        
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
                    // Gọi hàm menuMovies() bạn đã viết
                    // menuMovies(); 
                    System.out.println("Feature Movie Management coming soon...");
                    break;
                case 2:
                    // Gọi hàm menuAuthors() bạn đã viết
                    // menuAuthors();
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

    private static void menuMovies() {
        mv.add(MovieDAO.Select(user.getID()));
        while(true) {
            System.out.println("\n====== MOVIE MENU ======");
            System.out.println("1. print all");
            System.out.println("2. add a new movie");
            System.out.println("3. delete a movie");
            System.out.println("4. find");
            System.out.println("5. sort");
            System.out.println("6. update");
            System.out.println("0. exit");
            
            System.out.println("========================== \n \n");
            mv.print();
            int choose = InputUtils.inputInteger("Please choose an option: ", 0, 6);
            
            switch (choose) {
                case 1:
                    mv.add(MovieDAO.Select(user.getID()));                
                    break;
                case 2:
                    addMoive();
                    break;
                case 3:
                    int input = InputUtils.inputIntegeroptional("Please enter the ID of the movie you wish to delete: ");
                    System.out.println("If you don't want to type, just press enter");
                    if(input != -1) {
                        MovieDAO.remove(input);
                        mv.remove(input);
                    }
                    break; 
                case 4:
                    String name = InputUtils.inputStringOptional("Please enter the name of the movie you are looking for: ");
                    if(name != null) {
                        mv.add(MovieDAO.find(name, user.getID()));
                    } 
                case 5:
                    sortMovie();
                    break;
                case 6:

                default:
                    break;
            }
        }
    }

    private static void addMoive() {
        String name = InputUtils.inputString("Please enter name: ");
        clearScreen();
        System.out.println("If you don't want to type, just press enter");
        String description = InputUtils.inputStringOptional("Please enter description: ");
        clearPreviousLine();
        String link = InputUtils.inputStringOptional("Please enter link: ");
        clearPreviousLine();
        String duration = InputUtils.inputStringOptional("Please enter duration: ");
        clearPreviousLine();
        String date = InputUtils.inputStringToDate("Please enter in yyyy-mm-dd format: ");
        clearPreviousLine();
        String code = InputUtils.inputStringOptional("Please enter code: ");
        if(!MovieDAO.addMovies(name, description, link, duration, date, code, user.getID())) {
            System.out.println("Adding a new movie was a failure! ");
        }
        mv.add(MovieDAO.Select(user.getID()));
    }

    private static void sortMovie() {
        System.out.println("1. Name");
        System.out.println("2. Duration");
        System.out.println("3. Date");
        int option = InputUtils.inputInteger("sort by:  ",1,3);
        String ans = InputUtils.inputString("Do you want to sort in ascending order? (y/n) : ");
        boolean desc = false;
        if (ans.equalsIgnoreCase("y")) {
            desc = true;
        } 
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
        System.out.println("1. Name");
        System.out.println("2. Duration");
        System.out.println("3. Code");
        System.out.println("4. Date");
        System.out.println("5. Link");
        System.out.println("6. Description");
        
        int choose = InputUtils.inputInteger("Please select the column you wish to change: ", 1, 6);

        switch (choose) {
            case 1:
                break;
        
            default:
                break;
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
}