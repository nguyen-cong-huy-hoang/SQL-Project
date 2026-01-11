package vn.MovieManagement.model;

public class Movie extends BaseObject{
    private String link;
    private String duration;
    private String date;
    private String code;
    private int AuthorNumber;
    private int id;

    public Movie(String Name, String description, String link, int AuthorNumber,
                String duration, String date, String code, int userID, int id) {
        super(Name, description, userID);
        this.link = link;
        this.duration = duration;
        this.date = date;
        this.code = code;
        this.AuthorNumber = AuthorNumber;
        this.id = id;
    }

    public String  getLink() { return this.link; }
    public String getDuration() { return this.duration; }
    public String getDate() { return this.date; }
    public String getCode() { return this.code; }
    public int getAuthorNumber() { return this.AuthorNumber; }
    public int getID() { return this.id; }

    public void setLink(String link) { this.link = link; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setDate(String date) { this.date = date; }
    public void setCode(String code) { this.code = code; }
    public void setAuthorNumber(int AuthorNumber) { this.AuthorNumber = AuthorNumber; }
    public void setID(int id) { this.id = id; }
}
