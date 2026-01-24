package vn.MovieManagement.service;

import vn.MovieManagement.model.User;
import java.util.ArrayList;
public class UserManagement {
    private ArrayList<User> userManager;
    private int size;

    public UserManagement() {
        userManager = new ArrayList<>();
        size = 0;
    }

    public int Size() {
        return this.size;
    }
    public void add(int id, User user) {
        userManager.add(id, user);
        size++;
    }
    public void remove(int id) {
        userManager.remove(id);
        size--;
    }

    public void clear() {
        userManager.clear();
        size = 0;
    }

    
}
