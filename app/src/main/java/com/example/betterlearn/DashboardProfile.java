package com.example.betterlearn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class DashboardProfile extends Fragment {
    public static final String TAG = "TAG";
    TextView name;
    TextView email;
    TextView accType;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_dashboard_profile, container, false);

        name= (TextView)rootView.findViewById(R.id.name_profile);
        email= (TextView)rootView.findViewById(R.id.email_profile);
        accType= (TextView)rootView.findViewById(R.id.accounttype_profile);

        fAuth= FirebaseAuth.getInstance();  //initialize firebase authentication instance
        fStore= FirebaseFirestore.getInstance();    ////initialize firebase database instance
        userID= fAuth.getCurrentUser().getUid();    //get current user's UserID

        DocumentReference documentReference=fStore.collection("users").document(userID);    //document reference for current user's document

        //ddd
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot value = task.getResult();

                    name.setText(value.getString("name").toString());
                    email.setText(value.getString("email").toString());

                    if(value.getBoolean("type").booleanValue()==false) {
                        accType.setText("Teacher");
                    }
                    else if(value.getBoolean("type").booleanValue()==true) {
                        accType.setText("Student");
                    }

                    if (value.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + value.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        //dddd



        return rootView;


    }




}