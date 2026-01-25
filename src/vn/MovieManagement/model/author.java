package vn.MovieManagement.model;

public class author extends BaseObject{
    private int age;
    private String country;
    private int AuthorNumber;
    private int id;
    private int UserID;
    public author(String Name, String description, int age, String country,
                  int AuthorNumber, int id, int UserID) {
        super(Name, description);
        this.age = age;
        this.country = country;
        this.AuthorNumber = AuthorNumber;
        this.id = id;
        this.UserID = UserID;
    }

    public int getAge() { return this.age; }
    public String getCountry() { return this.country; }
    public int getAuthorNumber() { return this.AuthorNumber; }
    public int getID() { return this.id; }
    public int getUserID() { return this.UserID; }

    public void setAge(int age) { this.age = age;}
    public void setCountry(String country) { this.country = country; }
    public void setAuthorNumber(int AuthorNumber) { this.AuthorNumber = AuthorNumber; }
    public void setID(int id) { this.id = id; }
    public void setUserID(int UserID) { this.UserID = UserID; }
}
