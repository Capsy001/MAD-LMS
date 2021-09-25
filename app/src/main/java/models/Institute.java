package models;

public class Institute {

    String instituteName;
    String user_id;

    public Institute(String instituteName, String user_id) {
        this.instituteName = instituteName;
        this.user_id = user_id;
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
}
