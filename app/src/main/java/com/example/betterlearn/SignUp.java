package com.example.betterlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import database.DataAccess;
import models.Person;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        EditText name=findViewById(R.id.name_signup);
        EditText email=findViewById(R.id.email_signup);
        EditText password=findViewById(R.id.password_signup);
        EditText password2=findViewById(R.id.retypepassword_signup);
        Switch accounttype=findViewById(R.id.accounttype_signup);

        Button signup=findViewById(R.id.signup_signup);

        DataAccess dataAccess=new DataAccess();

        signup.setOnClickListener(view -> {

            Person person=new Person(name.getText().toString(), email.getText().toString(), password.getText().toString(), accounttype.isChecked());

        });



    }
}