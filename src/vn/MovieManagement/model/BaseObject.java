package vn.MovieManagement.model;

public class BaseObject {
    private String Name;
    private String description;
    public BaseObject(String Name, String description) {
        this.Name = Name;
        this.description = description;
    }

    public String getName() { return this.Name; }
    public String getDescription() { return this.description; }

    public void setName(String Name) { this.Name = Name; }
    public void setDescription(String description) { this.description = description; }

}
