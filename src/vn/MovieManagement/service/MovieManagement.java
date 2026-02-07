package vn.MovieManagement.service;

import vn.MovieManagement.model.Movie;
import java.util.ArrayList;

public class MovieManagement implements IMovieManagement {
    private int Size;
    private ArrayList<Movie> movieManagement;
    public MovieManagement() {
        movieManagement = new ArrayList<>();
        Size = 0;
    }

    public int Size() {
        return this.Size;
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
        Size = 0;
    }

    public void print() {
        String format = "| %-10s | %-15.15s | %-10s | %-12s | %-10s | %-30s |%n";
        int descWidth = 30; 

        String header = String.format(format, "ID", "NAME", "DURATION", "CODE", "DATE", "DESCRIPTION");
        int tableLength = header.length() - System.lineSeparator().length();
        String separator = "-".repeat(tableLength);

        System.out.println(separator);
        System.out.print(header);
        System.out.println(separator);

        if (movieManagement == null || movieManagement.isEmpty()) {
            String msg = "DATA DOES NOT EXIST";
            System.out.printf("| %-" + (tableLength - 4) + "s |%n", msg);
        } else {
            for (Movie m : movieManagement) {
                String desc = (m.getDescription() == null ? "" : m.getDescription());

                String firstPart = desc.length() > descWidth ? desc.substring(0, descWidth) : desc;
                System.out.printf(format,
                    String.valueOf(m.getID()),
                    m.getName(),
                    m.getDuration(),
                    m.getCode(),
                    String.valueOf(m.getDate()),
                    firstPart
                );

                int start = descWidth;
                while (start < desc.length()) {
                    int end = Math.min(start + descWidth, desc.length());
                    String subDesc = desc.substring(start, end);        
                    System.out.printf("| %-10s | %-15s | %-10s | %-12s | %-10s | %-30s |%n",
                                    "", "", "", "", "", subDesc);
                    start += descWidth;
                }
            }
        }
        System.out.println(separator);
    }
}