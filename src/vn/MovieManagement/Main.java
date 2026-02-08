package vn.MovieManagement;

import vn.MovieManagement.dao.*;
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

            mv.print();
            int choose = InputUtils.inputInteger("Please choose an option: ", 0, 6);
            
            switch (choose) {
                case 1:
                    mv.add(MovieDAO.Select(user.getID()));
                    mv .print();                  
                    break;
            
                default:
                    break;
            }
        }
    }


    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}