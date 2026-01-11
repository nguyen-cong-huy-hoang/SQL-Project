package vn.MovieManagement.model;

public class author extends BaseObject{
    private int age;
    private String country;
    private String code;
    private int AuthorNumber;
    private int id;
    public author(String Name, String description, int age, String country, 
                  String code, int userID, int AuthorNumber, int id) {
        super(Name, description, userID);
        this.age = age;
        this.country = country;
        this.code = code;
        this.AuthorNumber = AuthorNumber;
        this.id = id;
    }

    public int getAge() { return this.age; }
    public String getCountry() { return this.country; }
    public String getCode() { return this.code; }
    public int getAuthorNumber() { return this.AuthorNumber; }
    public int getID() { return this.id; }

    public void setAge(int age) { this.age = age;}
    public void setCountry(String country) { this.country = country; }
    public void setCode(String code) { this.code = code; }
    public void setAuthorNumber(int AuthorNumber) { this.AuthorNumber = AuthorNumber; }
    public void setID(int id) { this.id = id; }
}
