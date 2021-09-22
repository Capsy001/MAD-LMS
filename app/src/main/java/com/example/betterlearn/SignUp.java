package com.example.betterlearn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import StudentFragments.StudentDashboard;

public class SignUp extends AppCompatActivity {

    public static final String TAG="TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        EditText name=findViewById(R.id.name_signup);
        EditText email=findViewById(R.id.email_signup);
        EditText password=findViewById(R.id.password_signup);
        EditText password2=findViewById(R.id.retypepassword_signup);
        Switch accounttype=findViewById(R.id.accounttype_signup);

        //firebase Authentication initialize
        fAuth=FirebaseAuth.getInstance();

        //firestore databse initialize
        fStore=FirebaseFirestore.getInstance();

        Button signup=findViewById(R.id.signup_signup);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
            finish();
        }


        signup.setOnClickListener(view -> {

            String rEmail=email.getText().toString().trim();
            String rPassword=password.getText().toString();
            String rPassword2=password2.getText().toString();
            String rName=name.getText().toString();
            Boolean rAccounttype=accounttype.isChecked();


            if(!rPassword.equals(rPassword2)){
                password.setError("Both passwords should match");
                return;

            }

            if(TextUtils.isEmpty(rEmail)){
                email.setError("Email should not be empty");
                return;

            }

            if(rPassword.length()<6){
               password.setError("Password should be 6 or more characters");
                return;

            }


            fAuth.createUserWithEmailAndPassword(rEmail,rPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull  Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(SignUp.this,"User Created", Toast.LENGTH_SHORT).show();

                        userID=fAuth.getCurrentUser().getUid(); //get the firebase user ID of the current user

                        //database document reference
                        DocumentReference documentReference= fStore.collection("users").document(userID);   //Collection="users"

                        Map<String, Object> user= new HashMap<>();
                        user.put("name", rName);
                        user.put("email", rEmail);
                        user.put("type", rAccounttype);

                        //insert to database
                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "User profile created for "+userID);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull  Exception e) {
                                Log.d(TAG, "OnFailure: "+e.toString());
                            }
                        });

                        //foward to student dashboard
                        startActivity(new Intent(getApplicationContext(),StudentDashboard.class));
                    }else{
                        Toast.makeText(SignUp.this,"Error "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            });

        });



    }
}