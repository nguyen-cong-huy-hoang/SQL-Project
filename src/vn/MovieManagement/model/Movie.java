package vn.MovieManagement.model;

public class Movie extends BaseObject{
    private String link;
    private String duration;
    private String date;
    private String code;

    public Movie(String Name, String description, String link, String duration, String date, String code) {
        super(Name, description);
        this.link = link;
        this.duration = duration;
        this.date = date;
        this.code = code;
    }

    public String  getLink() { return this.link; }
    public String getDuration() { return this.duration; }
    public String getDate() { return this.date; }
    public String getCode() { return this.code; }

    public void setLink(String link) { this.link = link; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setDate(String date) { this.date = date; }
    public void setCode(String code) { this.code = code; }
}
