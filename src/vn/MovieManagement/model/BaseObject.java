package vn.MovieManagement.model;

public class BaseObject {
    private String Name;
    private String description;
    private int userID;
    public BaseObject(String Name, String description, int userID) {
        this.Name = Name;
        this.description = description;
        this.userID = userID;
    }

    public String getName() { return this.Name; }
    public String getDescription() { return this.description; }
    public int getUserID() { return this.userID; }
    public void setName(String Name) { this.Name = Name; }
    public void setDescription(String description) { this.description = description; }
    public void setUserID(int userID) { this.userID = userID; }
}
