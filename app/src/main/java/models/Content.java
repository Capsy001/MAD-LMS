package models;

import java.io.Serializable;

public class Content implements Serializable {

    String contentID;
    String courseName;
    String description;
    String instituteName;
    String LinkDescription;
    String title;
    String type;
    String downloadURL;

    public Content(String contentID, String courseName, String description, String instituteName, String linkDescription, String title, String type, String downloadURL) {
        this.contentID = contentID;
        this.courseName = courseName;
        this.description = description;
        this.instituteName = instituteName;
        this.LinkDescription = linkDescription;
        this.title = title;
        this.type = type;
        this.downloadURL = downloadURL;
    }

    public String getContentID() {
        return contentID;
    }

    public void setContentID(String contentID) {
        this.contentID = contentID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getLinkDescription() {
        return LinkDescription;
    }

    public void setLinkDescription(String linkDescription) {
        LinkDescription = linkDescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }
}
