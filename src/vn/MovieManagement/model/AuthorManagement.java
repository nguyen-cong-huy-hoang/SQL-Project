package vn.MovieManagement.model;

import java.util.ArrayList;
public class AuthorManagement {
    private ArrayList<author> authorManagement;
    private static AuthorManagement Instance;
    private int Size;
    private ArrayList<Boolean> Mark;

    private AuthorManagement() {
        authorManagement = new ArrayList<>();
        Size = 0;
        Mark = new ArrayList<>();
    }

    public static AuthorManagement getInstance() {
        if(Instance == null) {
            return new AuthorManagement();
        }
        return Instance;
    }

    public void add(int id, author Author) {
        authorManagement.set(id, Author);
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

    }

    public void MarkTrue() {
        for(author a : authorManagement) {
            Mark.set(a.getID(), true);
        }
    }

    public void MarkFalse() {
        for(author a : authorManagement) {
            Mark.set(a.getID(), false);
        }
    }

    public void setMark(int id, boolean val) {
        Mark.set(id, val);
    }
    
    public boolean getMark(int id) {
        return Mark.get(id);
    }
}
