package vn.MovieManagement.service;

import vn.MovieManagement.model.Movie;

public interface IMovieManagement {
    public int Size();
    public void add(int id, Movie movie);
    public void remove(int id);
    public void clear();
    public void print();
}
