package vn.MovieManagement.model;

public class User {
    private String user;
    private String passWord;
    private int id;
    public User(String user, String password) {
        this.user = user;
        this.passWord = password;
    }

    public String getUser() { return this.user; }
    public String getPassWord() { return this.passWord; }
    public int getID() { return this.id; }

    public void setUser(String user) { this.user = user; }
    public void setPassWord(String passWord) { this.passWord = passWord; }
    public void setID(int id) { this.id = id; }
    
}
