package vn.MovieManagement.model;

import java.util.ArrayList;
public class UserManagement {
    private ArrayList<User> userManager;
    private int size;
    private static UserManagement Instance;

    private UserManagement() {
        userManager = new ArrayList<>();
        size = 0;
    }

    public static UserManagement getInstance() {
        if(Instance == null) {
            return new UserManagement();
        }
        return Instance;
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
