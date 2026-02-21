package vn.MovieManagement.service;

import java.util.ArrayList;

import vn.MovieManagement.model.Movie;

public interface IMovieManagement {
    public int Size();
    public void add(Movie movie);
    public void add(ArrayList<Movie> m);
    public void remove(int id);
    public void clear();
    public void print();
    public int getIdFromStt(int stt);
}
