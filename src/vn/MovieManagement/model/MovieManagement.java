package vn.MovieManagement.model;

import java.util.ArrayList;
import java.util.List;


public class MovieManagement {
    private int Size;
    private ArrayList<Movie> movieManagement;
    private ArrayList<Boolean> Mark;
    private static MovieManagement Instance = null;
    private MovieManagement() {
        movieManagement = new ArrayList<>();
        Size = 0;
        Mark = new ArrayList<>();
    }

    public static MovieManagement getInstance() {
        if(Instance == null) {
            return new MovieManagement();
        }
        return Instance;
    }

    public void add(int id, Movie movie) {
        movieManagement.add(id, movie);
        Size++;
    }

    public void remove(int id) {
        if(Size == 0) return;
        movieManagement.remove(id);
        Size--;
    }

    public void clear() {
        movieManagement.clear();
    }

    public void print() {
        String format = "| %-10s | %-15.15s | %-10s | %-12s | %-30.30s |%n";

        String header = String.format(format, "CODE", "NAME", "DURATION", "DATE", "DESCRIPTION");

        int tableLength = header.length() - System.lineSeparator().length();
        String separator = "-".repeat(tableLength);

        System.out.println(separator);
        System.out.print(header);
        System.out.println(separator);

        if (movieManagement == null || movieManagement.isEmpty()) {
            String msg = "DATA DOES NOT EXIST";
            int padding = (tableLength - msg.length() - 2); 
            System.out.printf("| %-" + padding + "s |%n", msg);
        } else {
            for (Movie m : movieManagement) {
                if(Mark.get(m.getID()) == true) {
                    System.out.printf(format,
                        m.getCode(),               
                        m.getName(),                    
                        m.getDuration(),         
                        m.getID(),                
                        String.valueOf(m.getDate()), 
                        (m.getDescription() == null ? "" : m.getDescription()) 
                    );
                }
            }
        }
        System.out.println(separator);
    }

    public void MarkTrue() {
        for(Movie m : movieManagement) {
            Mark.set(m.getID(), true);
        }
    }

    public void MarkFalse() {
        for(Movie m : movieManagement) {
            Mark.set(m.getID(), false);
        }
    }

    public void setMark(int id, boolean val) {
        Mark.set(id, val);
    }
    
}