package vn.MovieManagement.service;

import vn.MovieManagement.model.Movie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;

public class MovieManagement implements IMovieManagement {
    private int Size;
    private ArrayList<Movie> movieManagement;
    private Map<Integer, Integer> idMapping; 

    public MovieManagement() {
        movieManagement = new ArrayList<>();
        idMapping = new HashMap<>();
        Size = 0;
    }

    public int Size() {
        return this.movieManagement.size(); 
    }

    public void add(Movie movie) {
        movieManagement.add(movie); 
        Size++;
        rebuildMapping();
    }

    public void add(ArrayList<Movie> m) {
        for(Movie x : m) {
            movieManagement.add(x);
        }
        Size = movieManagement.size();
        rebuildMapping(); 
    }

    public void remove(int index) {
        if (Size == 0 || index < 0 || index >= Size) return; 
        
        movieManagement.remove(index);
        Size--;
        rebuildMapping(); 
    }

    public void clear() {
        movieManagement.clear();
        idMapping.clear();
        Size = 0;
    }

   
    private void rebuildMapping() {
        idMapping.clear();
        for (int i = 0; i < movieManagement.size(); i++) {
            idMapping.put(movieManagement.get(i).getID(), i + 1); 
        }
    }

    public void print() {
        if (movieManagement == null || movieManagement.isEmpty()) {
            System.out.println("\n[!] DATA DOES NOT EXIST\n");
            return;
        }

        String table = AsciiTable.getTable(AsciiTable.BASIC_ASCII, movieManagement, Arrays.asList(
            new Column().header("STT") 
                        .with(m -> String.valueOf(adapterID(m))),

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

    private int adapterID(Movie m) {
        return idMapping.getOrDefault(m.getID(), -1);
    }

    public int getIdFromStt(int stt) {
        if (stt < 1 || stt > movieManagement.size()) {
            return -1; 
        }
        int index = stt - 1;
        Movie m = movieManagement.get(index);
        return m.getID();
    }
}