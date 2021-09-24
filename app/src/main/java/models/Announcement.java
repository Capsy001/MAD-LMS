package models;

public class Announcement {
    // variables for storing our image and name.
    private String title;
    private String description;

    public Announcement() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public Announcement(String title, String description) {
        this.title = title;
        this.description = description;
    }

    // getter and setter methods
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
