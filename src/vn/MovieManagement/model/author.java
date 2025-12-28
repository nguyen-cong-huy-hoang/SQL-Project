package vn.MovieManagement.model;

public class author extends BaseObject{
    private int age;
    private String country;
    private String code;
    public author(String Name, String description, int age, String country, String code) {
        super(Name, description);
        this.age = age;
        this.country = country;
        this.code = code;
    }

    public int getAge() { return this.age; }
    public String getCountry() { return this.country; }
    public String getCode() { return this.code; }

    public void setAge(int age) { this.age = age;}
    public void setCountry(String country) { this.country = country; }
    public void setCode(String code) { this.code = code; }
}
