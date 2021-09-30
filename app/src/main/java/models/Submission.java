package models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Submission implements Serializable {
    // variables for storing our image and name.
    private String course;
    private String institutes;
    private String owner;
    private String submissions;
    private String user;


    @Exclude
    private String key;

    public Submission() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public Submission(String course, String submissions) {
        this.course = course;
        this.submissions = submissions;
    }

    public Submission(String course, String institutes, String owner, String submissions, String user) {
        this.course = course;
        this.institutes = institutes;
        this.owner = owner;
        this.course = course;
        this.submissions = submissions;
        this.user = user;
    }


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getInstitute() {
        return institutes;
    }

    public void setInstitute(String institutes) {
        this.institutes = institutes;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getSubmissions() {
        return submissions;
    }

    public void setSubmissions(String submissions) {
        this.submissions = submissions;
    }
}
