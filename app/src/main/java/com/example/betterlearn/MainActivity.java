package com.example.betterlearn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import StudentFragments.StudentDashboard;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth fAuth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void signIn(View view){

        if(fAuth.getCurrentUser() != null){

            Intent intent1=new Intent(this, StudentDashboard.class);
            startActivity(intent1);

            return;

        }

        Intent intent1=new Intent(this, Login.class);
        startActivity(intent1);

    }

    public void signUp(View view){

        Intent intent1=new Intent(this, SignUp.class);
        startActivity(intent1);

    }
}