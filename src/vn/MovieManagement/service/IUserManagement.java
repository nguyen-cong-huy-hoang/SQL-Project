package vn.MovieManagement.service;

import vn.MovieManagement.model.User;

public interface IUserManagement {
    public int Size();
    public void add(int id, User user);
    public void remove(int id);
    public void clear();
    
}
