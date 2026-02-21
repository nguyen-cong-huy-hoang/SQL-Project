package vn.MovieManagement.service;

import java.util.ArrayList;

import vn.MovieManagement.model.author;

public interface IAuthorManagement {
    public int Size();
    public void add(author Author);
    public void add(ArrayList<author> Author);
    public void remove(int id);
    public void clear();
    public void print();
    public int getIdFromStt(int stt);
}
