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
        String format = "| %-10s | %-30.30s | %-5s | %-30.30s | %-30s |%n";
        int descWidth = 30; 

        String header = String.format(format, "ID", "NAME", "AGE", "COUNTRY", "DESCRIPTION");
        int tableLength = header.length() - System.lineSeparator().length();
        String separator = "-".repeat(tableLength);

        System.out.println(separator);
        System.out.print(header);
        System.out.println(separator);

        if (authorManagement == null || authorManagement.isEmpty()) {
            String msg = "DATA DOES NOT EXIST";
            System.out.printf("| %-" + (tableLength - 4) + "s |%n", msg);
        } else {
            for (author a : authorManagement) {
                String desc = (a.getDescription() == null ? "" : a.getDescription());
                
                String firstPart = desc.length() > descWidth ? desc.substring(0, descWidth) : desc;
                
   
                System.out.printf(format, 
                    String.valueOf(a.getID()), 
                    a.getName(), 
                    a.getAge(), 
                    a.getCountry(), 
                    firstPart
                );

                int start = descWidth;
                while (start < desc.length()) {
                    int end = Math.min(start + descWidth, desc.length());
                    String subDesc = desc.substring(start, end);
                    
                    System.out.printf("| %-10s | %-30s | %-5s | %-30s | %-30s |%n", 
                                    "", "", "", "", subDesc);
                    start += descWidth;
                }
               
            }
        }
        System.out.println(separator);
    }

}
