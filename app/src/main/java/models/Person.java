package models;


import com.google.firebase.auth.FirebaseAuth;

public class Person {

    public String name;
    public String email;
    public String password;
    public boolean accounttype;



    public Person(){}


    public Person(String name, String email, String password, boolean accounttype) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accounttype = accounttype;
    }
}
