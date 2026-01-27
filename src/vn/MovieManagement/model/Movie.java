package vn.MovieManagement.model;

public class Movie extends BaseObject{
    private String link;
    private String duration;
    private String date;
    private String code;
    private int id;
    private int User_ID;

    public Movie(String Name, String description, String link, 
                String duration, String date, String code, int id, int User_ID) {
        super(Name, description);
        this.link = link;
        this.duration = duration;
        this.date = date;
        this.code = code;
        this.id = id;
        this.User_ID = User_ID;
    }

    public String  getLink() { return this.link; }
    public String getDuration() { return this.duration; }
    public String getDate() { return this.date; }
    public String getCode() { return this.code; }
    public int getID() { return this.id; }
    public int getUserID() { return this.User_ID; }

    public void setLink(String link) { this.link = link; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setDate(String date) { this.date = date; }
    public void setCode(String code) { this.code = code; }
    public void setID(int id) { this.id = id; }
    public void setUserID(int User_ID) { this.User_ID = User_ID; }
}
