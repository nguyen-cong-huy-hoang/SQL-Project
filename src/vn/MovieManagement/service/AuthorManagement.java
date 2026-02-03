package vn.MovieManagement.service;

import vn.MovieManagement.model.author;
import java.util.ArrayList;

public class AuthorManagement implements IAuthorManagement {
    private ArrayList<author> authorManagement;
    private int Size;


    public AuthorManagement() {
        authorManagement = new ArrayList<>();
        Size = 0;
    }

    public int Size() {
        return this.Size;
    }


    public void add(int id, author Author) {
        authorManagement.add(id, Author);
        Size++;
    }
    
    public void remove(int id) {
        if(Size == 0) return;
        authorManagement.remove(id);
        Size--;
    }

    public void clear() {
        authorManagement.clear();
        Size = 0;
    }

    public void print() {

        String format = "| %-10d | %-15.15s | %-5s | %-15.15s | %-30.30s |%n";

 
        String header = String.format(format, "ID", "NAME", "AGE", "COUNTRY", "DESCRIPTION");

        int tableLength = header.length() - System.lineSeparator().length();
        String separator = "-".repeat(tableLength);

        System.out.println(separator);
        System.out.print(header);
        System.out.println(separator);


        if (authorManagement == null || authorManagement.isEmpty()) {
            String msg = "DATA DOES NOT EXIST";
    
            int padding = (tableLength - msg.length() - 4);
            System.out.printf("| %-" + (tableLength - 4) + "s |%n", msg);
        } else {
            for (author a : authorManagement) {
                System.out.printf(format,
                    a.getID(),              
                    a.getName(),         
                    a.getAge(),         
                    a.getCountry(),                  
                    (a.getDescription() == null ? "" : a.getDescription())
                );
            }
        }
        System.out.println(separator);
    }


}
