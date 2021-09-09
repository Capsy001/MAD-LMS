package models;



public class Person {

    private String name;
    private String email;
    private String password;
    private boolean accounttype;

    public Person(){}


    public Person(String name, String email, String password, boolean accounttype) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accounttype = accounttype;
    }
}
