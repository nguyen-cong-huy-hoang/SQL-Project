package vn.MovieManagement.service;

import vn.MovieManagement.model.Movie;
import java.util.ArrayList;
import java.util.Arrays;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;


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
        if (movieManagement == null || movieManagement.isEmpty()) {
            System.out.println("\n[!] DATA DOES NOT EXIST\n");
            return;
        }

        String table = AsciiTable.getTable(AsciiTable.BASIC_ASCII, movieManagement, Arrays.asList(
            new Column().header("ID")
                        .dataAlign(HorizontalAlign.CENTER)
                        .with(m -> String.valueOf(m.getID())),

            new Column().header("NAME")
                        .maxWidth(25) 
                        .with(Movie::getName),

            new Column().header("DURATION").with(Movie::getDuration),
            new Column().header("CODE").with(Movie::getCode),
            new Column().header("DATE").with(m -> String.valueOf(m.getDate())),

            new Column().header("LINK")
                        .maxWidth(25) 
                        .with(m -> m.getLink() == null ? "" : m.getLink()),

            new Column().header("DESCRIPTION")
                        .maxWidth(35) 
                        .with(m -> m.getDescription() == null ? "" : m.getDescription())
        ));

        System.out.println(table);
    }
}