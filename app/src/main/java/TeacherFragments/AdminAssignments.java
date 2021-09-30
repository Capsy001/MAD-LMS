package TeacherFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.betterlearn.Admin_Assigment_Adapter;
import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import models.Assigments;


public class AdminAssignments extends Fragment implements View.OnClickListener   {

    Button Add_assigmnt;
    GridView assigmnt;
    ArrayList<Assigments> dataModalArrayList;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    public static FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_admin_assignment, container, false);

        Add_assigmnt = (Button) myView.findViewById(R.id.add_assignment);

        fragmentManager=getFragmentManager();
        Add_assigmnt.setOnClickListener(this);


        assigmnt = myView.findViewById(R.id.admin_assignment_sec);
        dataModalArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        // here we are calling a method
        // to load data in our list view.
        loadAssigmntDetails();
        // Inflate the layout for this fragment
        return myView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;



        fragment = new NewAssignment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.defaultDashboard, fragment);
        ft.commit();



    }

    private void loadAssigmntDetails() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        fAuth= FirebaseAuth.getInstance();
        //firestore databse initialize
        fStore=FirebaseFirestore.getInstance();
        userID=fAuth.getCurrentUser().getUid();
        db.collection("Assigments").whereEqualTo("user", userID).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our
                            // progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                // after getting this list we are passing
                                // that list to our object class.
                                Assigments dataModal = d.toObject(Assigments.class);

                                // after getting data from Firebase
                                // we are storing that data in our array list
                                dataModalArrayList.add(dataModal);
                            }
                            // after that we are passing our array list to our adapter class.
                            Admin_Assigment_Adapter adapter = new Admin_Assigment_Adapter(getActivity(), dataModalArrayList);

                            // after passing this array list
                            // to our adapter class we are setting
                            // our adapter to our list view.
                            assigmnt.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getActivity(), "No Announcements in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(getActivity(), "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}