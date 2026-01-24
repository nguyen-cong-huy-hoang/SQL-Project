package vn.MovieManagement.service;

import vn.MovieManagement.model.author;

public interface IAuthorManagement {
    public int Size();
    public void add(int id, author Author);
    public void remove(int id);
    public void clear();
    public void print();
}
