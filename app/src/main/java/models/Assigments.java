package models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Assigments implements Serializable {
    // variables for storing our image and name.
    private String title;
    private String description;
    private String deadline;
    private String institutes;
    private String course;
    private String user;


    @Exclude
    private String key;

    public Assigments() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public Assigments(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Assigments(String title, String description, String institutes,String course,String deadline, String user) {
        this.title = title;
        this.description = description;
        this.institutes = institutes;
        this.course = course;
        this.deadline = deadline;
        this.user = user;
    }

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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getInstitutes() {
        return institutes;
    }

    public void setInstitutes(String institutes) {
        this.institutes = institutes;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


}
