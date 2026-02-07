package vn.MovieManagement.service;

import vn.MovieManagement.model.author;
import java.util.ArrayList;
import java.util.Arrays;

import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

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
        if (authorManagement == null || authorManagement.isEmpty()) {
            System.out.println("\n[!] DATA DOES NOT EXIST\n");
            return;
        }

        String table = AsciiTable.getTable(AsciiTable.BASIC_ASCII, authorManagement, Arrays.asList(
            new Column().header("ID")
                        .headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.CENTER)
                        .with(a -> String.valueOf(a.getID())),

            new Column().header("NAME")
                        .maxWidth(30)
                        .with(author::getName),

            new Column().header("AGE")
                        .headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.CENTER)
                        .with(a -> String.valueOf(a.getAge())),

            new Column().header("COUNTRY")
                        .maxWidth(30)
                        .with(author::getCountry),

            new Column().header("DESCRIPTION")
                        .maxWidth(30)
                        .with(a -> a.getDescription() == null ? "" : a.getDescription())
        ));

        System.out.println(table);
    }
}
