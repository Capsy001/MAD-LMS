package models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Announcement  implements Serializable {
    // variables for storing our image and name.
    private String title;
    private String description;
    private String institutes;
    private String user;



    @Exclude
    private String key;

    public Announcement() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public Announcement(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Announcement(String title, String description,String institutes,String user) {
        this.title = title;
        this.description = description;
        this.institutes = institutes;
        this.user = user;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    public String getInstitutes() {
        return institutes;
    }

    public void setInstitutes(String institutes) {
        this.institutes = institutes;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
