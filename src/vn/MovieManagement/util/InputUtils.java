package vn.MovieManagement.util;

import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    private InputUtils() {} 

    public static int inputInteger(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Incorrect format, please enter a number!");
            }
        }
    }

    public static int inputInteger(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                int number = Integer.parseInt(input);
                if (number >= min && number <= max) {
                    return number; 
                } else {
                    System.out.println(">> Error: The value must be within the range of " + min + " to " + max + "!");
                }
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Incorrect format, please enter a number!");
            }
        }
    }
    
    public static String inputString(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(">> Error: No blank space allowed!!");
        }
    }

    public static String inputStringOptional(String message) {
        System.out.print(message);
        String input = scanner.nextLine().trim();
    
        if (input.isEmpty() || input == "") {
            return null; 
        }
        return input;
    }

    public static int inputIntegeroptional(String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return -1; 
            }
            try {
                return Integer.parseInt(input); 
            } catch (NumberFormatException e) {
                System.out.println(">> Error: Please enter a valid number, or press Enter to skip!");
            }
        }
    }

    public static String inputStringToDate(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                if (input.isEmpty()) {
                    return null; 
                }
                StringFormat.parseSqlDate(input); 
                
                if (StringFormat.isValidDate(input)) {
                    return input;
                } else {
                    System.out.println(">> Error: Date does not exist!");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(">> Error: " + e.getMessage());
            }
        }
    }
}