package com.example.betterlearn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import StudentFragments.StudentDashboard;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view){

        Intent intent1=new Intent(this, StudentDashboard.class);
        startActivity(intent1);

    }

    public void forgotPassword(View view){

        Intent intent1=new Intent(this, ForgotPassword.class);
        startActivity(intent1);

    }
}