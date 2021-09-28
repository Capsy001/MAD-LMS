package models;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import java.io.Serializable;

public class Institute implements Serializable {

    String instituteName;
    String user_id;
    String image_url;


    public Institute(String instituteName, String user_id, String image) {
        this.instituteName = instituteName;
        this.user_id = user_id;
        this.image_url=image;


    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }


}
